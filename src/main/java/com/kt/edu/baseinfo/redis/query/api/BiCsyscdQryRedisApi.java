package com.kt.edu.baseinfo.redis.query.api;

import com.kt.edu.baseinfo.redis.query.controller.BiCsyscdQryRedisCntr;
import com.kt.edu.baseinfo.redis.query.payload.in.BiCsyscdInQryPyld;
import com.kt.edu.baseinfo.redis.query.payload.out.BiCsyscdOutListQryPyld;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "[기준정보] 기준정보 조회", description = "기준정보 조회")
public class BiCsyscdQryRedisApi {

	private final BiCsyscdQryRedisCntr biCsyscdQryRedisCntr;

	@Operation(summary = "List CSysCd", description = "기준 정보 조회(grpID -> Redis, 기타 -> DB)")
	@PostMapping(path = "/findcsyscd")
	public BiCsyscdOutListQryPyld findCsyscd(@RequestBody BiCsyscdInQryPyld inPyld) {
		return BiCsyscdOutListQryPyld.builder().outDs(biCsyscdQryRedisCntr.findCsyscd(inPyld.getInDs())).build();
	}
}