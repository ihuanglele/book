package com.ihuanglele.book.util;

import com.ihuanglele.book.exception.PageErrorException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by ihuanglele on 2018/11/8.
 */
public class GetHtmlPage {
    // 当前抓取的URL
    private String url;

    private static final String mobileAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1";
    private static final String pcAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";

    private OkHttpClient client;

    // 是否使用手机模式访问
    private boolean isMobile = false;

    private static Integer times = 0;

    public GetHtmlPage() {
        client = new OkHttpClient.Builder()
                .connectTimeout(5,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .build();
    }

    public boolean isMobile() {
        return isMobile;
    }

    public GetHtmlPage setMobile(boolean mobile) {
        isMobile = mobile;
        return this;
    }

    public Response getPage(String url) throws PageErrorException {
        times++;
        if(times > 10000){
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.url = url;
        Tool.save(url,"urls");
        return this.request();
    }

    private Response request() throws PageErrorException {
        Request request = new Request.Builder()
                .addHeader("User-Agent",isMobile ? GetHtmlPage.mobileAgent : GetHtmlPage.pcAgent)
                .addHeader("Connection","close")
                .url(url)
                .build();
        request.url();


        Response response = null;
        try {
            client.newCall(request);
            response = client.newCall(request).execute();
            if(response.isSuccessful()){
                return response;
            }else {
                response.close();
                throw new PageErrorException("response：ERROR -> " + url + " " + response.message());
            }
        } catch (IOException e) {
            if(null != response){
                response.close();
            }
            throw new PageErrorException("response：IOException -> " + url + " " + e.getMessage());
        }
    }

}
