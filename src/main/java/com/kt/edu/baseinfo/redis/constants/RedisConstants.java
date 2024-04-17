package com.kt.edu.baseinfo.redis.constants;

/**

 * Redis log 메시지

 */

public class RedisConstants {

    public final static String DELIM = ":"; //Redis 구분자

    public final static String PREFIX = "edu:order"; //Redis 식별키 시작

    public final static String GET_LOG = "[REDIS GET][KEY={}][SIZE={}]";

    public final static String SET_LOG = "[REDIS SET][KEY={}][SIZE={}]";

}