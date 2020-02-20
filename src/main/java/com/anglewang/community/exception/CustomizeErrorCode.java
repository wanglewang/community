package com.anglewang.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND("问题已经不再啦");
    private String message;
    CustomizeErrorCode(String message) {
        this.message=message;
    }
    @Override
    public String getMessage() {
        return message;
    }

}
