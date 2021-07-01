package cn.littleBird.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/Hi")
    public String say(){
        return "hello rabbit!";
    }
}
