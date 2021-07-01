package cn.littleBird.config;

import cn.littleBird.core.Son;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("cn.littleBird")
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public Son son(){
        return new Son();
    }
}
