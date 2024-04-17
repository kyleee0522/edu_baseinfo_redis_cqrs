package com.kt.edu.baseinfo.redis.command.service;

import static com.kt.edu.baseinfo.redis.constants.RedisKeyConstants.REDIS_BI_MDB_CSYSCD_GRPID;
import static com.kt.edu.baseinfo.redis.constants.RedisKeyConstants.REDIS_BI_MDB_CSYSCD_GRPID_CD;
import static com.kt.edu.baseinfo.redis.constants.RedisKeyConstants.getRedisKey;

import com.kt.edu.baseinfo.redis.command.payload.in.dto.BiCsyscdInCmdDto;
import com.kt.edu.baseinfo.redis.command.repository.BiCsyscdCmdRepo;
import com.kt.edu.baseinfo.redis.constants.RedisKeyConstants;
import com.kt.edu.baseinfo.redis.query.payload.out.dto.BiCsyscdOutQryDto;
import com.kt.edu.common.redis.RedisOperator;
import com.kt.edu.common.utils.LogUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BiCsyscdCmdSvc {
    private final RedisOperator<List<BiCsyscdOutQryDto>> redisOperator;
    private final BiCsyscdCmdRepo biCsyscdCmdRepo;

    public Integer updateCsyscdGrpid(String grpId, List<BiCsyscdInCmdDto> list) {
        String redisKey = getRedisKey(REDIS_BI_MDB_CSYSCD_GRPID.key(), grpId);
        return updateRedisData(list, redisKey);
    }

    @Transactional
    public Integer updateCsyscdGrpidCd(String grpId, String cd, List<BiCsyscdInCmdDto> list) throws Exception {
        String redisKey = getRedisKey(REDIS_BI_MDB_CSYSCD_GRPID_CD.key(), grpId, cd);
        List<BiCsyscdOutQryDto> originalData = redisOperator.getValue(redisKey);

        try {
            updateRedisData(list, redisKey);
            list.forEach(dto -> {
                biCsyscdCmdRepo.updateCsyscd(grpId, dto);
            });
            LogUtil.info("Successfully updated Redis and DB for grpId: {}, cd: {}", grpId, cd);
        } catch (Exception e) {
            // Redis에 원래 데이터를 복원
            redisOperator.set(redisKey, originalData, REDIS_BI_MDB_CSYSCD_GRPID.ttl(), REDIS_BI_MDB_CSYSCD_GRPID.unit());
            LogUtil.error("Failed to update Redis and DB. Reverted Redis data for key {}: {}", redisKey, originalData);
            throw e; // 예외를 다시 던져서 스프링 트랜잭션이 처리하도록 함
        }
        return list.size();
    }

    public void deleteCsyscdGrpid(String grpId) {
        String redisKey = RedisKeyConstants.getRedisKey(RedisKeyConstants.REDIS_BI_MDB_CSYSCD_GRPID.key(), grpId);
        redisOperator.delete(redisKey);
        biCsyscdCmdRepo.deleteByGrpId(grpId); // Assuming this method is implemented in the repository
        LogUtil.info("Redis와 DB에서 삭제 grpId: {}", grpId);
    }

    public void deleteCsyscdGrpidCd(String grpId, String cd) {
        String redisKey = RedisKeyConstants.getRedisKey(RedisKeyConstants.REDIS_BI_MDB_CSYSCD_GRPID_CD.key(), grpId, cd);
        redisOperator.delete(redisKey);
        biCsyscdCmdRepo.deleteByGrpIdAndCd(grpId, cd); // Assuming this method is implemented in the repository
        LogUtil.info("Redis와 DB에서 삭제 grpId: {}, cd: {}", grpId, cd);
    }

    private Integer updateRedisData(List<BiCsyscdInCmdDto> list, String redisKey) {
        List<BiCsyscdOutQryDto> rList = new ArrayList<>();
        list.forEach(dto -> {
            BiCsyscdOutQryDto outDto = new BiCsyscdOutQryDto();
            BeanUtils.copyProperties(dto, outDto);
            rList.add(outDto);
        });
        redisOperator.set(redisKey, rList, RedisKeyConstants.REDIS_BI_MDB_CSYSCD_GRPID.ttl(), RedisKeyConstants.REDIS_BI_MDB_CSYSCD_GRPID.unit());
        LogUtil.info("Updated Redis data at key {}: {}", redisKey, rList);
        return list.size();
    }
}
