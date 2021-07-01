package cn.littleBird;

import cn.littleBird.rule.Rule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
//使用自定义注解或替换负载均衡策略注解，name调用的服务，configuration自定义负载均衡的java文件
@RibbonClient(name = "PAYMENT-SERVICE",configuration = Rule.class)
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
