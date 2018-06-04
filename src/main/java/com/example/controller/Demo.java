package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo {
    @GetMapping("/aa")
    public String helloworld(){
        return "HelloWorld!";
    }
}
