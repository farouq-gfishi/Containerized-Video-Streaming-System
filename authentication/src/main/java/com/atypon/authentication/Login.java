package com.atypon.authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {

    @GetMapping("/login")
    public Boolean login(@RequestParam String id, @RequestParam String password){
        if(id != null && password != null &&
                id.equals("1") && password.equals("123"))
            return Boolean.TRUE;
        else return Boolean.FALSE;
    }
}
