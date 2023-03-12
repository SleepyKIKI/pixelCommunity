package com.pixeldreamer.community.provider;

import com.alibaba.fastjson2.JSON;
import com.pixeldreamer.community.dto.AccessTokenDTO;
import com.pixeldreamer.community.dto.GithubUser;

import okhttp3.*;

//import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.stereotype.Component;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();


        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
            .url("https://github.com/login/oauth/access_token")
            .post(body)
            .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String token = split[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Mona: HAHA! you loser");
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
            .url("https://api.github.com/user")
            .get()
            .addHeader("cache-control", "no-cache")
            .addHeader("Authorization", "Bearer " + accessToken)
            .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            // using a hash-generated avatar rather than the avatar on github
            githubUser.setAvatarUrl("https://api.dicebear.com/5.x/adventurer/svg");
            return githubUser;
        } catch (Exception e) {
        }
        return null;
    }
}
