package cn.littleBird.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
//启用Hystrix图形化监控功能(管理端)
@EnableHystrixDashboard
public class HystrixDashboardOrder {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardOrder.class,args);
    }
}
