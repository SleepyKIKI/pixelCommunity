package com.pixeldreamer.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.pixeldreamer.community.mapper.UserMapper;
import com.pixeldreamer.community.model.Users;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;


    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                Users users = userMapper.findByToken(token);
                if (users!=null) {
                    request.getSession().setAttribute("users", token);
                }
                break;
            }
        }
        return "index";
    }

}
