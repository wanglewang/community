package com.anglewang.community.controller;

import com.anglewang.community.dto.CommentDTO;
import com.anglewang.community.dto.QuestionDTO;
import com.anglewang.community.enums.CommentTypeEnums;
import com.anglewang.community.service.CommentService;
import com.anglewang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 查看该问题
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Long id,
                           Model model) {
        List<CommentDTO> commentDTOS=commentService.selectByQuestionOrCommentId(id, CommentTypeEnums.QUESTION);
        QuestionDTO questionDTO=questionService.selectById(id);
        questionService.increaseView(id);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("commentDTOS",commentDTOS);

        return "question";
    }
}
