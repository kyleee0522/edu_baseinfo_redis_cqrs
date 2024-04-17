package com.kt.edu.baseinfo.redis.query.repository.sql;



import com.kt.edu.common.utils.MdbSqlUtil;
import com.kt.edu.baseinfo.redis.query.payload.in.dto.BiCsyscdInQryDto;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;


/**
 * MDB BI_SCYSCD SQL
 */

@Slf4j
public class BiCsyscdDynamicQrySql {

    public static final String CSYSCD_MAIN_SQL = " GRP_ID as grpId, CD as cd, NAME as name, REF1 as ref1, REF2 as ref2, REF3 as ref3, REF4 as ref4, REF5 as ref5, DEFINE_NAME as defineName, OUTPUT_SEQ as outputSeq, START_DATE as startDate, END_DATE as endDate, REMARK as remark ";
    public static final String CSYSCD_MAIN_SQL_FROM = " BI_CSYSCD ";
    public static final String CSYSCD_WHERE_GRPID = "GRP_ID = #{grpId}";
    public static final String CSYSCD_WHERE_CD = "CD = #{cd}";
    public static final String CSYSCD_WHERE_NAME = "NAME = #{name}";
    public static final String CSYSCD_WHERE_REF1 = "REF1 = #{ref1}";
    public static final String CSYSCD_WHERE_REF2 = "REF2 = #{ref2}";
    public static final String CSYSCD_WHERE_REF3 = "REF3 = #{ref3}";
    public static final String CSYSCD_WHERE_REF4 = "REF4 = #{ref4}";
    public static final String CSYSCD_WHERE_REF5 = "REF5 = #{ref5}";
    public static final String CSYSCD_WHERE_DEFINENAME = "DEFINE_NAME = #{defineName}";
    public static final String CSYSCD_WHERE_OUTPUTSEQ = "OUTPUT_SEQ = #{outputSeq}";
    public static final String CSYSCD_WHERE_STARTDATE = "START_DATE = #{startDate}";
    public static final String CSYSCD_WHERE_REMARK = "REMARK = #{remark}";
    public static final String CSYSCD_WHERE_EFFECTIVE_DATE_CHECK = "TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN NVL(START_DATE,'19000101') AND NVL(END_DATE,'99999999')";

    //------------------------------------------------------------------------------------------------------------------------------------------------//

    public static final String CSYSORDTYPE_WHERE_GRPID = "A.GRP_ID = #{grpId}";

    public static final String CSYSORDTYPE_WHERE_SACD = "B.SA_CD = #{saCd}";

    public static final String CSYSORDTYPE_WHERE_CD = "A.CD = #{cd}";

    public static final String CSYSORDTYPE_WHERE_ORDCOMPLETEDTYPE = "B.ORD_COMPLETED_TYPE = #{ordCompletedType}";

    public static final String CSYSORDTYPE_WHERE_ORDMNGFLAG = "B.ORD_MNG_FLAG = #{ordMngFlag}";

    public static final String CSYSORDTYPE_WHERE_USERORDSEQ = "B.USER_ORD_SEQ = #{userOrdSeq}";

    public static final String CSYSORDTYPE_WHERE_ORDTYPECD = "B.ORD_TYPE_CD = #{ordTypeCd}";

    //------------------------------------------------------------------------------------------------------------------------------------------------//

    public static final String CSYSCD_CSYSORDTYPE_MAIN_SQL = "A.GRP_ID as grpId, B.SA_CD as saCd, A.CD as cd , B.ORD_COMPLETED_TYPE as ordCompletedType, B.ORD_MNG_FLAG as ordMngFlag, B.USER_ORD_SEQ as userOrdSeq, B.ORD_TYPE_CD as ordTypeCd, A.NAME as name, B.OUTPUT_SEQ as outputSeq, B.DLL_NAME AS dllName";

    public static final String CSYSCD_CSYSORDTYPE_MAIN_SQL_FROM = "BI_CSYSCD A, BI_CSYSORDTYPE B";

    public static final String CSYSCD_CSYSORDTYPE_MAIN_SQL_WHERE = "A.CD = B.ORD_TYPE_CD ";

    //------------------------------------------------------------------------------------------------------------------------------------------------//

    public static final String CSYSCD_CSYSPRODCSCD_MAIN_SQL = "A.CD as cd, B.SA_CD as saCd, B.SO_ITEM_ID as soItemId, B.FCTR_1 as fctr1, B.FCTR_2 as fctr2, B.FCTR_3 as fctr3, B.FCTR_4 as fctr4, B.FCTR_5 as fctr5, A.REF1 as ref1, A.REF2 as ref2, A.REF3 as ref3, A.GRP_ID as grpId, A.NAME as name, OUTPUT_SEQ as outputSeq ";

    public static final String CSYSCD_CSYSPRODCSCD_MAIN_SQL_FROM = "BI_CSYSCD A, BI_CSYSPRODVSCD B";

