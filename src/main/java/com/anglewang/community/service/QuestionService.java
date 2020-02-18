package com.anglewang.community.service;

import com.anglewang.community.dto.PaginationDTO;
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

    public PaginationDTO select(Integer page, Integer size) {
        PaginationDTO paginationDTO=new PaginationDTO();
        Integer count = questionMapper.selectCount();//数据库总条目
        paginationDTO.setPagination(count,page,size);
        if(page<1) {
            page=1;
        }
        if(page>paginationDTO.getTotalPage()) {
            page=paginationDTO.getTotalPage();
        }
        Integer offset=size*(page-1);
        List<Question> questions = questionMapper.select(offset,size);
        List<QuestionDTO> questionDTOs=new ArrayList<>();
        for(Question question : questions) {
            User user = userMapper.selectById(question.getCreatorId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        paginationDTO.setQuestionDTOs(questionDTOs);

        return paginationDTO;
    }
}
