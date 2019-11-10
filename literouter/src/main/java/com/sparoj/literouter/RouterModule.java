package com.sparoj.literouter;

import java.util.HashMap;
import java.util.Map;

/**
 * description:
 * Created by sdh on 2019/3/13
 */

public abstract class RouterModule {

    private final Map<String, RouterAction> actionMap;

    public RouterModule() {
        actionMap = new HashMap<>();
    }

    /**
     *
     * 注册对应module所具有的action
     */
    public void registerModuleAction(String actionName, RouterAction action) {
        actionMap.put(actionName, action);
    }

    public RouterAction findRouterAction(String actionName) {
       return actionMap.get(actionName);
    }


    /**
     * 注册module使用 【与全局注入module集合对应】
     * @return moduleName
     */
    protected abstract String getModuleName();
}
