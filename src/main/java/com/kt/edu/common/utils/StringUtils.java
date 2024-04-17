package com.kt.edu.common.utils;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @FileName : StringUtils.java
 * @Project : ICIS Transformation Project
 * @Date : 2022. 12. 15.
 * @작성자 :
 * @변경이력 :
 * @프로그램 설명 : common-lang3의 StringUtils를 상속 받아서 StringUtils에서 제공하는 기능을 사용하며,
내부에서 String 기능을 처리 기능을 추가하면된다.
 */
@SuppressWarnings("all")
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static final String DEFAULT_DECIMAL_FORMAT = "###,###";

    private StringUtils() {
        // ignore..
    }

    public static final boolean notEquals(final CharSequence cs1, final CharSequence cs2) {
        return !equals(cs1, cs2);
    }

    public static final boolean notEqualsIgnoreCase(final CharSequence cs1, final CharSequence cs2) {
        return !equalsIgnoreCase(cs1, cs2);
    }

    public static final boolean notEqualsAny(final CharSequence cs1, final CharSequence... searchs) {
        return !equalsAny(cs1, searchs);
    }

    public static final boolean notEqualsAnyIgnoreCase(final CharSequence cs1, final CharSequence... searchs) {
        return !equalsAnyIgnoreCase(cs1, searchs);
    }


    /**
     *
     * @Method Name : decimalFormat
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 :
     * @Method 설명 : DecimalFormat을 이용해서 숫자의 포멧을 변경한다.
     * @param value
     * @return
     */
    public static final String decimalFormat(final long value) {
        return decimalFormat(DEFAULT_DECIMAL_FORMAT, value);
    }


    /**
     *
     * @Method Name : decimalFormat
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 :
     * @Method 설명 : DecimalFormat을 이용해서 숫자의 포멧을 변경한다.
     * @param pattern
     * @param value
     * @return
     */
    public static final String decimalFormat(final String pattern, final long value) {
        DecimalFormat formatter = new DecimalFormat(pattern);
        return formatter.format(value);
    }

    /**
     *
     * @Method Name : printStack
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 :
     * @Method 설명 : Exception을 String으로 변환한다.
     * @param t
     * @return
     */
    @SuppressWarnings("all")
    public static final String printStack(final Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    /**
     *
     * @Method Name : convertCamelcaseToUnderscore
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 :
     * @Method 설명 : 카멜 표기법을 언더바 표기법 대문자로 변환한다.
     * @param str
     * @return
     */
    public static final String convertCamelcaseToUnderscore(final String str) {
        return convertCamelcaseToUnderscore(str, true);
    }


    /**
     *
     * @Method Name : convertCamelcaseToUnderscore
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 :
     * @Method 설명 : 카멜 표기법을 언더바 표기법으로 변환한다.,  모두 대문자 처리 여부
     * @param str
     * @param isUpperCase
     * @return
     */
    public static final String convertCamelcaseToUnderscore(final String str, final boolean isUpperCase) {
        if (StringUtils.isBlank(str)) {
            throw new IllegalArgumentException("변환 할 값이 없습니다");
        }
        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";
        String value = StringUtils.replacePattern(str, regex, replacement);
        if (isUpperCase) {
            return StringUtils.upperCase(value);
        } else {
            return StringUtils.lowerCase(value);
        }
    }


    /**
     *
     * @Method Name : converUnderscoreToCamelcase
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 :
     * @Method 설명 : 언더바 표기법을 카멜 표기법으로 변환한다.
     * @param str
     * @return
     */
    public static final String converUnderscoreToCamelcase(final String str) {
        if (StringUtils.isBlank(str)) {
            throw new IllegalArgumentException("변환 할 값이 없습니다");
        }
        String result = StringUtils.lowerCase(StringUtils.trim(str));
        if (StringUtils.contains(result, "_")) {
            StringBuffer output = new StringBuffer();
            String[] strs = StringUtils.split(result, "_");
            for (int i = 0; i < strs.length; i++) {
                if (i != 0) {
                    output.append(StringUtils.capitalize(strs[i]));
                } else {
                    output.append(strs[i]);
                }
            }
            return output.toString();
        } else {
            return result;
        }
    }


    /**
     *
     * @Method Name : generateRandomString
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 :
     * @Method 설명 : 무작위 문자열을 발생시킨다.
     * @param number
     * @return
     */
    public static final String generateRandomString(final int number) {
        StringBuffer temp = new StringBuffer();
        Random rnd = new Random();
        for (int i = 0; i < number; i++) {
            int rIndex = rnd.nextInt(3);
            switch (rIndex) {
                case 0:
                    // a-z
                    temp.append((char) ((rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) ((rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
                default :
                    temp.append("");
                    break;
            }

        }
        return temp.toString();
    }


    /**
     *
     * @Method Name : convertNull2Nbsp
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 :
     * @Method 설명 : Object null check
     * @param src
     * @return
     */
    public static String convertNull2Nbsp(Object src) {
        return convertNull2Nbsp(src, "");
    }

    /**
     *
     * @Method Name : convertNull2Nbsp
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 :
     * @Method 설명 : Object null check default return value
     * @param src
     * @param returnVal
     * @return
     */
    public static String convertNull2Nbsp(Object src, String returnVal) {
        try {
            if (src == null || "".equals(src)) {
                return returnVal;
            } else {
                return src.toString();
            }
        } catch (Exception e) {
            return (String) src;
        }
    }

    /**
     *
     * @Method Name : convertMessage
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 :
     * @Method 설명 : 문자열 중 '#','#1".. 의 특정문자를 배열의 문자열로 바꾼다.
     * @param codeMessage 변환할 문자열
     * @param str [] 특정문자를 변경할 문자
     * @return
     */
    public static String convertMessage(String codeMessage, String[] str) {
        String propMessage = convertNull2Nbsp(codeMessage);
        StringBuffer message = new StringBuffer(propMessage);
        for (int i = 0; i < str.length; i++) {
            if (i == 0) {
                message = new StringBuffer(propMessage.replaceAll("#", str[0]));
            } else {
                message = new StringBuffer(message.toString().replaceAll(str[0] + i, str[i]));
            }
        }
        return message.toString();
    }

    /**
     *
     * @Method Name : getDocumentNum
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 :
     * @Method 설명 : 랜덤한 숫자를 자리수에 맞게 리턴한다.
     * @param n
     * @param len
     * @return
     */
    public static String getDocumentNum(int n, int len) {
        String rtnNum = null;

        Random ran = new Random();
        int num = ran.nextInt(n);

        if (num > 0) {
            rtnNum = String.valueOf(num);

            for (int i = rtnNum.length(); i < len; i++) {
                rtnNum = "0" + rtnNum;
            }
        } else {
            num = num * -1;

            rtnNum = String.valueOf(num);

            for (int i = rtnNum.length(); i < len; i++) {
                rtnNum = "0" + rtnNum;
            }
        }

        return rtnNum;
    }

    /**
     * SHUB 패스워드 필터링 --> 이 필터링 값도 기왕이면 DB에서 호출해서 사용하는걸로....
     *
     * @param shubpw
     * @return
     */

    /**
     *
     * @Method Name : shubPwFilter
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 : 특수문자 필터
     * @Method 설명 :
     * @param spcText
     * @return
     */
    public static String spcTextFilter(String spcText) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < spcText.length(); i++) {
            if (spcText.charAt(i) == '"') {
                sb.append("&#39;");
            } else if (spcText.charAt(i) == '<') {
                sb.append("&lt;");
            } else if (spcText.charAt(i) == '>') {
                sb.append("&gt;");
            } else
                sb.append(spcText.charAt(i));
        }
        return sb.toString();
    }

    /**
     *
     * @Method Name : shubPwReverse
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 : 특수문자 필터
     * @Method 설명 :
     * @param spcText
     * @return
     */
    public static String spcTextReverse(String spcText) {
        String returnVal = spcText.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("get&nbsp;", "get ").replaceAll("set&nbsp;", "set ");


        return returnVal;
    }

    /**
     *
     * @Method Name : passwordCheck
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 :
     * @Method 설명 : 비밀번호 패턴 체크
     * @param id
     * @param pw
     * @return
     */
    public static String passwordCheck(String id, String pw) {
        String rtVal = "";
        int pwlen = pw.length();

        if (pw.indexOf(id) > 0) {
            rtVal = "ID를 비밀번호에 사용하시면 안됩니다.";
        }
        if (pwlen > pw.trim().length()) {
            rtVal = "비밀번호에 공백을 사용하시면 안됩니다.";
        }

        String pwPattern = "^(?=.*[0-9])"; // 숫자 패턴
        String pwPattern1 = "^(?=.*[~`!@#$%\\^%*()-])"; // 특문 패턴
        String pwPattern2 = "^(?=.*[a-z/A-Z])"; // 영문 패턴
        String pwPattern3 = "(.)\\1\\1\\1"; // 반복된문자

        Matcher nummatcher = Pattern.compile(pwPattern).matcher(pw);
        Matcher engmatcher = Pattern.compile(pwPattern2).matcher(pw);
        Matcher spcmatcher = Pattern.compile(pwPattern1).matcher(pw);
        Matcher matcher = Pattern.compile(pwPattern3).matcher(pw);

        if (matcher.find()) {
            rtVal = "반복된 숫자는 비밀번호로 사용할 수 없습니다.";
            //return rtVal;
        }

        if (!rtVal.equals("")) {
            return rtVal;
        }

        int patterncnt = 0;

        if (engmatcher.find()) {
            patterncnt = 1;
        }
        if (spcmatcher.find()) {
            patterncnt = patterncnt + 1;
        }
        if (nummatcher.find()) {
            patterncnt = patterncnt + 1;
        }

        if (patterncnt < 2) {
            rtVal = "비밀번호는 2가지 이상 문자를 조합해야 합니다.";
        } else if (patterncnt == 2 && pwlen < 10) {
            rtVal = "비밀번호 2가지 문자 조합시 10자리 이상을 사용해야 합니다.";
        } else if (patterncnt > 2 && pwlen < 8) {
            rtVal = "비밀번호 3가지 문자 조합시 8자리 이상을 사용해야 합니다.";
        } else {
            rtVal = "";
        }


        int o = 0;
        int d = 0;
        int p = 0;
        int n = 0;
        int limit = 4;

        for(int i=0; i<pw.length(); i++) {
            char tempVal = pw.charAt(i);
            p = o - tempVal;
            n = p;
            if(i > 0 && p > -2 && (n == d ? n + 1 :0) > limit -3) {
                rtVal = "연속된 숫자, 연속된 문자는 비밀번호로 사용할 수 없습니다.";
                break;
            }
            d = p;
            o = tempVal;
        }

        if (!rtVal.equals("")) {
            return rtVal;
        }

        String checkPw[] = { "abcd", "1234", "zxcv", "qwer" };

        for (int i = 0; i < checkPw.length; i++) {
            if (pw.indexOf(checkPw[i]) > 0) {
                rtVal = "단순한 비밀번호는 사용할 수 없습니다.";
                break;
            }
        }

        return rtVal;
    }

    /**
     *
     * @Method Name : setMask
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 :
     * @Method 설명 : 개인정보 마스킹 룰
     * @param field
     * @param val
     * @return
     */
    public static String setMask(String field, String val) {
        String maskVal = "";
        if (val != null && !val.equals("") && val.indexOf("*") < 0) {
            if (field.equalsIgnoreCase("userName")) { // 고객명 뒷자리 1/3을 마스킹처리
                maskVal = val.trim();
                int nameLen = maskVal.length();
                int nameCut = Math.abs(nameLen / 3);

                if (nameLen < 2) {
                    maskVal = "**";
                } else if (nameLen == 2) {
                    maskVal = maskVal.substring(0, 1) + "*";
                } else {
                    maskVal = maskVal.substring(0, nameLen - nameCut) + repeat("*", nameCut, "");
                }
            } else if (field.equalsIgnoreCase("userId")) {
                maskVal = val.trim();
                int idLen = maskVal.length();
                if (idLen > 0 && idLen < 4) {
                    maskVal = "***";
                }else if(idLen > 0 && idLen >= 4) {
                    maskVal = maskVal.substring(0, idLen - 3) + "***";
                }
            } else if (field.equalsIgnoreCase("phoneNumber")) {
                if (val.indexOf("-") > -1) {
                    maskVal = val.replaceAll("-", "");
                } else {
                    maskVal = val;
                }

                int phoneLen = maskVal.length();
                if (phoneLen < 4) {
                    maskVal = "***";
                } else {
                    int subLen = phoneLen - 3;
                    String parseEnd = maskVal.substring(subLen, phoneLen);
                    if (phoneLen == 11) {
                        maskVal = maskVal.substring(0, 3) + "-" + maskVal.substring(3, 5) + "**-*" + parseEnd;
                    } else if (phoneLen == 10) {
                        maskVal = maskVal.substring(0, 3) + "-" + maskVal.substring(3, 4) + "**-*" + parseEnd;
                    }
                }
            } else if (field.equalsIgnoreCase("email")) {
                String[] mail = val.trim().split("@");
                int emailLen = mail[0].length();
                if (emailLen > 0 && mail[1] != null) {
                    if (emailLen < 4) {
                        maskVal = "***@" + mail[1];
                    } else {
                        maskVal = mail[0].substring(0, emailLen - 3) + "***@" + mail[1];
                    }
                } else {
                    maskVal = "***";
                }
            }
        }
        return maskVal;
    }


    /**
     *
     * @Method Name : repeat
     * @작성일 : 2022. 12. 15.
     * @작성자 :
     * @변경이력 :
     * @Method 설명 : 한 문자열을 반복하여 연결한 새로운 문자열 만들기
     * <pre>
     * String s = Util.repeat("?", 5, ", "); // "?, ?, ?, ?, ?" 반환
     * </pre>
     * @param str
     * @param cnt
     * @param delimeter
     * @return
     */
    private static String repeat(String str, int cnt, String delimeter) {
        if (str == null || cnt <= 0) {
            return "";
        }

        StringBuffer buf = new StringBuffer(str);

        if (delimeter == null) {
            for (int i = 1; i < cnt; i++) {
                buf.append(str);
            }
        } else {
            for (int i = 1; i < cnt; i++) {
                buf.append(delimeter).append(str);
            }
        }
        return buf.toString();
    }


    /**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the CharSequence.
     * That functionality is available in isBlank().</p>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     *
     * @Method Name : StringsToLower
     * @Method 설명 : String배열을 받아서 모든 문자를 소문자로 치환
     * @작성일 : 2023. 1. 25.
     * @작성자 :  91327141 / 임유
     * @param String[]
     * @return String[]
     * @변경이력 :
     */
    public static String[] StringsToLower(String[] str) {
        String[] returnValue = null;
        int returnIdx = 0;
        for(int idx=0; idx<str.length; idx++) {
            String getStr = str[idx];
            if (!StringUtils.isBlank(getStr)) {
                returnValue[returnIdx] = StringUtils.lowerCase(getStr);
                returnIdx++;
            }
        }

        return returnValue;
    }

    /**
     *
     * @Method Name : StringTrim
     * @Method 설명 : trim Type에 따라  Rtrim , Ltrim , trim 구현
     * @작성일 : 2023. 2. 15.
     * @작성자 : 91327141 / 91327141 / 임유
     * @param sourceStr
     * @param trimType (r : rTrim , l : ltrim , b : trim )
     * @return String
     * @변경이력 :
     */
    public static String StringTrim(String sourceStr, String trimType) {
        if(sourceStr != null && !"".equals(sourceStr) && trimType != null && !"".equals(trimType)) {
            if("r".equals(trimType)) { //RTrim
                return sourceStr.replaceAll("\\s+$", "");
            }else if("l".equals(trimType)) {
                return sourceStr.replaceAll("^\\s+", "");
            }else if("b".equals(trimType)) {
                return sourceStr.trim();
            }
        }

        return sourceStr;
    }

    /**
     *
     * @Method Name : SubString
     * @Method 설명 : 문자열을 자른다
     * @작성일 : 2023. 2. 15.
     * @작성자 : 알앤비소프트 / 91327141 / 임유
     * @param sourceStr
     * @param startIdx
     * @param endIdx
     * @return String
     * @변경이력 :
     */
    public static String SubString(String sourceStr, int startIdx, int endIdx) {
        if(sourceStr != null && !"".equals(sourceStr) && startIdx < sourceStr.length() && endIdx > sourceStr.length()) {
            if(startIdx < endIdx) {
                return sourceStr.substring(startIdx, endIdx);
            }
        }

        return sourceStr;
    }

    /**
     *
     * @Method Name : ConvertMaskingCustNo
     * @Method 설명 : 고객번호 마스킹 처리 함수
     * @작성일 : 2023. 2. 20.
     * @작성자 : 알앤비소프트 / 91327141 / 임유
     * @param custNo 고객번호
     * @param custNoType 고객유형
     * @return String
     * @변경이력 :
     */
    public static String ConvertMaskingCustNo(String custNo, String custNoType) {
        int nMaskingFlag = 0; /* 0 : 마스킹 미처리 1 : 마스킹 처리 */
        String maskingCustNo = custNo;

        if(custNo != null && !"".equals(custNo)) {
            char[] custNoAry = custNo.toCharArray();

            int numCnt = 0;

            for(char custChar : custNoAry) {
                if(Integer.parseInt(custNoType.toString()) >= 0 && Integer.parseInt(custNoType.toString()) <= 9) {
                    numCnt++;
                }
            }

	    	/*
		        마스킹 미처리
		        - 생년월일 숫자 6개만 들어온 경우
		        - 13자리인데 뒤 7자리가 '0000000'일 경우
		        - 가상화된 번호 : 13자리이면서 8번째 자리 'V'이고 7번째 자리가 주민번호(1~4) 또는 외국인번호(5~8)이면
		        - 여권번호 : 16자리이면서 5번째 자리가 'P'이면 마스킹 미처리
		     */
            if((custNo.length() ==6 && numCnt == 6) || (custNo.length() == 13 && "0000000".equals(custNo.substring(6, 7))) || (custNo.length() == 13 && "V".equals(custNo.substring(7,1)))
                    && ("1".equals(custNo.substring(6,1)) || "2".equals(custNo.substring(6,1)) || "3".equals(custNo.substring(6,1)) || "4".equals(custNo.substring(6,1)) || "5".equals(custNo.substring(6,1)) || "6".equals(custNo.substring(6,1)) || "7".equals(custNo.substring(6,1)) || "8".equals(custNo.substring(6,1))
                    || (custNo.length() == 16 && "P".equals(custNo.substring(3,1))))) {
                nMaskingFlag = 0;
            }else if(custNoType == null || "".equals(custNoType)) { /* 고객유형 파라미터가 null일 경우 */
                /* 13자리가 모두 숫자이면 */
                if(custNo.length() == 13 && numCnt == 13) {
                    /* 7번째 자리가 주민번호(1~4), 외국인번호(5~8)이면 마스킹 처리 */
                    /* 법인번호일 수도 있으나 명확한 구분이 어렵기 때문에 주민/외국인번호로 간주 */
                    if("1".equals(custNo.substring(6,1)) || "2".equals(custNo.substring(6,1)) || "3".equals(custNo.substring(6,1)) || "4".equals(custNo.substring(6,1)) || "5".equals(custNo.substring(6,1)) || "6".equals(custNo.substring(6,1)) || "7".equals(custNo.substring(6,1)) || "8".equals(custNo.substring(6,1))){
                        nMaskingFlag = 1;
                    }else {
                        /* 7번째 자리 1~8이 아니면 법인번호라고 판단하여 마스킹 미처리 */
                        nMaskingFlag = 0;
                    }

                }else if(custNo.length() == 10 && numCnt == 10) {
                    nMaskingFlag = 0;
                }else if(custNo.length() == 10 && numCnt != 10) {
                    nMaskingFlag = 1;
                }else if(custNo.length() < 13 && numCnt != 10) {
                    nMaskingFlag = 1;
                }else {
                    nMaskingFlag = 1;
                }
            }else if("1".equals(custNoType)) {
	    		/* 주민/외국인번호의 경우 6자리나 '0000000', 가상번호로 입력시 마스킹 미처리 위에서 수행
	            여기에는 13자리 숫자로 된 실번호만 들어오므로 마스킹 처리 */
                nMaskingFlag = 1;
            }else if("2".equals(custNoType)) {
	    		/* 여권번호 가상번호로 입력시 마스킹 미처리 위에서 수행
	            실번호만 들어오므로 마스킹 처리  */
                nMaskingFlag = 1;
            }else {
                /* 사업자번호, 법인번호, 주한미군, 기타 경우 마스킹 미처리 */
                nMaskingFlag = 0;
            }
        }

        if(nMaskingFlag == 1) {
            int nSize = custNo.length() + 6;
            StringBuffer tmpString = new StringBuffer();
            tmpString.append(custNo.substring(0,6));

            String starString = "*";
            for(int nIdx=0;nIdx<nSize;nIdx++) {
                tmpString.append(starString);
            }

            maskingCustNo = tmpString.toString();

        }

        return maskingCustNo;

    }

    /**
     *
     * @Method Name : ConvertMaskingAcntNo
     * @Method 설명 : 계좌/카드번호 마스킹 처리 함수
     * @작성일 : 2023. 2. 20.
     * @작성자 : 알앤비소프트 / 91327141 / 임유
     * @param acntNo
     * @return String
     * @변경이력 :
     */
    public static String ConvertMaskingAcntNo(String acntNo) {
        int nMaskingFlag = 0; /* 0 : 마스킹 미처리 1 : 마스킹 처리 */
        String maskingAcntNo = acntNo;

        if(acntNo != null && !"".equals(acntNo)) {
    		/*
    	       마스킹 미처리
    	       - 파라미터가 가상계좌/카드번호인 경우 마스킹 미처리하여 내부 마스킹변수에 설정
    	         4번째 또는 5번째 자리가 'V'이면 가상 계좌/카드번호로 간주
    	    */
            if("V".equals(acntNo.substring(3,1)) || "V".equals(acntNo.substring(4,1))) {
                nMaskingFlag = 0;
            }else {
                nMaskingFlag = 1;
            }
        }

        if(nMaskingFlag == 1) {
            StringBuffer tmpString = new StringBuffer();
    		/* 정확하게 카드번호만 넘어오지 않고 csnl449sf처럼 '카드번호=유효일자'로 들어오는 경우
     	   카드번호 길이가 가변적이라고 보고 처리 */
            if(acntNo.indexOf("=") != -1) {
                String[] acntNoList = acntNo.split("=");
                int nSize = acntNoList[0].length() / 2 ;
                tmpString.append(acntNoList[0].substring(0, nSize));

                String starString = "*";
                for(int nIdx=0;nIdx<nSize-(acntNoList[0].substring(0, nSize).length());nIdx++) {
                    tmpString.append(starString);
                }
                tmpString.append("=");
                tmpString.append(acntNoList[1]);
            }else {
                int nSize = acntNo.length() / 2 ;
                tmpString.append(acntNo.substring(0, nSize));

                String starString = "*";
                for(int nIdx=0;nIdx<nSize-(acntNo.substring(0, nSize).length());nIdx++) {
                    tmpString.append(starString);
                }
            }

            maskingAcntNo = tmpString.toString();
        }

        return maskingAcntNo;
    }

    /**
     *
     * @Method Name : ConvertMaskingAcntNo
     * @Method 설명 : 연동 전문 고객정보 부분 마스킹 처리 함수
     * @작성일 : 2023. 2. 20.
     * @작성자 : 알앤비소프트 / 91327141 / 임유
     * @param str
     * @param totLen
     * @param acntNoStartPos
     * @param acntNoLen
     * @param custNoStartPos
     * @param custNoLen
     * @param custNoTypeStartPos
     * @param custNoTypeLen
     * @return String
     * @변경이력 :
     */
    public static String ConvertMaskingAcntNo(String str, int totLen, int acntNoStartPos, int acntNoLen, int custNoStartPos, int custNoLen, int custNoTypeStartPos, int custNoTypeLen) {
        int minLen = 0;
        String acntNo = "";
        String custNoType = "";
        String custNo = "";
        String maskingCustNo = "";
        String maskingAcntNo = "";
        StringBuffer maskingStr = new StringBuffer();

        /* 정연식_[20150317]_계좌번호, 고객번호 중 '나중 위치+그 길이'만큼보다 전체 문자열의 길이가 짧으면 마스킹 미처리_2015.06.10 */
        if(acntNoStartPos > custNoStartPos) {
            minLen = acntNoStartPos + acntNoLen;
        }else {
            minLen = custNoStartPos + custNoLen;
        }

        if(totLen < minLen) {
            return str;
        }

        /* 고객유형 설정 : 고객유형코드 값의 위치가 0이 아니면, 즉 문자열 중 고객유형 값이 있으면 */
        if(custNoTypeStartPos != 0) {
            custNoType = StringTrim(str.substring(custNoTypeStartPos, custNoTypeLen), "r");
        }

        /* 고객코드 마스킹 처리 : 고객코드 값의 위치가 0이 아니면, 즉 문자열 중 고객코드 값이 있으면 */
        if(custNoStartPos != 0) {
            custNo = StringTrim(str.substring(custNoStartPos, custNoLen), "r");
            maskingCustNo = ConvertMaskingCustNo(custNo, custNoType);
        }

        /* 계좌/카드번호 마스킹 처리 : 계좌/카드번호 값의 위치가 0이 아니면, 즉 문자열 중 계좌/카드번호 값이 있으면 */
        if(acntNoStartPos != 0) {
            acntNo = StringTrim(str.substring(acntNoStartPos, acntNoLen), "r");
            maskingAcntNo = ConvertMaskingAcntNo(acntNo);
        }

        /* 전문 버퍼 생성 */
        /* 전문 유형
           1. nAcntNoStartPos, nCustNoStartPos 둘 다 없는 경우
           2. nAcntNoStartPos만 있고, nCustNoStartPos가 없는 경우
           3. nAcntNoStartPos가 없고, nCustNoStartPos가 있는 경우
           4. nAcntNoStartPos, nCustNoStartPos 둘 다 있는 경우
            4-1. nAcntNoStartPos이 nCustNoStartPos보다 큰 경우
            4-2. nAcntNoStartPos이 nCustNoStartPos보다 작은 경우
        */
        /* 계좌번호, 고객번호 둘 다 없는 경우 */
        if(acntNoStartPos == 0 && custNoStartPos == 0) {
            /* 마스킹 안 함 */
            maskingStr.append(str);
        }else if(acntNoStartPos != 0 && custNoStartPos == 0) {
            /* 계좌번호만 있고, 고객번호가 없는 경우 */
            /*DP(("ConvertMaskingBuffer() : 계좌번호만 있고, 고객번호가 없는 경우"));*/
            /* 계좌번호 위치 앞까지 copy */
            maskingStr.append(str.substring(0, acntNoStartPos));
            maskingStr.append(maskingAcntNo);
            /* 계좌번호 길이만큼 뒷자리 space 만들기 */
            for(int i=0;i<(acntNoLen-maskingAcntNo.length());i++) {
                /* 계좌번호 붙이고 길이만큼 space 추가 */
                maskingStr.append(" ");
            }
            /* 버퍼+계좌번호 끝나는 자리(계좌번호 위치+계좌번호 길이)부터 나머지 길이(전체 길이 - 계좌번호까지 길이)만큼 */
            maskingStr.append(str.substring(acntNoStartPos+acntNoLen, totLen-((acntNoStartPos+acntNoLen))));

        }else if(acntNoStartPos == 0 && custNoStartPos != 0) {
            /* 계좌번호가 없고, 고객번호만 있는 경우 */
            /* 고객번호 위치 앞까지 copy */
            maskingStr.append(str.substring(0, custNoStartPos));
            maskingStr.append(maskingCustNo);
            /* 고객번호 길이만큼 뒷자리 space 만들기 */
            for(int i=0;i<(custNoLen-maskingCustNo.length());i++) {
                /* 계좌번호 붙이고 길이만큼 space 추가 */
                maskingStr.append(" ");
            }
            /* 버퍼+고객번호 끝나는 자리(고객번호 위치+고객번호 길이)부터 나머지 길이(전체 길이 - 고객번호까지 길이)만큼 */
            maskingStr.append(str.substring(custNoStartPos+custNoLen, totLen-((custNoStartPos+custNoLen))));
        }else if( (acntNoStartPos-custNoStartPos) < 0 ) {
            /* 계좌번호가 고객번호보다 앞이면 */
            /* 계좌번호 위치 앞까지 copy */
            maskingStr.append(str.substring(0, acntNoStartPos));
            maskingStr.append(maskingAcntNo);
            /* 계좌번호 길이만큼 뒷자리 space 만들기 */
            for(int i=0;i<(acntNoLen-maskingAcntNo.length());i++) {
                /* 계좌번호 붙이고 길이만큼 space 추가 */
                maskingStr.append(" ");
            }
    		/* 계좌번호 뒤부터 고객번호 앞까지 버퍼에 붙인다.
     	   계좌번호 끝 위치(계좌시작위치+길이)부터 고객번호 앞까지의 길이(고객번호 시작위치-계좌번호 종료 위치)만큼 */
            maskingStr.append(str.substring((acntNoStartPos+acntNoLen), custNoStartPos-(acntNoStartPos+acntNoLen)));
            maskingStr.append(maskingCustNo);
            /* 고객번호 붙임. */
            /* 고객번호 길이만큼 뒷자리 space 만들기 */
            for(int i=0;i<(custNoLen-maskingCustNo.length());i++) {
                /* 계좌번호 붙이고 길이만큼 space 추가 */
                maskingStr.append(" ");
            }
    		/* 고객번호 이후 붙임
     	   버퍼+고객번호 끝나는 자리(고객번호 위치+고객번호 길이)부터 나머지 길이(전체 길이 - 고객번호까지 길이)만큼 */
            maskingStr.append(str.substring((custNoStartPos+custNoLen), totLen-(custNoStartPos+custNoLen)));
        }else if( (acntNoStartPos-custNoStartPos) > 0) {
            /* 고객번호가 계좌번호보다 앞이면 */
            /* 고객번호 위치 앞까지 copy */
            maskingStr.append(str.substring(0, custNoStartPos));
            maskingStr.append(maskingCustNo);
            /* 고객번호 길이만큼 뒷자리 space 만들기 */
            for(int i=0;i<(custNoLen-maskingCustNo.length());i++) {
                /* 계좌번호 붙이고 길이만큼 space 추가 */
                maskingStr.append(" ");
            }
    		/* 고객번호 뒤부터 계좌번호 앞까지 버퍼에 붙인다.
     	   고객번호 끝 위치(고객번호위치+길이)부터 계좌번호 앞까지의 길이(계좌번호 시작위치-고객번호 종료 위치)만큼 */
            maskingStr.append(str.substring((custNoStartPos+custNoLen), acntNoStartPos-(custNoStartPos+custNoLen)));

            /* 계좌번호 붙임. */
            maskingStr.append(maskingAcntNo);
            /* 계좌번호 길이만큼 뒷자리 space 만들기 */
            for(int i=0;i<(acntNoLen-maskingAcntNo.length());i++) {
                /* 계좌번호 붙이고 길이만큼 space 추가 */
                maskingStr.append(" ");
            }
    		/* 계좌번호 이후 붙임
     	   버퍼+계좌번호 끝나는 자리(계좌번호 위치+계좌번호 길이)부터 나머지 길이(전체 길이 - 계좌번호까지 길이)만큼 */
            maskingStr.append(str.substring((acntNoStartPos+acntNoLen), totLen-(acntNoStartPos+acntNoLen)));


        }

        return maskingAcntNo;
    }

    /**
     *
     * @Method Name : intToAscii
     * @Method 설명 :	 int값을 받아서 Ascii코드 값으로 리턴
     * @작성일 : 2023. 2. 20.
     * @작성자 : 소속 / 91327141 / 성명
     * @param iValue
     * @return String
     * @변경이력 :
     */
    public static String intToAscii(int iValue) {
        String returnStr = "";

        String asciiStr = Character.toString(iValue);

        return asciiStr;
    }
}