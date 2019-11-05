package com.xw.baselib.http;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostAndDownFileUtils {

    public static OkHttpClient mClient;
    private static String mImageType = "multipart/form-data";

    /**
     * 如果需要上传其他参数自己添加设置
     *配合rxjava 在子线程（subscribeOn().map..）运行该方法，在主线程toast结果
     * @param fileList 上传的文件路径
     */
    public static String PostFile(Map<String,String> map, List<String> fileList) {

        if (mClient == null) {
            mClient = getUnsafeOkHttpClient();
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        RequestBody fileBody = null;
        for (String s :fileList) {
            File file = new File(s);
            if (file.exists()) {
                fileBody = RequestBody.create(MediaType.parse(mImageType), file);
                builder.addFormDataPart("file", file.getName(), fileBody);
            }
        }

        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {

                if (!TextUtils.isEmpty(entry.getValue())) {
                    builder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }
        }

        RequestBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url("http://172.16.131.4:8080/pushMp4")
                .post(requestBody)
                .build();

        Response response;
        try {
            response = mClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                Log.i("info","======上传失败===");
                return "上传失败";
            } else {
                Log.i("info","=====上传成功===");
                return "上传成功";
            }
        } catch (Exception e) {
            Log.d("info", "======上传异常====", e);
            return "上传异常";
        }
    }

    //这里是异步，如果需要toast结果，需要切换到线程
    public static void DownFile(final Map<String,String> map, final DataResponce dr){

        if (mClient == null) {
            mClient = getUnsafeOkHttpClient();
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {

                if (!TextUtils.isEmpty(entry.getValue())) {
                    builder.addFormDataPart(entry.getKey(), entry.getValue());
                }
            }
        }

        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url("http://172.16.131.4:8080/getFile")
                .post(requestBody)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dr.onFail(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buffer =new byte[2048];
                int len = 0;
                FileOutputStream fos = null;

                try{
                    File file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                    if (!file1.exists())file1.mkdirs();

                    File file = new File(file1,map.get("fileName"));

                    fos = new FileOutputStream(file);
                    is = response.body().byteStream();
                    //文件长度
                    long total = response.body().contentLength();

                    long sum = 0;
                    while ((len = is.read(buffer)) != -1){
                        fos.write(buffer,0,len);
                        sum+= len;

                        int progress = (int) (sum * 1.0f / total*100);
                        Log.i("info","===下载进度==" + progress);
                    }

                    fos.flush();
                    dr.onSucc("下载成功");

                }catch (Exception e){
                    e.printStackTrace();
                    dr.onFail("下载失败");
                }finally {
                    if (is != null) is.close();

                    if (fos != null) fos.close();
                }
            }
        });
    }

    //忽略证书验证
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
