package com.anglewang.community.dto;

import com.anglewang.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creatorId;
    private Integer commentCount;
    private Integer viewCount;
    private Integer supportCount;
    private String tag;
    private User user;
}
