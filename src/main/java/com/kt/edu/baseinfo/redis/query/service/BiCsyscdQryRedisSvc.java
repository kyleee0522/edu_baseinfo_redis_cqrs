package com.kt.edu.baseinfo.redis.query.service;

import com.kt.edu.baseinfo.constants.BiqueryConstants;
import com.kt.edu.baseinfo.redis.constants.RedisKeyConstants;
import com.kt.edu.baseinfo.redis.query.payload.in.dto.BiCsyscdInQryDto;
import com.kt.edu.baseinfo.redis.query.payload.out.dto.BiCsyscdOutQryDto;
import com.kt.edu.baseinfo.redis.query.repository.BiCsyscdQryRepo;
import com.kt.edu.common.redis.RedisOperator;
import com.kt.edu.common.utils.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BiCsyscdQryRedisSvc {
	private final BiCsyscdQryRepo biCsyscdQryRepo;
	private final RedisOperator<List<BiCsyscdOutQryDto>> redisOperator;

	public List<BiCsyscdOutQryDto> findCsyscd(BiCsyscdInQryDto inDto) {
		List<BiCsyscdOutQryDto> list = new ArrayList<>();

		if (isOnlyGrpIdProvided(inDto) || isGrpIdAndCdOnly(inDto)) {
			return findByRedis(inDto);
		} else {
			// 그 외 조회는 DB를 사용
			return biCsyscdQryRepo.findCsyscd(inDto);
		}
	}

	private boolean isOnlyGrpIdProvided(BiCsyscdInQryDto inDto) {
		return StringUtils.isNotEmpty(inDto.getGrpId()) &&
				StringUtils.isAllEmpty(inDto.getCd(), inDto.getSaCd(), inDto.getSoItemId(), inDto.getName(),
						inDto.getRef1(), inDto.getRef2(), inDto.getRef4(), inDto.getRef5(),
						inDto.getDefineName(), inDto.getStartDate(), inDto.getRemark(),
						inDto.getWhereTxt(), inDto.getEffectiveDateCheck()) &&
				inDto.getRef3() == null && inDto.getOutputSeq() == null;
	}

	private boolean isGrpIdAndCdOnly(BiCsyscdInQryDto inDto) {
		return StringUtils.isNotEmpty(inDto.getGrpId()) && StringUtils.isNotEmpty(inDto.getCd()) &&
				StringUtils.isAllEmpty(inDto.getSaCd(), inDto.getSoItemId(), inDto.getName(), inDto.getRef1(),
						inDto.getRef2(), inDto.getRef4(), inDto.getRef5(), inDto.getDefineName(),
						inDto.getStartDate(), inDto.getRemark(), inDto.getWhereTxt(),
						inDto.getEffectiveDateCheck()) &&
				inDto.getRef3() == null && inDto.getOutputSeq() == null;
	}

	private List<BiCsyscdOutQryDto> findByRedis(BiCsyscdInQryDto inDto) {
		List<BiCsyscdOutQryDto> list = new ArrayList<>();
		for (String grpId : inDto.getGrpId().split(BiqueryConstants.MDB_DELIM)) {
			String redisKey;
			if (StringUtils.isNotEmpty(inDto.getCd())) {
				LogUtil.info("GrpID와 CD로 조회");
				redisKey = RedisKeyConstants.getRedisKey(RedisKeyConstants.REDIS_BI_MDB_CSYSCD_GRPID_CD.key(), grpId, inDto.getCd());
				LogUtil.info("redisKey : {}", redisKey);
			} else {
				LogUtil.info("GrpID로 조회");
				redisKey = RedisKeyConstants.getRedisKey(RedisKeyConstants.REDIS_BI_MDB_CSYSCD_GRPID.key(), grpId);
			}

			List<BiCsyscdOutQryDto> redisList = null;

			try {
				redisList = redisOperator.getValue(redisKey);
				LogUtil.info("redisList : {}", redisList);
			} catch (Exception e) {
				LogUtil.info("Redis 접근 중 에러 발생: " + e.getMessage());
			}

			if (redisList == null || redisList.isEmpty()) {
				List<BiCsyscdOutQryDto> dbList = biCsyscdQryRepo.findCsyscd(BiCsyscdInQryDto.builder().grpId(grpId).cd(inDto.getCd()).build());
				if (!dbList.isEmpty()) {
					redisOperator.set(redisKey, dbList, RedisKeyConstants.REDIS_BI_MDB_CSYSCD_GRPID.ttl(), RedisKeyConstants.REDIS_BI_MDB_CSYSCD_GRPID.unit());
					LogUtil.info("DB에서 데이터 확인 및 Redis 업데이트 : {}", redisKey);
					list.addAll(dbList);
				}
			} else {
				LogUtil.info("Redis에서 데이터 확인 : {}", redisKey);
				list.addAll(redisList);
			}
		}
		return list;
	}

}
