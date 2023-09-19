package com.example.demo.controller;

import com.example.demo.bean.Estudiante;
import com.example.demo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping()
    public String hello() {
        String messageHello = helloService.getMessageHello();
        return messageHello;
    }

    @GetMapping("/a")
    public String a() {
        Estudiante estudiante = helloService.getEstudiante();
        return estudiante.getPaterno();
    }

    @GetMapping("/b")
    public String b() {
        return "b";
    }
}
