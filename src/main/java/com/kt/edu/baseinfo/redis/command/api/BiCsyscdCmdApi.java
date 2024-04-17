/**************************************************************************************
 * ICIS version 1.0
 *
 *  Copyright ⓒ 2022 kt/ktds corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 *************************************************************************************/

package com.kt.edu.baseinfo.redis.command.api;

import com.kt.edu.baseinfo.comcd.exception.EduException;
import com.kt.edu.baseinfo.redis.command.controller.BiCsyscdCmdCntr;
import com.kt.edu.baseinfo.redis.command.payload.in.BiCsyscdInCmdPyld;
import com.kt.edu.baseinfo.redis.command.payload.in.dto.BiCsyscdInCmdDto;
import com.kt.edu.baseinfo.redis.command.payload.out.BiCsyscdOutCmdPyld;
import com.kt.edu.baseinfo.redis.command.payload.out.dto.BiCsyscdOutCmdDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Redis", description = "Redis 기준정보 데이터")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class BiCsyscdCmdApi {

    private final BiCsyscdCmdCntr biCsyscdCmdCntr;

    @PostMapping("/update/grpid")
    public BiCsyscdOutCmdPyld updateCsyscdGrpid(@RequestBody BiCsyscdInCmdPyld inPyld) {
        List<BiCsyscdInCmdDto> list = inPyld.getInDs();
        if (list.isEmpty()) {
            throw new EduException("MCSNE1010", List.of("입력값이 없습니다."));
        }
        Integer result = biCsyscdCmdCntr.updateCsyscdGrpid(list.get(0).getGrpId(), list);
        return BiCsyscdOutCmdPyld.builder().outDs(BiCsyscdOutCmdDto.builder().updateCnt(result).build()).build();
    }

    @PostMapping("/update/grpid-cd")
    public BiCsyscdOutCmdPyld updateCsyscdGrpidCd(@RequestBody BiCsyscdInCmdPyld inPyld) throws Exception {
        List<BiCsyscdInCmdDto> list = inPyld.getInDs();
        if (list.isEmpty()) {
            throw new EduException("MCSNE1010", List.of("입력값이 없습니다."));
        }
        Integer result = biCsyscdCmdCntr.updateCsyscdGrpidCd(list.get(0).getGrpId(), list.get(0).getCd(), list);
        return BiCsyscdOutCmdPyld.builder().outDs(BiCsyscdOutCmdDto.builder().updateCnt(result).build()).build();
    }

    @PostMapping("/delete/grpid")
    public void deleteCsyscdGrpid(@RequestParam String grpId) {
        biCsyscdCmdCntr.deleteCsyscdGrpid(grpId);
    }

    @PostMapping("/delete/grpid-cd")
    public void deleteCsyscdGrpidCd(@RequestParam String grpId, @RequestParam String cd) {
        biCsyscdCmdCntr.deleteCsyscdGrpidCd(grpId, cd);
    }
}
