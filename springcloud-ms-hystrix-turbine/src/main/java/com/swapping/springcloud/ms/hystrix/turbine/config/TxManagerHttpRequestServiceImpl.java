package com.swapping.springcloud.ms.hystrix.turbine.config;

import com.codingapi.tx.netty.service.TxManagerHttpRequestService;
import com.lorne.core.framework.utils.http.HttpUtils;
import org.springframework.stereotype.Service;

/**
 * @author xuezhijian
 * @date 2018/10/21 下午8:54
 * @description
 */
@Service
public class TxManagerHttpRequestServiceImpl implements TxManagerHttpRequestService {

    @Override
    public String httpGet(String url) {
        System.out.println("httpGet-start======Turbine====Dashboard");
        String res = HttpUtils.get(url);
        System.out.println("httpGet-end======Turbine====Dashboard");
        return res;
    }

    @Override
    public String httpPost(String url, String params) {
        System.out.println("httpPost-start======Turbine====Dashboard");
        String res = HttpUtils.post(url,params);
        System.out.println("httpPost-end======Turbine====Dashboard");
        return res;
    }
}
