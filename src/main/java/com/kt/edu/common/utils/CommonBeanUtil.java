package com.kt.edu.common.utils;

import com.kt.edu.common.context.ApplicationContextProvider;


public class CommonBeanUtil {

    public static Object getBean(String beanName) {
        try {
            return ApplicationContextProvider.getContext().getBean(beanName);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> Object getBean(Class<T> clz) {
        return ApplicationContextProvider.getContext().getBean(clz);

    }

}
