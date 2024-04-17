package com.kt.edu.baseinfo.redis.query.repository;


import com.kt.edu.baseinfo.redis.query.payload.in.dto.BiCsyscdInQryDto;
import com.kt.edu.baseinfo.redis.query.payload.out.dto.BiCsyscdOutQryDto;
import com.kt.edu.baseinfo.redis.query.repository.sql.BiCsyscdDynamicQrySql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface BiCsyscdQryRepo {
	@SelectProvider(type = BiCsyscdDynamicQrySql.class, method = "findCsyscdSql" )
	List<BiCsyscdOutQryDto> findCsyscd(BiCsyscdInQryDto inDto);
}
