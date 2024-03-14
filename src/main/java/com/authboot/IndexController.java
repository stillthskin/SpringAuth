package com.authboot;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class IndexController {

    @GetMapping
    public ResponseEntity<String> informer(){
        return ResponseEntity.ok("It works");
    }
}
