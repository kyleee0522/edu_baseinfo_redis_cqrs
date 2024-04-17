/**************************************************************************************
 * ICIS version 1.0
 *
 *  Copyright ⓒ 2022 kt/ktds corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  Integerended publication of such software.
 *************************************************************************************/

package com.kt.edu.baseinfo.redis.command.controller;


import java.util.List;

import com.kt.edu.baseinfo.redis.command.payload.in.dto.BiCsyscdInCmdDto;


import com.kt.edu.baseinfo.redis.command.service.BiCsyscdCmdSvc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class BiCsyscdCmdCntr {

	private final BiCsyscdCmdSvc biCsyscdCmdSvc;

	public Integer updateCsyscdGrpid(final String grpId, final List<BiCsyscdInCmdDto> list) {
		return biCsyscdCmdSvc.updateCsyscdGrpid(grpId, list);
	}

	public Integer updateCsyscdGrpidCd(final String grpId, final String cd, final List<BiCsyscdInCmdDto> list) throws Exception {
		return biCsyscdCmdSvc.updateCsyscdGrpidCd(grpId, cd, list);
	}

	public void deleteCsyscdGrpid(final String grpId) {
		biCsyscdCmdSvc.deleteCsyscdGrpid(grpId);
	}

	public void deleteCsyscdGrpidCd(final String grpId, final String cd) {
		biCsyscdCmdSvc.deleteCsyscdGrpidCd(grpId, cd);
	}

}
