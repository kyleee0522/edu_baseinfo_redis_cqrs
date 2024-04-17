package com.kt.edu.baseinfo.redis.query.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "BI_CSYSCD")
public class BiCsyscdQryEntt implements Serializable {
	private static final long serialVersionUID = -805991557545104220L;

	@Column("GRP_ID")
	private String grpId;
	
	@Column("CD")
	private String cd;
	
	@Column("NAME")
	private String name;
	
	@Column("VAL1")
	private String val1;
	
	@Column("VAL2")
	private String val2;

	@Column("VAL3")
	private String val3;
	
	@Column("VAL4")
	private String val4;
	
	@Column("VAL5")
	private String val5;
	
	@Column("REG_DATE")
	private Timestamp regDate;	
}
