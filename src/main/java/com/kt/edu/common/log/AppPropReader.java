package com.kt.edu.common.log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.IOException;

public class AppPropReader {

    private static String APP_NAME = ""; //null;
    private static String APP_TYPE = "ONLINE"; //null;
    private static String WORKFLOW_NAME = null;
    private static String CMMN_HEADER = null;
    private static String GLOBAL_NO = null;

    public static String getAppName() {
        if (APP_NAME == null || APP_NAME.equals("")) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            try {
                ClassLoader classLoader = AppPropReader.class.getClassLoader();
                File file = new File(classLoader.getResource("application.yml").getFile());
                JsonNode jsonNode = mapper.readTree(file);
                APP_NAME = jsonNode.at("/spring/application/name").toString().replaceAll("\"", "").replaceAll("'", "");
            } catch (IOException e) {
                APP_NAME = "";
            }
        }

        return APP_NAME;
    }

    public static String getAppType() {
        if (APP_TYPE == null || APP_TYPE.equals("")) {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            try {
                ClassLoader classLoader = AppPropReader.class.getClassLoader();
                File file = new File(classLoader.getResource("application.yml").getFile());
                JsonNode jsonNode = mapper.readTree(file);
                APP_TYPE = jsonNode.at("/app-info/app-type").toString().replaceAll("\"", "").replaceAll("'", "");
            } catch (IOException e) {
                APP_TYPE = "";

            }
        }
        return APP_TYPE;
    }

    public static String getWorkflowName() {
        if (WORKFLOW_NAME == null) {
            WORKFLOW_NAME = System.getenv("WORKFLOW_NAME");
            if (WORKFLOW_NAME == null) {
                WORKFLOW_NAME = System.getProperty("WORKFLOW_NAME");
            }
        }
        return WORKFLOW_NAME;
    }

    public static String getCmmnHeader() {
        if (CMMN_HEADER == null) {
            String encCmmnHeader = System.getenv("CMMN_HEADER");
            if (encCmmnHeader == null) {
                encCmmnHeader = System.getProperty("CMMN_HEADER");
            }

            if (encCmmnHeader != null) {
                byte[] decCmmnHeader = Base64Utils.decodeFromString(encCmmnHeader);
                CMMN_HEADER = new String(decCmmnHeader);
            }
        }
        return CMMN_HEADER;
    }

    public static String getGlobalNo() {
        if (GLOBAL_NO == null) {
            GLOBAL_NO = System.getenv("GLOBAL_NO");
            if (GLOBAL_NO == null) {
                GLOBAL_NO = System.getProperty("GLOBAL_NO");
            }
        }
        return GLOBAL_NO;
    }

}