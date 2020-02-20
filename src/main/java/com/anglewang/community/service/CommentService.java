package com.anglewang.community.service;

import com.anglewang.community.enums.CommentTypeEnums;
import com.anglewang.community.exception.CustomizeErrorCode;
import com.anglewang.community.exception.CustomizeException;
import com.anglewang.community.mapper.CommentMapper;
import com.anglewang.community.mapper.QuestionMapper;
import com.anglewang.community.mapper.QuestionMapperExt;
import com.anglewang.community.model.Comment;
import com.anglewang.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionMapperExt questionMapperExt;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId()==null|| comment.getParentId()==0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType()==null|| !CommentTypeEnums.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_ERROR);
        }

        if ( comment.getType() == CommentTypeEnums.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey( comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode. QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            Question question1=new Question();
            question1.setId(question.getId());
            question1.setCommentCount(1);
            questionMapperExt.increaseComment(question);
        }

    }

}
