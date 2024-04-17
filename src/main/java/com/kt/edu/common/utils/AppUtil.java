package com.kt.edu.common.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

//@RefreshScope
//@Component
public class AppUtil {

    /*private static final String REQ_TR_FLAG = "T";

    private static final String RES_TR_FLAG = "R";

    private static final String FILLER_SECURE = "E";

    private static final String FILLER_NOSECURE = "";

    private static final String SRC_ID = "SWAGGER";

    private static final String CMPN_CD = "KT";

    @Value("${app-info.app-name}")
    private String appName;

    @Value("${app-info.pod-name}")
    private String hostId;

    @Value("${app-info.chnl-type}")
    private String chnlType;

    @Value("${app-info.user-id}")
    private String userId;

    @Value("${app-info.org-id}")
    private String orgId;

    @Value("${app-info.node-ip}")
    private String clntIp;

    public String getAppName() {
        return appName;
    }

    public String getSecureFiller() {
        return AppUtil.FILLER_SECURE;
    }

    public String getNoSecureFiller() {
        return AppUtil.FILLER_NOSECURE;
    }


    public String getHostId() {
        return hostId;
    }

    public String getGlobalNo() {
        String sGlobalNo = this.getUserId() + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        // global No 중복 발생하여 랜덤 숫자 추가
        SecureRandom random = new SecureRandom();
        int rnd = random.nextInt(999999);
        sGlobalNo = String.format("%s%s", sGlobalNo, String.valueOf(rnd));
        // global No 중복 발생하여 랜덤 숫자 추가
        String globalNo = StringUtils.leftPad(sGlobalNo, 32, '0');
        return globalNo;
    }

    public String getChnlType() {
        return chnlType;
    }

    public String getUserId() {
        return userId;
    }

    public String getReqTrFlag() {
        return AppUtil.REQ_TR_FLAG;
    }

    public String getResTrFlag() {
        return AppUtil.RES_TR_FLAG;
    }


    public String getTrDate() {
        String trDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return trDate;
    }

    public String getTrTime() {
        String trTime = new SimpleDateFormat("HHmmssSSS").format(new Date());
        return trTime;
    }

    public String getOrgId() {
        return orgId;
    }

    public String getLgDateTime() {
        String lgDateTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return lgDateTime;
    }

    public String getSrcId() {
        return AppUtil.SRC_ID;
    }

    public String getClntIp() {
        return clntIp;
    }

    public String getCmpnCd() {
        return AppUtil.CMPN_CD;
    }
    */
}