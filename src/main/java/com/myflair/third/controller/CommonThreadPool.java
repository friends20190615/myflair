package com.myflair.third.controller;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.*;

/**
 * Created by wangjiulin on 2018/4/11.
 */
public final class CommonThreadPool {

    private static ExecutorService execute = init();

    private CommonThreadPool() {
    }

    public static JSONObject execute(YouZanHandler handler) throws ExecutionException, InterruptedException {
        Future future = execute.submit(handler);
        return JSONObject.parseObject(future.get().toString());
    }



    public static ExecutorService getThreadPool() {
        ExecutorService execute =new ThreadPoolExecutor(5,120,20000,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(2000));
        return execute;
    }

    private static ExecutorService init() {
            return getThreadPool();
        }
}
