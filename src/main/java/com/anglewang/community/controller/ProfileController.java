package com.anglewang.community.controller;

import com.anglewang.community.dto.PaginationDTO;
import com.anglewang.community.mapper.UserMapper;
import com.anglewang.community.model.User;
import com.anglewang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{section}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name="section") String section,
                          Model model,
                          @RequestParam(value = "page",defaultValue = "1")Integer page,
                          @RequestParam(value = "size",defaultValue = "2")Integer size) {
        Cookie[] cookies = request.getCookies();
        User user=null;
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")) {
                    String token=cookie.getValue();
                    user=userMapper.findByToken(token);
                    if(user!=null) {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if(user==null) {
            return "redirect:/";
        }
        if("questions".equals(section)) {
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else if("replies".equals(section)) {
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO paginationDTO = questionService.selectMyQuestions(user.getId(), page, size);
        model.addAttribute("paginationDTO",paginationDTO);
        return "profile";
    }
}
