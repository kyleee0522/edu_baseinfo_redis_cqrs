package com.kt.edu.baseinfo.redis.command.repository;


import com.kt.edu.baseinfo.redis.command.payload.in.dto.BiCsyscdInCmdDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BiCsyscdCmdRepo {

    //
    @Update({
            "UPDATE BI_CSYSCD",
            "SET",
            "NAME = #{dto.name},",
            "REF1 = #{dto.ref1},",
            "REF2 = #{dto.ref2},",
            "REF3 = #{dto.ref3},",
            "REF4 = #{dto.ref4},",
            "REF5 = #{dto.ref5},",
            "DEFINE_NAME = #{dto.defineName},",
            "OUTPUT_SEQ = #{dto.outputSeq},",
            "START_DATE = #{dto.startDate},",
            "END_DATE = #{dto.endDate},",
            "REMARK = #{dto.remark}",
            "WHERE GRP_ID = #{grpId} AND CD = #{dto.cd}"
    })
    int updateCsyscd(@Param("grpId") String grpId, @Param("dto") BiCsyscdInCmdDto dto);


    // grpId에 해당하는 모든 데이터 삭제
    @Delete("DELETE FROM BI_CSYSCD WHERE GRP_ID = #{grpId}")
    int deleteByGrpId(@Param("grpId") String grpId);

    // grpId와 cd에 해당하는 특정 데이터 삭제
    @Delete("DELETE FROM BI_CSYSCD WHERE GRP_ID = #{grpId} AND CD = #{cd}")
    int deleteByGrpIdAndCd(@Param("grpId") String grpId, @Param("cd") String cd);
}