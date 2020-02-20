package com.anglewang.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"问题已经不再啦"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NOT_LOGIN(2003,"当前操作需要操作，请登录后重试"),
    SYS_ERROR(2004,"服务器异常，等待修复"),
    TYPE_PARAM_ERROR(2005,"评论类型错误"),
    COMMENT_NOT_FOUND(2006,"评论未发现")
    ;
    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code=code;
        this.message=message;
    }
    @Override
    public Integer getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
