package com.jesse.learn.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext appCtx;

    public ApplicationContextHelper() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        appCtx = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return appCtx;
    }

    public static Object getBean(String beanName) {
        return appCtx.getBean(beanName);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return appCtx.getBean(beanName, clazz);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> claz) {
        return appCtx.getBeansOfType(claz);
    }
}
