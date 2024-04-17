package com.kt.edu.baseinfo.redis.query.repository.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Builder
@Data
public class BiCsyscdQryDto {
	private String grpId;
	private String cd;
	private String name;
	private String val1;
	private String val2;
	private String val3;
	private String val4;
	private String val5;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Timestamp regDate;
}
