package com.xw.mytoolsproject;

import com.xw.baselib.http.HttpManager;

public class API {

    public ApiService service;

    public static class SingleHolder{
        private static final API instance = new API();
    }

    public API() {
        service = HttpManager.getInstance().creatApi(ApiService.class);
    }

    public static API getInstance(){
        return SingleHolder.instance;
    }
}
