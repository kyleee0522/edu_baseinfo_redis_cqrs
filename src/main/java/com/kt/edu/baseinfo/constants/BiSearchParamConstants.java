package com.kt.edu.baseinfo.constants;

public class BiSearchParamConstants {
    /*
     * LOB 코드, 미완료오더 전체조회시 해당 ordInfo 의 LOOP기준
     * 기준정보 : BI
     * 유선공통 : WC
     * 일반전환 : PP
     * 정보통신 : IC
     * 기업인터넷 : EI
     * 인터넷응용 : IA
     * 지능망 : IN
     * 위성통신(기타) : ET
     */
    public final static String[] LOB_CD_PARAM = {"IC","EI","IA","IN","ET"};
    /*
     * LOB 코드, 미완료오더 전체조회시 해당 ordInfo 의 LOOP기준
     * 기준정보 : 01
     * 유선공통 : 02
     * 일반전환 : 03
     * 정보통신 : 04
     * 기업인터넷 : 05
     * 인터넷응용 : 06
     * 지능망 : 07
     * 위성통신(기타) : 08
     */
    public final static String[] LOB_NUM_PARAM = {"04","05","06","07","08"};


    /*
     * 미완료오더 계약구분 SA_CD 참조값
     */
    public final static String RETV_LOB_IC_LOB_ID_916 = "916";
    public final static String RETV_LOB_IC_LOB_ID_923 = "923";
    public final static String RETV_LOB_IA_LOB_ID_915 = "915";
    public final static String RETV_LOB_IN_LOB_ID_912 = "912";
    public final static String RETV_LOB_IN_LOB_ID_913 = "913";
    public final static String RETV_LOB_EI_LOB_ID_911 = "911";
    public final static String RETV_LOB_EI_LOB_ID_917 = "917";
    public final static String RETV_LOB_ET_LOB_ID_914 = "914";

    /* 정보통신 */
    public final static String RETV_LOB_IC_MENU_PROD_TYPE_CD = "'E'";
    public final static String RETV_LOB_IC_MENU_PROD_TYPE_CD_916 = "'E'";
    public final static String RETV_LOB_IC_MENU_PROD_TYPE_CD_923 = "'E'";
    /* 정보통신 */
    public final static String RETV_LOB_IC_SA_CD_1 = "'0653','0656','0730','0731','0735','079A','0659'";
    public final static String RETV_LOB_IC_SA_CD_916 = "'0653','0656','0730','0731','0735','079A','0659'";
    /* 전용회선 */
    public final static String RETV_LOB_IC_SA_CD_2 = "'0615','0616','0618','0640','06A0','0632'";
    public final static String RETV_LOB_IC_SA_CD_923 = "'0615','0616','0618','0640','06A0','0632'";
    /* 정보통신 */

    /* 기업인터넷 */
    public final static String RETV_LOB_EI_MENU_PROD_TYPE_CD = "'G'";
    public final static String RETV_LOB_EI_MENU_PROD_TYPE_CD_917 = "'G'";
    public final static String RETV_LOB_EI_MENU_PROD_TYPE_CD_911 = "'G'";
    /* 기업인터넷 */
    public final static String RETV_LOB_EI_SA_CD_1 = "'0552','0553','0554','0570'";
    public final static String RETV_LOB_EI_SA_CD_917 = "'0552','0553','0554','0570'";
    /* WiFi */
    public final static String RETV_LOB_EI_SA_CD_2 = "'0843','0844','0847','0860','0862','097H'";
    public final static String RETV_LOB_EI_SA_CD_911 = "'0843','0844','0847','0860','0862','097H'";
    /* 기업인터넷 */

    /* 인터넷응용 */
    public final static String RETV_LOB_IA_MENU_PROD_TYPE_CD = "'H'";
    public final static String RETV_LOB_IA_MENU_PROD_TYPE_CD_915 = "'H'";
    /* 인터넷응용 */
    public final static String RETV_LOB_IA_SA_CD_1 		= "'CS10'";
    public final static String RETV_LOB_IA_SA_CD_915 	= "'CS10'";
    /* 인터넷응용 */

    /* 지능망 */
    /* 국내지능망 */
    public final static String RETV_LOB_IN_MENU_PROD_TYPE_CD_1 = "'C','G'";
    public final static String RETV_LOB_IN_MENU_PROD_TYPE_CD_912 = "'C','G'";
    /* 국제지능망 */
    public final static String RETV_LOB_IN_MENU_PROD_TYPE_CD_2 = "'D'";
    public final static String RETV_LOB_IN_MENU_PROD_TYPE_CD_913 = "'D'";
    /* 지능망 */

    /* 기타 */
    public final static String RETV_LOB_ET_MENU_PROD_TYPE_CD_1 = "'D','E','F'";
    public final static String RETV_LOB_ET_MENU_PROD_TYPE_CD_914 = "'D','E','F'";
    /* 기타 */
}