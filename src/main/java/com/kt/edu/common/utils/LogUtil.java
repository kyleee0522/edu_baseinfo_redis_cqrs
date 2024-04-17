package com.kt.edu.common.utils;


import java.util.ArrayList;
import java.util.List;

import com.kt.edu.common.log.CustomHeaderDto;
import org.apache.logging.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Common Logging 일반 적인 로깅
 */

public class LogUtil {

    private static Logger log = LoggerFactory.getLogger(LogUtil.class);

    public static void trace(CustomHeaderDto header, String format, Object... argument) {
        MDC.put("custom.header", JsonUtil.toJson(header));
        logging(Level.TRACE, format, argument);
    }

    public static void debug(CustomHeaderDto header, String format, Object... argument) {
        MDC.put("custom.header", JsonUtil.toJson(header));
        logging(Level.DEBUG, format, argument);
    }

    public static void info(CustomHeaderDto header, String format, Object... argument) {
        MDC.put("custom.header", JsonUtil.toJson(header));
        logging(Level.INFO, format, argument);
    }

    public static void warn(CustomHeaderDto header, String format, Object... argument) {
        MDC.put("custom.header", JsonUtil.toJson(header));
        logging(Level.WARN, format, argument);
    }

    public static void error(CustomHeaderDto header, String format, Object... argument) {
        MDC.put("custom.header", JsonUtil.toJson(header));
        logging(Level.ERROR, format, argument);
    }

    public static void error(CustomHeaderDto header, String format, Throwable t) {
        MDC.put("custom.header", JsonUtil.toJson(header));
        log.error(format, t);
    }

    public static void trace(String format, Object... argument) {
        logging(Level.TRACE, format, argument);
    }

    public static void debug(String format, Object... argument) {
        logging(Level.DEBUG, format, argument);
    }

    public static void info(String format, Object... argument) {
        logging(Level.INFO, format, argument);
    }

    public static void warn(String format, Object... argument) {
        logging(Level.WARN, format, argument);
    }

    public static void error(String format, Object... argument) {
        logging(Level.ERROR, format, argument);
    }

    public static void error(String format, Throwable t) {
        log.error(format, t);
    }


    public static boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    public static boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public static boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    public static boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    public static boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }


    /**
     * debug  로그
     *
     * @param format
     * @param argument
     */
    public static void logging(Level level, String format, Object... argument) {

        // 2. package 정보
        String packageInfo = getPackageInfo();

        Object[] modifyArgument = argument;

        if (level == Level.TRACE) {
            log.trace(format, modifyArgument);
        } else if (level == Level.DEBUG) {
            log.debug(format, modifyArgument);
        } else if (level == Level.INFO) {
            log.info(format, modifyArgument);
        } else if (level == Level.WARN) {
            log.warn(format, modifyArgument);
        } else if (level == Level.ERROR) {
            log.error(format, modifyArgument);
        }
    }

    private static String getPackageInfo() {
        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        String privClass = stackTraceElements[3].getClassName();
        return privClass;
    }

    /**
     * 마스킹 처리 json 타입에 String 변수 인지 또는 객체인지 확인한다.
     *
     * @param argument
     * @return
     */
    private static List<String> isJsonArgument(Object[] argument) {

        if (argument == null) {
            return new ArrayList<String>();
        }

        List<String> argumentList = new ArrayList<String>();
        if (argument.length > 0) {
            for (int i = 0; i < argument.length; i++) {
                String jsonData = "";
                if (argument[i] != null) {
                    if (argument[i].getClass().getName().equals("java.lang.String")) { // Json 타입을 String 변한한 경우
                        jsonData = argument[i].toString();
                    } else { // 객체일경우
                        jsonData = JsonUtil.toJson(argument[i]);
                    }
                }
                argumentList.add(jsonData);

            }
        }
        return argumentList;
    }

}