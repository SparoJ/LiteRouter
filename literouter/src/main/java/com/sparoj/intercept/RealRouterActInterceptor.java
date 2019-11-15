package com.sparoj.intercept;

import android.text.TextUtils;

import com.sparoj.literouter.EmptyAction;
import com.sparoj.literouter.RouterAction;
import com.sparoj.literouter.RouterModule;
import com.sparoj.literouter.RouterRequest;
import com.sparoj.literouter.RouterResponse;

import java.util.Map;

/**
 * description: 发起路由点
 * Created by sdh on 2019/3/16
 */

public class RealRouterActInterceptor implements RouterInterceptor {

    public static final int ROUTER_MODULE_PARAM_LENGTH = 2;
    @Override
    public RouterResponse intercept(Chain chain) {
        RouterInterceptorChain routerChain = (RouterInterceptorChain) chain;
        RouterRequest request = routerChain.request();
       return router(routerChain.moduleMap(), request);
    }

    /**
     * 发起行为
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public RouterResponse router(Map<String, RouterModule> routerMap, RouterRequest request){
        if (routerMap == null || routerMap.size()==0) {
            return new EmptyAction().routerError("404", "relative register router module should not be null or empty");
        }
        RouterAction action = findRouterAction(routerMap, request);
        return action.router(request);
    }



    private RouterAction findRouterAction(Map<String, RouterModule> routerMap, RouterRequest request) {
        String[] paramArr = parseRouterModule(request);
        String targetModule = paramArr[0];
        String targetAction = paramArr[1];
        // request.getTargetModule()
        RouterModule module = routerMap.get(targetModule);
        if (module != null) {
            // request.getRouterAction()
            return module.findRouterAction(targetAction);
        }
        return new EmptyAction();
    }

    private String[] parseRouterModule(RouterRequest request) {
        String moduleAction = request.getModuleAction();
        if (TextUtils.isEmpty(moduleAction)) {
            throw new IllegalArgumentException("router module and action should not be null");
        }
        String[] split = moduleAction.split("/");
        // module & action
        if (split.length != ROUTER_MODULE_PARAM_LENGTH) {
            throw new IllegalArgumentException("parse module and action string error, check your routerAction string first!");
        }
        return split;
    }
}
