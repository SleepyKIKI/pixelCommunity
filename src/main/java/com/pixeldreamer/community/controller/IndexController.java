package com.pixeldreamer.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pixeldreamer.community.dto.QuestionDTO;
import com.pixeldreamer.community.mapper.UserMapper;
import com.pixeldreamer.community.model.Users;
import com.pixeldreamer.community.service.QuestionService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        /*using cookie to establish a long-time sign*/
        Cookie[] cookies = request.getCookies();
        if (cookies!=null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    Users users = userMapper.findByToken(token);
                    if (users!=null) {
                        request.getSession().setAttribute("users", users);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionList = questionService.List();
        model.addAttribute("questions", questionList);

        return "index";
    }

}
