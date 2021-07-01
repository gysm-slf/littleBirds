package cn.littleBird.core.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//该类配置好后可以使用80端口调用8001
@Configuration
public class ApplicationContextConfig {

    @Bean
    //开启负载均衡,采用轮询方式调用PAYMENT-SERVICE服务下的每一台机器
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
