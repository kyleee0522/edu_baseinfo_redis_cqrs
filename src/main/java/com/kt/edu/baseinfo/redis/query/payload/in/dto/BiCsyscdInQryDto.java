package com.kt.edu.baseinfo.redis.query.payload.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BiCsyscdInQryDto {
    private String grpId;
    private String saCd;
    private String soItemId;
    private String cd;
    private String name;
    private String ref1;
    private String ref2;
    private Integer ref3;
    private String ref4;
    private String ref5;
    private String defineName;
    private Integer outputSeq;
    private String startDate;
    private String remark;
    private String whereTxt;
    private String effectiveDateCheck;
}