    public static final String CSYSCD_CSYSPRODCSCD_MAIN_SQL_WHERE = "A.GRP_ID = B.SO_ITEM_ID AND A.CD = B.CD";

    //------------------------------------------------------------------------------------------------------------------------------------------------//

    public static final String CSYSPRODVSCD_WHERE_SOITEMID = "B.SO_ITEM_ID = #{soItemId}";

    public static final String CSYSPRODVSCD_WHERE_FCTR1 = "B.FCTR_1 = #{fctr1}";

    public static final String CSYSPRODVSCD_WHERE_FCTR2 = "B.FCTR_2 = #{fctr2}";

    public static final String CSYSPRODVSCD_WHERE_FCTR3 = "B.FCTR_3 = #{fctr3}";

    public static final String CSYSPRODVSCD_WHERE_FCTR4 = "B.FCTR_4 = #{fctr4}";
    public static final String CSYSPRODVSCD_WHERE_FCTR5 = "B.FCTR_5 = #{fctr5}";
    public static final String CSYSPRODVSCD_WHERE_REF1 = "A.REF1 = #{ref1}";
    public static final String CSYSPRODVSCD_WHERE_REF2 = "A.REF2 = #{ref2}";
    public static final String CSYSPRODVSCD_WHERE_EFFECTIVE_DATE_CHECK = "TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN NVL(A.START_DATE,'19000101') AND NVL(A.END_DATE,'99999999')";

    //------------------------------------------------------------------------------------------------------------------------------------------------//

    public static final String CSYSCD_MPRDCD_MAIN_SQL = "A.GRP_ID, B.SA_CD, A.NAME";
    public static final String CSYSCD_MPRDCD_MAIN_SQL_FROM = "BI_CSYSCD A, BI_MPRDCD B";
    public static final String CSYSCD_MPRDCD_MAIN_SQL_WHERE = "A.CD = B.SA_CD";

    public static String findCsyscdSql(BiCsyscdInQryDto inDto) {

        String sql = new SQL() {{

            SELECT(CSYSCD_MAIN_SQL);

            FROM(CSYSCD_MAIN_SQL_FROM);

            if (StringUtils.isNotEmpty(inDto.getGrpId())) {

                WHERE(MdbSqlUtil.getWhereSql(inDto.getGrpId(), "GRP_ID", CSYSCD_WHERE_GRPID));

            }

            if (StringUtils.isNotEmpty(inDto.getCd())) {

                WHERE(MdbSqlUtil.getWhereSql(inDto.getCd(), "CD", CSYSCD_WHERE_CD));

            }

            if (StringUtils.isNotEmpty(inDto.getName())) {

                WHERE(CSYSCD_WHERE_NAME);

            }

            if (StringUtils.isNotEmpty(inDto.getRef1())) {

                WHERE(MdbSqlUtil.getWhereSql(inDto.getRef1(), "REF1", CSYSCD_WHERE_REF1));

            }

            if (StringUtils.isNotEmpty(inDto.getRef2())) {

                WHERE(MdbSqlUtil.getWhereSql(inDto.getRef2(), "REF2", CSYSCD_WHERE_REF2));

            }

            if (inDto.getRef3() != null) {

                WHERE(CSYSCD_WHERE_REF3);

            }

            if (StringUtils.isNotEmpty(inDto.getRef4())) {

                WHERE(MdbSqlUtil.getWhereSql(inDto.getRef4(), "REF4", CSYSCD_WHERE_REF4));

            }

            if (StringUtils.isNotEmpty(inDto.getRef5())) {

                WHERE(MdbSqlUtil.getWhereSql(inDto.getRef5(), "REF5", CSYSCD_WHERE_REF5));

            }

            if (StringUtils.isNotEmpty(inDto.getDefineName())) {

                WHERE(CSYSCD_WHERE_DEFINENAME);

            }

            if (inDto.getOutputSeq() != null) {

                WHERE(CSYSCD_WHERE_OUTPUTSEQ);

            }

            if (StringUtils.isNotEmpty(inDto.getStartDate())) {

                WHERE(CSYSCD_WHERE_STARTDATE);

            }

            if (StringUtils.isNotEmpty(inDto.getRemark())) {

                WHERE(CSYSCD_WHERE_REMARK);

            }

            if (StringUtils.isNotEmpty(inDto.getWhereTxt())) {

                WHERE(inDto.getWhereTxt());

            }

            if (StringUtils.isNotEmpty(inDto.getEffectiveDateCheck()) && "TRUE".equals(inDto.getEffectiveDateCheck().toUpperCase())) {

                WHERE(CSYSCD_WHERE_EFFECTIVE_DATE_CHECK);

            }

            // order by 추가 - 2023.12.04

            ORDER_BY("GRP_ID");

            //ORDER_BY("CD");

        }}.toString();


        // log.info("SQL 코드 사용 : " + sql);

        return sql;

    }


}