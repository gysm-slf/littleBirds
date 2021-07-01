package cn.littleBird;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication里面的@ComponentScan默认会扫描主启动类同级目录下的所有文件
@SpringBootApplication
@EnableEurekaClient
//熔断器注解 加上此注解，服务降级的兜底方法那些才会生效
@EnableCircuitBreaker
public class HystrixPaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixPaymentApplication.class,args);
    }

    /* 此配置是为了服务监控而配置，与服务容错本身五官，springcloud升级后的坑
    * servletRegistrationBean因为springboot的默认路径不是"/hystrix.stream"
    * 只要在自己的项目里配置上下面的servlet就可以了 */
    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(streamServlet);
        servletRegistrationBean.setLoadOnStartup(1);
        servletRegistrationBean.addUrlMappings("/hystrix.stream");
        servletRegistrationBean.setName("HystrixMetricsStreamServlet");
        return servletRegistrationBean;
    }
}
