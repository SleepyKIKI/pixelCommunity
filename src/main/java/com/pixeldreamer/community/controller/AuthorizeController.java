package com.pixeldreamer.community.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pixeldreamer.community.dto.*;
import com.pixeldreamer.community.mapper.UserMapper;
import com.pixeldreamer.community.model.Users;
import com.pixeldreamer.community.provider.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        //accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_id("6ea671811b8c4bbd1c1d");
        //accessTokenDTO.setClient_secret(clientScret);
        accessTokenDTO.setClient_secret("c393c3471a97a254e7a412c07a7a00b08e8e9595");
        accessTokenDTO.setCode(code);
        //accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setRedirect_uri("http://localhost:8666/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser gihubUser = githubProvider.getUser(accessToken);

        if (gihubUser != null && gihubUser.getId()!=null) {
            // sql model
            Users users = new Users();
            String token = UUID.randomUUID().toString();
            users.setToken(token);
            users.setName(gihubUser.getName());
            users.setAccountId(String.valueOf(gihubUser.getId()));
            users.setGmtCreate(System.currentTimeMillis());
            users.setGmtModified(users.getGmtCreate());

            userMapper.insert(users);

            response.addCookie(new Cookie("token", token));

            return "redirect:/";
        }
        else {
            return "redirect:/";
        }
    }
}
