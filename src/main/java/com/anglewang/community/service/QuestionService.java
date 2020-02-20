package com.anglewang.community.service;

import com.anglewang.community.dto.PaginationDTO;
import com.anglewang.community.dto.QuestionDTO;
import com.anglewang.community.exception.CustomizeErrorCode;
import com.anglewang.community.exception.CustomizeException;
import com.anglewang.community.mapper.QuestionMapper;
import com.anglewang.community.mapper.QuestionMapperExt;
import com.anglewang.community.mapper.UserMapper;
import com.anglewang.community.model.Question;
import com.anglewang.community.model.QuestionExample;
import com.anglewang.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    @Autowired
    private QuestionMapperExt questionMapperExt;

    public PaginationDTO select(Integer page, Integer size) {
        PaginationDTO paginationDTO=new PaginationDTO();
        Integer count = (int)questionMapper.countByExample(new QuestionExample());//数据库总条目
        Integer totalPage=(count/size)+((count%size==0)?0:1);//计算页数
        page = (page>0&&page<=totalPage) ? page : 1;
        paginationDTO.setPagination(totalPage,page);

        Integer offset=size*(page-1);
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<QuestionDTO> questionDTOs=new ArrayList<>();
        for(Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreatorId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        paginationDTO.setQuestionDTOs(questionDTOs);

        return paginationDTO;
    }

    public PaginationDTO selectMyQuestions(Long id, Integer page, Integer size) {
        PaginationDTO paginationDTO=new PaginationDTO();

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorIdEqualTo(id);
        Integer count = (int)questionMapper.countByExample(questionExample);//数据库总条目
        Integer totalPage=(count/size)+((count%size==0)?0:1);//计算页数
        page = (page>0&&page<=totalPage) ? page : 1;
        paginationDTO.setPagination(totalPage,page);

        Integer offset=size*(page-1);
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOs=new ArrayList<>();
        for(Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreatorId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOs.add(questionDTO);
        }
        paginationDTO.setQuestionDTOs(questionDTOs);

        return paginationDTO;
    }

    public QuestionDTO selectById(Long id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        //捕获异常
        if(question==null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(question.getCreatorId());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void insertOrUpdate(Question question) {
        if(question.getId()==null) {
            questionMapper.insert(question);
        }else {
            Question updateQuestion=new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());

            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updated=questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if(updated!=1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void increaseView(Long id) {
        Question question=new Question();
        question.setId(id);
        question.setViewCount(1);
        questionMapperExt.increaseView(question);
    }
}
