package com.sparoj.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.sparoj.literouter.EmptyAction;
import com.sparoj.literouter.LiteRouter;
import com.sparoj.literouter.RouterCallback;
import com.sparoj.literouter.RouterRequest;
import com.sparoj.literouter.RouterResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * description:
 * Created by sdh on 2019/3/14
 */

public class RouterDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initProcess();
    }


    private void initProcess() {
        //异步
//        findViewById(R.id.btn_face_async).setOnClickListener(new View.OnClickListener() {
//            @SuppressWarnings("unchecked")
//            @Override
//            public void onClick(View v) {
//                Log.e("RouterDemoActivity", "onClick(RouterDemoActivity.java:37)" );
//                RouterResponse response = LiteRouter.getInstance().router(new RouterRequest
//                        .Builder()
//                         .isAsync(true)
//                        .toModule("face")
//                        .routerAction("FaceAction")
//                        .wrapData("face", "router hia")
//                        .wrapObj(new EmptyAction())
//                        .build());
//                String responseCode = response.getResponseCode();
//                String responseMsg = response.getResponseMsg();
//                Map map = response.getWrapResponseMap();
//                Log.e("RouterDemoActivity", "onClick(RouterDemoActivity.java:47)" + Thread.currentThread());
//                Log.e("RouterDemoActivity", "onClick(RouterDemoActivity.java:45)" + responseCode);
//                Log.e("RouterDemoActivity", "onClick(RouterDemoActivity.java:47)" + responseMsg);
//                Log.e("RouterDemoActivity", "onClick(RouterDemoActivity.java:48)" + map.get("FaceAction"));
//            }
//        });
        final JSONObject obj = new JSONObject();
        try {
            obj.put("key", "str");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //同步
        findViewById(R.id.btn_face_sync).setOnClickListener(new View.OnClickListener() {
                                                                @SuppressWarnings("unchecked")
                                                                @Override
                                                                public void onClick(View v) {
                                                                    Log.e("RouterDemoActivity", "onClick(RouterDemoActivity.java:62)" + Thread.currentThread());
                                                                     LiteRouter.getInstance().call((new RouterRequest
                                                                            .Builder()
                                                                             .toModuleAction("face/FaceAction")
//                                                                            .toModule("face")
//                                                                            .routerAction("FaceAction")
                                                                            .wrapData("face", "router hia")
                                                                             .wrapJson(obj.toString())
                                                                             .wrapJSONObject(obj)
                                                                            .wrapObj(new EmptyAction())
                                                                            .build())).enqueue(new RouterCallback() {
                                                                        @Override
                                                                        public void routerBack(RouterResponse response) {
                                                                            String responseCode = response.getResponseCode();
                                                                            String responseMsg = response.getResponseMsg();
                                                                            Map map = response.getWrapResponseMap();
                                                                            Log.e("RouterDemoActivity", "onClick(RouterDemoActivity.java:69)" + Thread.currentThread());
                                                                            Log.e("RouterDemoActivity", "onClick(RouterDemoActivity.java:45)" + responseCode);
                                                                            Log.e("RouterDemoActivity", "onClick(RouterDemoActivity.java:47)" + responseMsg);
                                                                            Log.e("RouterDemoActivity", "onClick(RouterDemoActivity.java:48)" + map.get("FaceAction"));
                                                                        }
                                                                    });

                                                                }
                                                            }
        );
    }


}
