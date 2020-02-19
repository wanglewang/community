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
        Integer totalPage=(count/size)+((count%size==0)?0:1);//计算页数
        page = (page>0&&page<=totalPage) ? page : 1;
        paginationDTO.setPagination(totalPage,page);

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

    public PaginationDTO selectMyQuestions(Integer id, Integer page, Integer size) {
        PaginationDTO paginationDTO=new PaginationDTO();
        Integer count = questionMapper.selectCountByCreatorID(id);//数据库总条目
        Integer totalPage=(count/size)+((count%size==0)?0:1);//计算页数
        page = (page>0&&page<=totalPage) ? page : 1;
        paginationDTO.setPagination(totalPage,page);

        Integer offset=size*(page-1);
        List<Question> questions = questionMapper.selectByCreatorID(id,offset,size);
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

    public QuestionDTO selectById(Integer id) {

        Question question = questionMapper.selectById(id);
        User user = userMapper.selectById(question.getCreatorId());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }


    /**
     * "insert into question " +
     *             "(title,description,gmt_create,gmt_modified,creator_id,tag)" +
     *             "values" +
     *             "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creatorId},#{tag})")
     * @param question
     */
    public void insertOrUpdate(Question question) {
        if(question.getId()==null) {
            questionMapper.insert(question);
        }else {
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
