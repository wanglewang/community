package com.anglewang.community.service;

import com.anglewang.community.dto.QuestionDTO;
import com.anglewang.community.mapper.QuestionMapper;
import com.anglewang.community.mapper.UserMapper;
import com.anglewang.community.model.Question;
import com.anglewang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public List<QuestionDTO> select() {
        List<Question> questions = questionMapper.select();
        List<QuestionDTO> questionDTOs=new ArrayList<>();
        for(Question question : questions) {
            User user = userMapper.selectById(question.getCreatorId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }
}
