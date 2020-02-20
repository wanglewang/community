package com.anglewang.community.enums;

public enum CommentTypeEnums {
    QUESTION(1),
    COMMENT(2)
    ;
    private Integer type;
    CommentTypeEnums(Integer type) {
        this.type=type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnums value : CommentTypeEnums.values()) {
            if(value.getType()==type)
                return true;
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
