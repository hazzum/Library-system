package com.hazem.library.rest;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParentRestController {

    @GetMapping("/")
    public String sayHello() {
        return "Hello! Time on server is "+LocalDateTime.now();

    }
}
