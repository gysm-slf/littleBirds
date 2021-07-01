package cn.littleBird.core.service.impl;

import cn.littleBird.core.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    public void sayHello(){
        System.out.println("调用了该方法");
    }
}
