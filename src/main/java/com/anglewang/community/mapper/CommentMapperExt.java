package com.anglewang.community.mapper;

import com.anglewang.community.model.Comment;
import org.apache.ibatis.annotations.Param;

public interface CommentMapperExt {
    int increaseComment(@Param("record") Comment record);
}
