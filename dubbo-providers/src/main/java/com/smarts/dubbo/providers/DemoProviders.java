package com.smarts.dubbo.providers;

import com.alibaba.dubbo.config.annotation.Service;
import com.smarts.dubbo.service.FirstService;
import org.springframework.stereotype.Component;

/**
 * Created by yangxuejun
 * on 16-10-31 下午11:09
 */
@Component
@Service()
public class DemoProviders  implements FirstService{
    public String sayHello(String name) {
        System.out.println("receive name : "+name);
        return "hi , "+name;
    }
}
