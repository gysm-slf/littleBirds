package cn.littleBird.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

//想要生效要加这个注解 注册到spring容器中
@Component
public class LittleBirdBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
//        这里为什么不用BeanDefinition  因为BeanDefinition是一个接口 很多要使用的方法在该接口中没有 都是在子类中实现的 常用的也就是这个子类
//        BeanDefinition userServiceImpl = configurableListableBeanFactory.getBeanDefinition("userServiceImpl");
//        GenericBeanDefinition userServiceImpl = (GenericBeanDefinition)configurableListableBeanFactory.getBeanDefinition("userServiceImpl");
//        userServiceImpl.setBeanClass(UserController.class);
    }
}
