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

package com.kt.edu.common.payload;

import lombok.*;

@Getter 
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonHeader {

    /**
	 * 어플리케이션이름
	 */
    private String appName;
    
    /**
	 * 서비스이름
	 */
    private String svcName;
    
    /**
	 * 오퍼레이션이름
	 */
    private String fnName;
    
    /**
	 * 기능코드
	 */
    private String fnCd;
    
    /**
	 * 거래고유번호
	 */
    private String globalNo;
    
    /**
	 * 채널구분
	 */
    private String chnlType;
    
    /**
	 * 환경구분
	 */
    private String envrFlag;
    
    /**
	 * 송수신FLAG
	 */
    private String trFlag;
    
    /**
	 * 송수신일자
	 */
    private String trDate;
    
    /**
	 * 송수신시간
	 */
    private String trTime;
    
    /**
	 * 클라이언트ip
	 */
    private String clntIp;
    
    /**
	 * 응답유형
	 */
    private String responseType;
    
    /**
	 * 응답코드
	 */
    private String responseCode;
    
    /**
	 * 응답구분코드
	 */
    private String responseLogcd;
    
    /**
	 * 응답타이틀
	 */
    private String responseTitle;
    
    /**
	 * 응답기본내역
	 */
    private String responseBasc;
    
    /**
	 * 응답상세내역
	 */
    private String responseDtal;
    
    /**
	 * 응답시스템
	 */
    private String responseSystem;
    
    /**
	 * 사용자아이디
	 */
    private String userId;
    
    /**
	 * 실사용자아이디
	 */
    private String realUserId;
    
    /**
	 * 필러
	 */
    private String filler;
    
    /**
	 * 사용자언어코드
	 */
    private String langCode;
    
    /**
	 * 조직ID
	 */
    private String orgId;
    
    /**
	 * Source ID
	 */
    private String srcId;
    
    /**
	 * Current Host ID
	 */
    private String curHostId;
    
    /**
	 * Logical Date & Time
	 */
    private String lgDateTime;
    
    /**
	 * Token Id
	 */
    private String tokenId;
    
    /**
	 * Company Code
	 */
    private String cmpnCd;
    
    /**
	 * Locking 유형
	 */
    private String lockType;
    
    /**
	 * Locking ID
	 */
    private String lockId;
    
    /**
	 * Locking Timestamp
	 */
    private String lockTimeSt;
    
    /**
	 * 비즈니스키
	 */
    private String businessKey;
    
    /**
	 * 임의키
	 */
    private String arbitraryKey;
    
    /**
	 * 재처리연동구분
	 */
    private String resendFlag;
    
    /**
	 * phase
	 */
    private String phase;
    
    private String logPoint;
}
