package com.kt.edu.baseinfo.redis.query.controller;


import com.kt.edu.baseinfo.redis.query.payload.in.dto.BiCsyscdInQryDto;
import com.kt.edu.baseinfo.redis.query.payload.out.dto.BiCsyscdOutQryDto;
import com.kt.edu.baseinfo.redis.query.service.BiCsyscdQryRedisSvc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component

public class BiCsyscdQryRedisCntr {
	private final BiCsyscdQryRedisSvc biCsyscdQryRedisSvc;

	public List<BiCsyscdOutQryDto> findCsyscd(BiCsyscdInQryDto inDto) {

		return biCsyscdQryRedisSvc.findCsyscd(inDto);
	}
}