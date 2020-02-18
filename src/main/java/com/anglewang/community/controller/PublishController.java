package com.anglewang.community.controller;

import com.anglewang.community.mapper.QuestionMapper;
import com.anglewang.community.model.Question;
import com.anglewang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 发布新的问题
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String executePublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model) {

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        //校验
        if(title==null||title=="") {
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description==null||description=="") {
            model.addAttribute("error","描述不能为空");
            return "publish";
        }
        if(tag==null||tag=="") {
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        User user= (User) request.getSession().getAttribute("user");
        if(user==null) {
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreatorId(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtModified());
        questionMapper.insert(question);
        return "redirect:/";
    }
}
