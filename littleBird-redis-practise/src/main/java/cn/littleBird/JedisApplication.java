package cn.littleBird;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class JedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(JedisApplication.class,args);
    }
}
