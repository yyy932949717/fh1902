package com.fh.shop.api.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Httpclient {

    public static String postHttpclient(String url,Map<String,String> header,Map<String,String> params){
        CloseableHttpClient build =null;
        HttpPost httpPost=null;
        CloseableHttpResponse execute =null;
        String string="";
        try {
            //创建浏览器
             build = HttpClientBuilder.create().build();
            //访问的网址
             httpPost=new HttpPost(url);
            //设置发送时的头信息
            if(header!=null && header.size()>0){
                Iterator<Map.Entry<String, String>> headerIterator = header.entrySet().iterator();
                while (headerIterator.hasNext()){
                    Map.Entry<String, String> next = headerIterator.next();
                    httpPost.addHeader(next.getKey(),next.getValue());
                }
            }
            //设置发送时参数信息
            if(params!=null && params.size()>0){
                List<BasicNameValuePair> list=new ArrayList();
                Iterator<Map.Entry<String, String>> paramsIterator = params.entrySet().iterator();
                while (paramsIterator.hasNext()){
                    Map.Entry<String, String> next = paramsIterator.next();
                    list.add(new BasicNameValuePair(next.getKey(),next.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
                //发送请求
                 execute = build.execute(httpPost);
                HttpEntity entity = execute.getEntity();
                string = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
                try {
                    if (execute!=null){
                        execute.close();
                    }
                    if(httpPost!=null){
                        httpPost.releaseConnection();
                    }
                    if(build!=null){
                        build.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();

            }
        }

        return string;
    }
}
