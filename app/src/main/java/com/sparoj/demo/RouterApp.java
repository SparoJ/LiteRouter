package com.sparoj.demo;

import android.app.Application;

import com.sparoj.annotation.Modules;
import com.sparoj.literouter.LiteRouter;

/**
 * description:
 * Created by sdh on 2019/3/13
 */

@Modules(modules = {"face"})
public class RouterApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LiteRouter.getInstance().init();
    }
}
