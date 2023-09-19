package com.example.demo.service.imp;

import com.example.demo.bean.Estudiante;
import com.example.demo.repository.HelloRepository;
import com.example.demo.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImp implements HelloService {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    HelloRepository helloRepository;

    @Override
    public String getMessageHello() {
        String data = helloRepository.getDataString();
        return data;
    }

    @Override
    public Estudiante getEstudiante() {
        Estudiante estudiante = helloRepository.getDataObjetc();
        return estudiante;
    }
}
