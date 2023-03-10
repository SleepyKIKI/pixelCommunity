package com.pixeldreamer.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pixeldreamer.community.dto.*;
import com.pixeldreamer.community.provider.*;

import jakarta.servlet.http.HttpServletRequest;

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
        accessTokenDTO.setRedirect_uri("http://localhost:8081/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);

        if (user != null) {
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        }
        else {
            return "redirect:/";
        }
    }
}
