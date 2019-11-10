package com.sparoj.literouter;

import com.sparoj.intercept.RouterInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: 组件化轻量版路由
 * Created by sdh on 2019/3/13
 */

public class LiteRouter {

    private final Map<String, RouterModule> moduleMap;
    private HashMap<String, ArrayList<RouterModule>> routerModuleMap;
    private HashMap<String, ArrayList<RouterAction>> routerActionMap;
    private List<RouterInterceptor> interceptorList = new ArrayList<>();
    private static final String ROUTER_KEY = "com.sparoj.literouter";


    private static class LiteRouterInstance {
        private static final LiteRouter INSTANCE = new LiteRouter();
    }

    public static LiteRouter getInstance() {
        return LiteRouterInstance.INSTANCE;
    }

    public LiteRouter() {
        moduleMap = new HashMap<>();
    }

    /**
     * 全局初始化
     */
    public void init() {
        //初始化map
        routerModuleMap = new HashMap<>();
        routerActionMap = new HashMap<>();
        LiteRouterRegister.register(routerModuleMap, routerActionMap);
        //遍历注册自动注入的map module／action到对应维护类中
        iteratorMap();
    }

    private void iteratorMap() {
        ArrayList<RouterModule> routerList = routerModuleMap.get(ROUTER_KEY);
        if (routerList != null && routerList.size()>0) {
            for (RouterModule module:
                 routerList) {
                registerModule(module.getModuleName(), module);
                ArrayList<RouterAction> actionList = routerActionMap.get(module.getModuleName());
                if (actionList != null && actionList.size()>0) {
                    for (RouterAction action :
                            actionList) {
                        module.registerModuleAction(action.getActionName(), action);
                    }
                }
            }
        }
    }

    /**
     * 注册module 【前置通过apt 动态生成module并传递注册】
     * @param moduleName module name
     * @param module module实例
     */
    private void registerModule(String moduleName, RouterModule module) {
        moduleMap.put(moduleName, module);
    }

    public Map<String, RouterModule> getModuleMap() {
        return moduleMap;
    }

    public RouterCall call(RouterRequest request) {
        return new RouterCall(this, request);
    }

    public List<RouterInterceptor> getInterceptors() {
        return interceptorList;
    }



    /**
     * 添加router 拦截器 进行拦截处理
     * @return LiteRouter
     */
    public LiteRouter addInterceptor(RouterInterceptor interceptor) {
        if (!interceptorList.contains(interceptor)) {
            interceptorList.add(interceptor);
        }
        return this;
    }

}
