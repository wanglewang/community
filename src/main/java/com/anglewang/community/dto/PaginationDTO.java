package com.anglewang.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页用到的页面类
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOs;
    private boolean hasPrevious;//是否显示上一页标签
    private boolean hasNext;//是否显示下一页标签
    private boolean hasFirst;//是否显示第一页标签
    private boolean hasLast;//是否显示最后一页标签
    private Integer currentPage;//当前页
    private List<Integer> pages=new ArrayList<>();//所有页数
    private Integer totalPage;//总页数
    private Integer currentPages=7;//页面展示的页标签个数（奇数）

    /**
     *
     * @param count  问题个数
     * @param page 当前页
     * @param size 一页包含几个问题
     */
    public void setPagination(Integer count, Integer page, Integer size) {

        Integer halfCurrentPages=currentPages/2;
        totalPage=(count/size)+((count%size==0)?0:1);//计算页数
        if(page<1) {
            page=1;
        }
        if(page>totalPage) {
            page=getTotalPage();
        }
        currentPage=page;
        hasPrevious=(currentPage>1);
        hasFirst=(currentPage>(halfCurrentPages+1));
        hasNext=(currentPage<totalPage);
        hasLast=(currentPage<(totalPage-halfCurrentPages));

        pages.add(currentPage);
        for(int i=1;i<=halfCurrentPages;i++) {
            if(currentPage-i>0) {
                pages.add(0,currentPage-i);
            }
            if(currentPage+i<=totalPage) {
                pages.add(currentPage+i);
            }
        }

    }

}
