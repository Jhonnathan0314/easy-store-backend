package com.sophie.store.backend.security.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TestController {

    @GetMapping(value = "test")
    public String welcome() {
        return "Welcome to a secure endpoint";
    }

}
