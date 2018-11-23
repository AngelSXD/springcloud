package com.swapping.springcloud.ms.sleuth.zipkin.config;

import com.codingapi.tx.netty.service.TxManagerHttpRequestService;
import com.lorne.core.framework.utils.http.HttpUtils;
import org.springframework.stereotype.Service;

@Service
public class TxManagerHttpRequestServiceImpl implements TxManagerHttpRequestService {


    @Override
    public String httpGet(String url) {
        System.out.println("httpGet-start>>>>>>>>>>>>>>SleuthZipkin>>");
        String res = HttpUtils.get(url);
        System.out.println("httpGet-end>>>>>>>>>>>>>>>>>SleuthZipkin>>");
        return res;
    }

    @Override
    public String httpPost(String url, String params) {
        System.out.println("httpPost-start>>>>>>>>>>>>>>>>>>>SleuthZipkin>>");
        String res = HttpUtils.post(url,params);
        System.out.println("httpPost-end>>>>>>>>>>>>>>>>>>>>>>SleuthZipkin>>");
        return res;
    }

}
