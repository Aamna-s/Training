package com.prac1.prac1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class prac1Controller {
    @GetMapping("/api/v1/hello")
    public ResponseEntity<Map<String,String>> hello()
    {
        return ResponseEntity.ok(Map.of("message","hello","message2","000000"));
    }

    @GetMapping("/hello")
    public String hello1()
    {
        return "hello";
    }
}
