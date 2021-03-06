package com.anglewang.community.mapper;

import com.anglewang.community.model.Question;
import org.apache.ibatis.annotations.Param;

public interface QuestionMapperExt {
    int increaseView(@Param("record") Question record);
    int increaseComment(@Param("record") Question record);
}
