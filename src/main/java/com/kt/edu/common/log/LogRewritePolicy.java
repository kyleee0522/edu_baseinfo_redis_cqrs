package com.kt.edu.common.log;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.message.FormattedMessage;
import org.apache.logging.log4j.message.SimpleMessage;

import com.fasterxml.jackson.core.io.JsonStringEncoder;

@Plugin(name = "LogRewritePolicy", category = "Core", elementType = "rewritePolicy", printObject = true)
public final class LogRewritePolicy implements RewritePolicy {

    @Override
    public LogEvent rewrite(final LogEvent event) {
        Log4jLogEvent.Builder builder = new Log4jLogEvent.Builder();

        SimpleMessage message = new SimpleMessage(write(event));

        builder.setLoggerName(event.getLoggerName());
        builder.setMarker(event.getMarker());
        builder.setLoggerFqcn(event.getLoggerFqcn());
        builder.setMessage(message);
        builder.setThrown(event.getThrown());
        builder.setContextStack(event.getContextStack());
        builder.setThreadName(event.getThreadName());
        builder.setSource(event.getSource());
        builder.setTimeMillis(event.getTimeMillis());

        return builder.build();
    }

    @PluginFactory
    public static LogRewritePolicy createPolicy() {
        return new LogRewritePolicy();
    }

