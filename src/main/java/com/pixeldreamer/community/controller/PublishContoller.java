package com.pixeldreamer.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublishContoller {
    
    @GetMapping("publish")
    public String publish() {
        return "publish";
    }
}
