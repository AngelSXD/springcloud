package com.swapping.springcloud.ms.gateway.config;

import com.codingapi.tx.netty.service.TxManagerHttpRequestService;
import com.lorne.core.framework.utils.http.HttpUtils;
import org.springframework.stereotype.Service;

@Service
public class TxManagerHttpRequestServiceImpl implements TxManagerHttpRequestService {


    @Override
    public String httpGet(String url) {
        System.out.println("httpGet-start>>>>>>>>>>>>>>gateway>>");
        String res = HttpUtils.get(url);
        System.out.println("httpGet-end>>>>>>>>>>>>>>>>>gateway>>");
        return res;
    }

    @Override
    public String httpPost(String url, String params) {
        System.out.println("httpPost-start>>>>>>>>>>>>>>>>>>>gateway>>");
        String res = HttpUtils.post(url,params);
        System.out.println("httpPost-end>>>>>>>>>>>>>>>>>>>>>>gateway>>");
        return res;
    }

}
