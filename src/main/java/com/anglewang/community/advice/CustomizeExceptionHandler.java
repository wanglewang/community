package com.anglewang.community.advice;

import com.alibaba.fastjson.JSON;
import com.anglewang.community.dto.ResultDTO;
import com.anglewang.community.exception.CustomizeErrorCode;
import com.anglewang.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request,
                                           HttpServletResponse response,
                                           Throwable e,
                                           Model model) {
        String contentType=request.getContentType();
        if("application/json".equals(contentType)) {
            //返回json
            ResultDTO resultDTO;
            if(e instanceof CustomizeException) {
                resultDTO=ResultDTO.errorOf((CustomizeException) e);
            }else {
                resultDTO=ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            response.setStatus(200);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter printWriter;
            try {
                printWriter = response.getWriter();
                printWriter.write(JSON.toJSONString(resultDTO));
                printWriter.close();
            } catch (IOException ex) {

            }
            return null;

        }else {
            //跳转页面
            if(e instanceof CustomizeException) {
                model.addAttribute("message",e.getMessage());
            }else {
                model.addAttribute("message",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
