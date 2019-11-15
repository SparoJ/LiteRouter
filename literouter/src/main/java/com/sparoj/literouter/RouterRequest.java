package com.sparoj.literouter;

import org.json.JSONObject;

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
    private final String json;
    private final JSONObject jo;
    /**
     * router 到 的 module 和 module中action的 拼接（使用path方式完成）
     */
    private final String moduleAction;

    @SuppressWarnings("unchecked")
    private RouterRequest(Builder builder) {
        this.builder = builder;
        targetModule = builder.targetModule;
        routerAction = builder.routerAction;
        dataMap = builder.dataMap;
        t = (T) builder.t;
        json = builder.json;
        jo = builder.jo;
        moduleAction = builder.moduleAction;
    }

    /**
     * @return wrap Json String
     */
    public String getJson() {
        return json;
    }

    /**
     * @return wrap JSONObject
     */
    public JSONObject getJSONObj() {
        return jo;
    }

    public T getWrapObj() {
        return t;
    }

    public Map<String, String> getWrapMap() {
        return dataMap;
    }

    /**
     * @see
     * @return replace with simple url path way to convey target and router action
     */
    @Deprecated
    public String getTargetModule() {
        return targetModule;
    }
    @Deprecated
    public String getRouterAction() {
        return routerAction;
    }

    public String getModuleAction() {
        return moduleAction;
    }

    public static class Builder<T> {


        private String targetModule;

        private String routerAction;

        private Map<String, String> dataMap = new HashMap<>();

        private T t;
        private String json;
        private JSONObject jo;
        private String moduleAction;

        /**
         *
         * @param moduleAction xxx(moduleName)/xxx(actionName)
         * @return
         */
        public Builder toModuleAction(String moduleAction) {
            this.moduleAction = moduleAction;
            return this;
        }

        @Deprecated
        public Builder toModule(String targetModule) {
            this.targetModule = targetModule;
            return this;
        }

        @Deprecated
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

        public Builder wrapJson(String json) {
            this.json = json;
            return this;
        }

        public Builder wrapJSONObject(JSONObject jo) {
            this.jo = jo;
            return this;
        }

        public RouterRequest build() {
            return new RouterRequest(this);
        }
    }
}
