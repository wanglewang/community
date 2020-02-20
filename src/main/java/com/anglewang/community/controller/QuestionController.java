package com.anglewang.community.controller;

import com.anglewang.community.dto.QuestionDTO;
import com.anglewang.community.mapper.QuestionMapper;
import com.anglewang.community.mapper.QuestionMapperExt;
import com.anglewang.community.model.Question;
import com.anglewang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 查看该问题
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Integer id,
                           Model model) {
        QuestionDTO questionDTO=questionService.selectById(id);
        questionService.increaseView(id);
        model.addAttribute("questionDTO",questionDTO);

        return "question";
    }
}
