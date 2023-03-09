package com.pixeldreamer.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MonaController {
    
    @GetMapping("/mona")
    public String mona(@RequestParam(name = "name", required = false, defaultValue = "Mona") String name, Model model){
        model.addAttribute("name", name);
        return "mona";
    }
}
