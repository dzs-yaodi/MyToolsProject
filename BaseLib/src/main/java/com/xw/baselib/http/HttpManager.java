package com.xw.baselib.http;

import com.xw.baselib.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {

    private static Retrofit mRetrofit;
    private static HttpManager instance;

    public static HttpManager getInstance(){
        if (instance == null){
            synchronized (HttpManager.class){
                if (instance == null){
                    instance = new HttpManager();
                }
            }
        }

        return instance;
    }

    /**
     * 初始化，传入baseUrl
     * @param httpUrl
     */
    public void init(String httpUrl){
        OkHttpClient.Builder builder =new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60,TimeUnit.SECONDS);

        if (BuildConfig.DEBUG){
            //调试模式下添加日志拦截
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logInterceptor);
        }

        OkHttpClient okHttpClient = builder.build();

        mRetrofit =new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(httpUrl)
                .build();
    }

    /**
     *创建一个api service服务。
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T creatApi(Class<T> tClass){
        return mRetrofit.create(tClass);
    }
}
