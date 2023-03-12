package com.pixeldreamer.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pixeldreamer.community.mapper.QuestionMapper;
import com.pixeldreamer.community.mapper.UserMapper;
import com.pixeldreamer.community.model.Question;
import com.pixeldreamer.community.model.Users;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PublishContoller {
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish (
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            HttpServletRequest request,
            Model model) {
        
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if (title==null || title=="") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description==null || description=="") {
            model.addAttribute("error", "描述不能为空");
            return "publish";
        }
        if (tag==null || tag=="") {
            model.addAttribute("error", "tag不能为空");
            return "publish";
        }
        
        Users users = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                users = userMapper.findByToken(token);
                if (users!=null) {
                    request.getSession().setAttribute("users", token);
                }
                break;
            }
        }

        if (users == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(users.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        
        questionMapper.create(question);

        return "redirect:/";
    }
}
