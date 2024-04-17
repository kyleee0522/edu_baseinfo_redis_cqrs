package com.kt.edu.common.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class DtoUtil implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6000523331697839784L;


    public DtoUtil() {
        super();
    }

    public static int getCount(Object obj) {
        int whereCnt = 0;

        Class<? extends Object> tarCls = obj.getClass();

        try {
            for (Field field : tarCls.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (!isEmpty(value)) {
                    whereCnt++;
                }
            }
        }catch(IllegalArgumentException e) {
            e.printStackTrace();
        }catch(IllegalAccessException e) {
            e.printStackTrace();
        }

        return whereCnt;
    }


    /**
     * entity object 자체가 empty인지 확인
     * @param clsName
     * @return
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }

        if ((o instanceof String) && (((String)o).trim().length()==0)) {
            return true;
        }
        if (o instanceof Map) {
            return ((Map<?,?>)o).isEmpty();
        }
        if (o instanceof List) {
            return ((List<?>)o).isEmpty();
        }
        if (o instanceof Object[]) {
            return (((Object[])o).length == 0);
        }
        return false;
    }
}