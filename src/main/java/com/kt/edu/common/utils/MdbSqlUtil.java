package com.kt.edu.common.utils;

import com.kt.edu.baseinfo.constants.BiqueryConstants;

/**
 * MDB BI_SCYSCD SQL
 */
public class MdbSqlUtil {

    /**
     * ssql 파라미터를 여러개 구분자로 병합하여 전달된 경우 조건문 sql 작성을 위한 사전체크
     * @param vals
     * @param colNm
     * @param finalSql
     * @return
     */
    public static String getWhereSql(String vals, String colNm, String finalSql) {
        String whereSql = "";
        if(vals.indexOf(BiqueryConstants.MDB_DELIM_IDX) != -1){
            whereSql = setIn(vals, colNm);
        }
        else {
            whereSql = finalSql;
        }
        return whereSql;
    }

    /**
     * sql 파라미터를 여러개 구분자로 병합하여 전달된 경우 조건문 sql 작성
     * @param vals
     * @param colNm
     * @return
     */
    public static String setIn(String vals, String colNm) {
        StringBuilder sbTemp = new StringBuilder(500);
        String[] sTemp = vals.split(BiqueryConstants.MDB_DELIM);
        for (int i = 0;i < sTemp.length; i++) {
            sbTemp.append("'" + sTemp[i] + "',");
        }
        return colNm + " IN (" + sbTemp.deleteCharAt(sbTemp.length() - 1).toString() + ")";
    }
}