package com.kt.edu.baseinfo.redis.query.payload.out;


import com.kt.edu.baseinfo.redis.query.payload.out.dto.BiCsyscdOutQryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BiCsyscdOutListQryPyld {
	private int listSize = 0;
	private List<BiCsyscdOutQryDto> outDs;
}
