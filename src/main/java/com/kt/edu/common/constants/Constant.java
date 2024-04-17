package com.kt.edu.common.constants;

public class Constant {
    public static final String REQUEST_PATH = "/service_request";
    public static final String REQUEST_COMMON_HEADER_PATH = REQUEST_PATH + "/commonHeader";
    public static final String REQUEST_BIZ_HEADER_PATH = REQUEST_PATH + "/bizHeader";
    public static final String REQUEST_SA_SECURITY = REQUEST_PATH + "/saSecurity";
    public static final String REQUEST_PAYLOAD_NAMES = REQUEST_SA_SECURITY + "/payloadNames";

    public static final String RESPONSE_PATH = "/service_response";
    public static final String RESPONSE_COMMON_HEADER_PATH = RESPONSE_PATH + "/commonHeader";
    public static final String RESPONSE_BIZ_HEADER_PATH = RESPONSE_PATH + "/bizHeader";
    public static final String RESPONSE_SA_SECURITY = RESPONSE_PATH + "/saSecurity";
    public static final String RESPONSE_PAYLOAD_NAMES = RESPONSE_SA_SECURITY + "/payloadNames";

    // board 서비스에서 사용한 redis key
    public static final String PPON_CONT_KEY = "icis:samp::ppon:sa_cont:saContNo:";
    public static final String INETAPLCA_CONT_KEY = "icis:samp::inetaplca:sa_cont:saContNo:";
    public static final String INTELNET_CONT_KEY = "icis:samp::intelnet:sa_cont:saContNo:";
    public static final String ORDR_KEY = "icis-tr:sample::sample-api:ordr:ordrNo:";
    public static final String BOARD_KEY = "icis-tr:sample::sample-api:board:num:";
    public static final String BBS_KEY = "icis-tr:sample::sample-api:bbs:bbsNo:";
    public static final String NTCART_KEY = "icis-tr:sample::sample-api:ntcart:ntcartNo:";

    /**
     * SafeDB
     * k8s Internal Access URL : icis-safedb.sa-app.svc.cluster.local
     * k8s External Access URL : icis-safedb-sa-app.apps.ktis-console.c01-okd4.cz-tb.paas.kt.co.kr
     */
    public static String SAFEDB_AGENT_URL 	= "icis-safedb.sa-app.svc.cluster.local";
    public static String SAFEDB_USER_NAME	= "SAFEDB";				// SafeDB UserID
    public static String SAFEDB_TABLE_NAME	= "SAFEDB.POLICY";		// 암호화정책 테이블명


    // 양방향 암호화 알고리즘 (AES128)
    public static String ACCOUNT_NO 		= "KT_ACCOUNT_NO";		// 전사 계좌번호 암호화정책
    public static String BIO_NO 			= "KT_BIO_NO";			// 전사 바이오정보 암호화정책
    public static String CARD_NO 			= "KT_CARD_NO";			// 전사 카드번호 암호화정책
    public static String DRIVER_NO 			= "KT_DRIVER_NO";		// 전사 운정면허번호 암호화정책
    public static String ETC_2way_NO 		= "KT_ETC_2way_NO";		// 전사 기타항목 양방향 암호화정책
    public static String FINANCE_NO 		= "KT_FINANCE_NO";		// 전사 금융정보(카드번호,계좌번호) 대표 암호화정책
    public static String FOREIGNER_NO 		= "KT_FOREIGNER_NO";    // 전사 외국인등록번호 암호화정책
    public static String IDENTITY_NO 		= "KT_IDENTITY_NO";		// 전사 개인식별번호(주민번호, 운전면허정보, 외국인등록번호, 여권번호) 대표 암호화정책
    public static String JUMIN_NO 			= "KT_JUMIN_NO";		// 전사 주민번호 암호화정책
    public static String LOCATION_NO 		= "KT_LOCATION_NO";		// 전사 위치정보 암호화정책
    public static String PASSPORT_NO 		= "KT_PASSPORT_NO";		// 전사 여권번호 암호화정책
    public static String PASSWORD_2way_NO 	= "KT_PASSWORD_2way_NO";// 전사 비밀번호 양방향 암호화정책


    // 일방향 암호화 알고리즘 (SHA256)
    public static String ETC 				= "KT_ETC";				// 전사 기타항목 일방향 암호화정책
    public static String PASSWORD_NO 		= "KT_PASSWORD_NO";		// 전사 비밀번호 일방향 암호화정책
    public static String RESERVED_1 		= "KT_RESERVED_1";		// 전사 예비정책1
    public static String RESERVED_2 		= "KT_RESERVED_2";		// 전사 예비정책2
    public static String RESERVED_3 		= "KT_RESERVED_3";		// 전사 예비정책3

    //Redis Key
    public static String FIELDENCRYP_KEY = "icis:gw-adm:esbmetacache:esbencryptfieldlist";               //필드 암호화 키
    //    public static String FIELDFILTERING_KEY = "icis:gw-adm:esbmetacache:esbfieldfilteringlist";     //필드필터링 키
    public static String FIELDFILTERING_KEY = "icis:gw-adm:esbmetacache:esbfieldfiltering:";     //필드필터링 키
    //    public static String CMMNCDMANG_KEY = "icis:gw-adm:esbmetacache:esbcdmanglist";     //공통 코드 키
    public static String CMMNCDMANG_KEY = "icis:gw-adm:esbmetacache:esbcdmang:";     //공통 코드 키
    public static String MASKING_KEY = "icis:gw-adm:esbmetacache:securmaskinglist";     //마스킹 키

}