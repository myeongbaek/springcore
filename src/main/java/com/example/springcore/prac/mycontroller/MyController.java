package com.example.springcore.prac.mycontroller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping(value = "/test/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id){

        Message message = new Message(StatusEnum.OK, "성공", id);
        HttpHeaders headers = new HttpHeaders();

        return ResponseEntity.ok(message);
    }
}
