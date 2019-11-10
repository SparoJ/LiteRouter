package com.sparoj.literouter;

import java.util.Map;

/**
 * description: 路由结果包装类
 * Created by sdh on 2019/3/13
 */

public class RouterResponse<T> {

    private String responseCode;
    private String responseMsg;
    private Map<String, String> wrapResponseMap;
    private T t;

    public RouterResponse code(String responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public RouterResponse msg(String msg) {
        this.responseMsg = msg;
        return this;
    }

    public RouterResponse responseMap(Map<String, String> map) {
        this.wrapResponseMap = map;
        return this;
    }

    public RouterResponse responseObj(T t) {
        this.t = t;
        return this;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public Map<String, String> getWrapResponseMap() {
        return wrapResponseMap;
    }

    public T getT() {
        return t;
    }
}
