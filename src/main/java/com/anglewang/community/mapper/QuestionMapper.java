package com.anglewang.community.mapper;

import com.anglewang.community.dto.QuestionDTO;
import com.anglewang.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question " +
            "(title,description,gmt_create,gmt_modified,creator_id,tag)" +
            "values" +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creatorId},#{tag})")
    void insert(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> select(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer selectCount();

    @Select("select count(1) from question where creator_id=#{id}")
    Integer selectCountByCreatorID(@Param("id") Integer id);

    @Select("select * from question where creator_id=#{id} limit #{offset},#{size}")
    List<Question> selectByCreatorID(@Param("id") Integer id,@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from question where id=#{id}")
    Question selectById(@Param("id") Integer id);

    @Update("update question " +
            "set title=#{title},description=#{description}," +
            "gmt_modified=#{gmtModified},tag=#{tag}" +
            "where id=#{id}")
    void update(Question question);
}
