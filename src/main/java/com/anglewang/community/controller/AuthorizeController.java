package com.anglewang.community.controller;

import com.anglewang.community.dto.AccessTokenDTO;
import com.anglewang.community.dto.GitHubUser;
import com.anglewang.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @GetMapping("/callback")
    /**
     * 调用access_token:携带code
     */
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("b85fd778f06b1317767d");
        accessTokenDTO.setClient_secret("4c838cd75f26966e84ff535607e3e8eacf4ff474");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser user = gitHubProvider.getUser(accessToken);
        return "index";
    }


}
