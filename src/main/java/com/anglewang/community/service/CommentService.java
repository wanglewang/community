package com.anglewang.community.service;

import com.anglewang.community.enums.CommentTypeEnums;
import com.anglewang.community.exception.CustomizeErrorCode;
import com.anglewang.community.exception.CustomizeException;
import com.anglewang.community.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    public void insert(Comment comment) {
        if(comment.getParentId()==null|| comment.getParentId()==0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType()==null|| !CommentTypeEnums.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_ERROR);
        }
    }
}
