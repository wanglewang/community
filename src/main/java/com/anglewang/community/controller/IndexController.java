package com.anglewang.community.controller;

import com.anglewang.community.dto.PaginationDTO;
import com.anglewang.community.dto.QuestionDTO;
import com.anglewang.community.mapper.QuestionMapper;
import com.anglewang.community.mapper.UserMapper;
import com.anglewang.community.model.Question;
import com.anglewang.community.model.User;
import com.anglewang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    /**
     * 用户进入首页时，先检测浏览器中的token
     * 如果有符合条件的token，就登录成功
     * 否则需要用户手动登录
     * @param request
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(value = "page",defaultValue = "1")Integer page,
                        @RequestParam(value = "size",defaultValue = "2")Integer size) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")) {
                    String token=cookie.getValue();
                    User user=userMapper.findByToken(token);
                    if(user!=null) {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        PaginationDTO paginationDTO=questionService.select(page,size);
        model.addAttribute("paginationDTO",paginationDTO);
        return "index";
    }
}
