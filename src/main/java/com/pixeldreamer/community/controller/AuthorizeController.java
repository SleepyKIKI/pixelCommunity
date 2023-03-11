package com.pixeldreamer.community.controller;

import java.util.UUID;

import org.apache.catalina.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pixeldreamer.community.dto.*;
import com.pixeldreamer.community.mapper.UserMapper;
import com.pixeldreamer.community.model.Users;
import com.pixeldreamer.community.provider.*;

import jakarta.servlet.http.HttpServletRequest;

//@MapperScan("com.pixeldreamer.community.mapper")
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    /* cannnot run
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.scret}")
    private String clientScret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
     */

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) {
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
        GithubUser gihubuser = githubProvider.getUser(accessToken);

        if (gihubuser != null) {
            // sql model
            Users users = new Users();
            users.setToken(UUID.randomUUID().toString());
            users.setName(gihubuser.getName());
            users.setAccountId(String.valueOf(gihubuser.getId()));
            users.setGmtCreate(System.currentTimeMillis());
            users.setGmtModified(users.getGmtCreate());

            userMapper.insert(users);

            // session
            request.getSession().setAttribute("user", gihubuser);
            return "redirect:/";
        }
        else {
            return "redirect:/";
        }
    }
}
