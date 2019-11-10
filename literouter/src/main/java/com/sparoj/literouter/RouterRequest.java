package com.sparoj.literouter;

import java.util.HashMap;
import java.util.Map;

/**
 * description: 路由请求包装类
 * Created by sdh on 2019/3/13
 */

public class RouterRequest<T> {

    private Builder builder;
    /**
     * 包装传递对象
     */
    private T t;
    /**
     * 包装传递数据map
     */
    private final Map<String, String> dataMap;
    /**
     * 目标module
     */
    private final String targetModule;
    /**
     * 路由行为
     */
    private final String routerAction;

    @SuppressWarnings("unchecked")
    private RouterRequest(Builder builder) {
        this.builder = builder;
        targetModule = builder.targetModule;
        routerAction = builder.routerAction;
        dataMap = builder.dataMap;
        t = (T) builder.t;
    }

    public T getWrapObj() {
        return t;
    }

    public Map<String, String> getWrapMap() {
        return dataMap;
    }

    public String getTargetModule() {
        return targetModule;
    }

    public String getRouterAction() {
        return routerAction;
    }


    public static class Builder<T> {


        private String targetModule;

        private String routerAction;

        private Map<String, String> dataMap = new HashMap<>();

        private T t;


        public Builder toModule(String targetModule) {
            this.targetModule = targetModule;
            return this;
        }

        public Builder routerAction(String action) {
            this.routerAction = action;
            return this;
        }

        public Builder wrapData(String key, String value) {
            dataMap.put(key, value);
            return this;
        }

        public Builder wrapObj(T t) {
            this.t = t;
            return this;
        }
        public RouterRequest build() {
            return new RouterRequest(this);
        }
    }
}