    public String write(LogEvent event) {
        try {

            String format = event.getMessage().getFormat();
            Object[] argument = event.getMessage().getParameters();
            String header = event.getContextData().getValue("header");
            String globalNo = event.getContextData().getValue("globalNo");
            String customHeader = event.getContextData().getValue("custom.header");

            String transactionId = event.getContextData().getValue("transaction.id");
            String traceId = event.getContextData().getValue("trace.id");
            String errorId = event.getContextData().getValue("error.id");

            if(transactionId==null) {
                transactionId = "";
            }
            if(traceId==null) {
                traceId = "";
            }
            if(errorId==null) {
                errorId = "";
            }

            String type = AppPropReader.getAppType();
            if(type==null) {
                type = event.getContextData().getValue("type");
            }
            if(header==null) {
                header = AppPropReader.getCmmnHeader();
            }

            header = compositHeader(header,customHeader);

            if(globalNo==null) {
                globalNo = AppPropReader.getGlobalNo();
                if(globalNo==null) {
                    globalNo = "";
                }
            }

            String workFlowName = AppPropReader.getWorkflowName();
            if(workFlowName==null) {
                workFlowName = "";
            }

            String appName = AppPropReader.getAppName();
            String category = "DEBUG";
            String escapeMessage = "";
            if (format != null) {

                // format에 ctg가 있으면 공통 로그
                if (format.startsWith("[CTG:")) {
                    category = format.substring(5, format.indexOf("]"));
                    String saFormat = format.substring(format.indexOf("]") + 1);
                    if (argument != null && argument.length > 0) {
                        escapeMessage = encodeMessage(getFormattedMessage(saFormat, argument));
                    } else {
                        escapeMessage = encodeMessage(saFormat);
                    }
                } else {
                    if (argument != null && argument.length > 0) {
                        escapeMessage = encodeMessage(getFormattedMessage(format, argument));
                    } else {
                        escapeMessage = encodeMessage(format);
                    }
                }

            } else {
                if (argument != null && argument.length > 0) {
                    escapeMessage = encodeMessage(getCompositeMessage(argument));
                } else {
                    escapeMessage = "";
                }
            }

            if (StringUtils.isBlank(type)) {
                type = "SYS";
                category = "SYS";
            }

            if (header == null) {
                return String.format(
                        "{\"SERVICE\":\"%s\",\"TYPE\":\"%s\",\"CATEGORY\":\"%s\",\"WORKFLOW\":\"%s\",\"GLOBAL_NO\":\"%s\",\"TRANSACTION_ID\":\"%s\",\"TRACE_ID\":\"%s\",\"ERROR_ID\":\"%s\",\"DATE\":\"%s\",\"SOURCE\":\"%s\",\"LOG_LEVEL\":\"%s\",\"MESSAGE\":\"%s\"}",
                        appName, type, category, workFlowName, globalNo, transactionId, traceId, errorId, DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"),
                        getResourceInfo(event.getSource()), event.getLevel().toString(), escapeMessage);
            }

            return String.format(
                    "{\"SERVICE\":\"%s\",\"TYPE\":\"%s\",\"CATEGORY\":\"%s\",\"WORKFLOW\":\"%s\",\"GLOBAL_NO\":\"%s\",\"TRANSACTION_ID\":\"%s\",\"TRACE_ID\":\"%s\",\"ERROR_ID\":\"%s\",\"DATE\":\"%s\",\"SOURCE\":\"%s\",\"LOG-LEVEL\":\"%s\",\"HEADER\":%s,\"MESSAGE\":\"%s\"}",
                    appName, type, category, workFlowName, globalNo, transactionId, traceId, errorId,
                    DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"), getResourceInfo(event.getSource()),
                    event.getLevel().toString(), header, escapeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getCompositeMessage(Object[] argument) {
        if (argument == null || argument.length == 0) {
            return "";
        }

        String message = "";
        if (argument.length > 0) {
            for (int i = 0; i < argument.length; i++) {
                if (argument[i] != null) {
                    message = message.concat(argument[i].toString());
                }
            }
        }
        return message;
    }

    public String getPackageInfo(StackTraceElement stackTraceElement) {
        String privClass = getPackageInfo();
        if (privClass == null) {
            privClass = stackTraceElement.getClassName();
        }
        return privClass;
    }

    private static String getPackageInfo() {

        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        StackTraceElement stackTraceElement = null;
        for (int i = 0; i < stackTraceElements.length; i++) {
            if ("com.kt.edu.baseinfo.utils.LogUtil".equals(stackTraceElements[i].getClassName())
                    && "logging".equals(stackTraceElements[i].getMethodName())) {
                stackTraceElement = stackTraceElements[i + 2];
                break;
            }
        }
        if (stackTraceElement == null) {
            return null;
        }
        String privClass = stackTraceElement.getClassName();
        return privClass;
    }

    private String encodeMessage(String message) {
        JsonStringEncoder encoder = JsonStringEncoder.getInstance();
        char[] escapedJson = encoder.quoteAsString(message);
        String escapeMessage = String.valueOf(escapedJson);
        return escapeMessage;
    }

    private String getFormattedMessage(String format, Object[] params) {
        FormattedMessage message = new FormattedMessage(format, params);
        return message.getFormattedMessage();
    }

    /**
     * 로그파일을 남긴 소스 정보를 가져온다.
     *
     */
    private String getResourceInfo(StackTraceElement stackTraceElement) {

        String resourceInfo = "";
        String privClass = stackTraceElement.getClassName();
        privClass = privClass.substring(privClass.lastIndexOf(".") + 1);

        if ("LogUtil".equals(privClass)) {
            resourceInfo = getResourceInfo();
        } else {
            resourceInfo = String.format("%s.%s(%s)", privClass, stackTraceElement.getMethodName(),
                    stackTraceElement.getLineNumber());
        }

        return resourceInfo;
    }

    private static String getResourceInfo() {
        String resourceInfo = "";
        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        StackTraceElement stackTraceElement = null;
        for (int i = 0; i < stackTraceElements.length; i++) {
            if ("com.kt.edu.baseinfo.utils.LogUtil".equals(stackTraceElements[i].getClassName())
                    && "logging".equals(stackTraceElements[i].getMethodName())) {
                stackTraceElement = stackTraceElements[i + 2];
                break;
            }
        }

        if (stackTraceElement == null) {
            return "";
        }

        String privClass = stackTraceElement.getClassName();
        privClass = privClass.substring(privClass.lastIndexOf(".") + 1);
        resourceInfo = String.format("%s.%s(%s)", privClass, stackTraceElement.getMethodName(),
                stackTraceElement.getLineNumber());

        return resourceInfo;
    }


    private String compositHeader(String header, String customHeader) {
        if(StringUtils.isNotBlank(header) && StringUtils.isBlank(customHeader)) {
            return header;
        }
        else if(StringUtils.isBlank(header) && StringUtils.isNotBlank(customHeader)) {
            return customHeader;
        }
        else if(StringUtils.isBlank(header) && StringUtils.isBlank(customHeader)) {
            return null;
        }
        String concatHeader = customHeader.substring(1,customHeader.length()-1);
        String originHeader = header.substring(0,header.length()-1);
        return String.format("%s,%s}", originHeader, concatHeader);
    }

}