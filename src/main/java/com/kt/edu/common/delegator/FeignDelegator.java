package com.kt.edu.common.delegator;

import com.kt.edu.common.payload.CommonHeader;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
public class FeignDelegator {

    @Autowired
    private Environment env;

    //@Autowired
    //private AppUtil appUtil;

    @Value("${app-info.node-ip:}")
    private String clntIp;

    @Value("${app-info.pod-name:}")
    private String hostId;

    @Value("${app-info.chnl-type:}")
    private String chnlType;

    @Value("${spring.profiles.active:dev}")
    private String profile;

    public CommonHeader makeCommonHeader(CommonHeader commonHeader, String svcName) {
        CommonHeader commonHeaderSource = null;
        CommonHeader commonHeaderTarget = new CommonHeader();

        if (commonHeader == null) {
            commonHeaderSource = makeCommonHeader(svcName);
        }
        else {
            commonHeaderSource = commonHeader;
        }

        BeanUtils.copyProperties(commonHeaderSource, commonHeaderTarget);
        commonHeaderTarget.setSvcName(svcName);

        return commonHeaderTarget;
    }

    public CommonHeader makeCommonHeader(String svcName) {
        return CommonHeader.builder().appName("Feign").svcName(svcName).build();
        /*return CommonHeader.builder().appName(appUtil.getAppName()).fnName("service").svcName(svcName).fnCd("")
                .langCode("").tokenId("").lockType("").lockId("").lockTimeSt("").businessKey("").arbitraryKey("")
                .resendFlag("").phase("").logPoint("OM").responseType("").responseBasc("").responseCode("")
                .responseDtal("").responseDtal("").responseLogcd("").responseSystem("").responseTitle("")
                .globalNo(appUtil.getGlobalNo()).chnlType(appUtil.getChnlType()).trFlag(appUtil.getReqTrFlag())
                .trDate(appUtil.getTrDate()).trTime(appUtil.getTrTime()).clntIp(appUtil.getClntIp())
                .userId(appUtil.getUserId()).realUserId(appUtil.getUserId()).filler(appUtil.getNoSecureFiller())
                .orgId(appUtil.getOrgId()).srcId(appUtil.getSrcId()).curHostId(appUtil.getHostId())
                .lgDateTime(appUtil.getLgDateTime()).cmpnCd(appUtil.getCmpnCd()).build();*/
    }

}