package com.sparoj.face;

import android.util.Log;

import com.sparoj.annotation.Action;
import com.sparoj.literouter.EmptyAction;
import com.sparoj.literouter.RouterAction;
import com.sparoj.literouter.RouterRequest;
import com.sparoj.literouter.RouterResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * description:
 * Created by sdh on 2019/3/13
 */

@Action(provideModuleName = "face")
public class FaceAction implements RouterAction {
    @Override
    public RouterResponse router(RouterRequest request) {
        //测试 异步完成 结合Rx todo
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("FaceAction", "router(FaceAction.java:28)" + Thread.currentThread());
        String moduleAction = request.getModuleAction();
        Log.e("FaceAction", "router(FaceAction.java:18)moduleAction!!!" + moduleAction);
        Map map = request.getWrapMap();
        String face = (String) map.get("face");
        Log.e("FaceAction", "router(FaceAction.java:25)map data!!!" + face);
        EmptyAction wrapObj = (EmptyAction) request.getWrapObj();
        Log.e("FaceAction", "router(FaceAction.java:35)" + wrapObj.getActionName());
        Log.e("FaceAction", "router(FaceAction.java:37)" + request.getJson());
        Log.e("FaceAction", "router(FaceAction.java:38)" + request.getJSONObj());
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put(getActionName(), "face action");
        return new RouterResponse().code("200")
                .msg("face action callback")
                .responseMap(responseMap);
    }

    @Override
    public String getActionName() {
        return this.getClass().getSimpleName();
    }
}
