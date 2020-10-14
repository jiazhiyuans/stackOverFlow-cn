package com.ali.edu.stackoverflow.utils;

import com.ali.edu.stackoverflow.entity.DeviceAddParam;
import com.ali.edu.stackoverflow.utils.entity.FactorCategory;
import com.ali.edu.stackoverflow.zdemo.Manufacturer;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class HttpUtils {

    private static  final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private PoolingHttpClientConnectionManager phccm;
    private RequestConfig requestConfig;

    public HttpUtils() {
        phccm = new PoolingHttpClientConnectionManager();
        requestConfig = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(1000)
                .setSocketTimeout(10*1000).build();
    }

    /**
     * 爬虫接口
     * @param url
     * @return
     */
    public String getHtml(String url) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(phccm).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        String html = null;
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (null == response) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = response.getEntity();
                html = EntityUtils.toString(entity, "utf8");
            }
        } catch (Exception e) {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return html;
    }

    /**
     * 添加JSON数据
     * @param url
     * @param deviceAddParam
     * @return
     */
    public Boolean postAddDevice(String url, DeviceAddParam deviceAddParam) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(phccm).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        ByteArrayEntity requestEntity = null;
        //设置body的信息
        String body = JSONObject.toJSONString(deviceAddParam);
        try {
            requestEntity = new ByteArrayEntity(body.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("post 解析异常");
        }
        requestEntity.setContentType("application/json");
        httpPost.setEntity(requestEntity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (null == response) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = response.getEntity();
                String responseMessage = EntityUtils.toString(entity, "utf8");
                return responseMessage.contains("true");
            }
        } catch (Exception e) {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return false;
    }


    public Boolean postAddManufacture(String url, Manufacturer manufacturer) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(phccm).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        ByteArrayEntity requestEntity = null;
        //设置body的信息
        String body = JSONObject.toJSONString(manufacturer);
        try {
            requestEntity = new ByteArrayEntity(body.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("post 解析异常");
        }
        requestEntity.setContentType("application/json");
        httpPost.setEntity(requestEntity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (null == response) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = response.getEntity();
                String responseMessage = EntityUtils.toString(entity, "utf8");
                return responseMessage.contains("true");

            }
        } catch (Exception e) {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 导入大气的类别
     * @param url
     * @param factorCategory
     * @return
     */
    public String postAddFactor(String url,  FactorCategory factorCategory) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(phccm).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        ByteArrayEntity requestEntity = null;
        //设置body的信息
        String body = JSONObject.toJSONString(factorCategory);
        try {
            requestEntity = new ByteArrayEntity(body.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error("post 解析异常");
        }
        requestEntity.setContentType("application/json");
        httpPost.setEntity(requestEntity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (null == response) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = response.getEntity();
                String responseMessage = EntityUtils.toString(entity, "utf8");
                logger.info("添加Device的返回信息 " + responseMessage);
            }
        } catch (Exception e) {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return "sucess";
    }
}
