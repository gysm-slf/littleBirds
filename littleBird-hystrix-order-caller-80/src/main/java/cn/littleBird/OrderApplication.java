package cn.littleBird;

//import littleBird.rule.Rule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableEurekaClient
//这里不需要注册进EurekaServer,改为使用OpenFeign调用即可,配置中是否注册到eureka选项也是false
@EnableFeignClients
//使用自定义注解或替换负载均衡策略注解，name调用的服务，configuration自定义负载均衡的java文件
//@RibbonClient(name = "PAYMENT-SERVICE",configuration = Rule.class)
@EnableHystrix
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
