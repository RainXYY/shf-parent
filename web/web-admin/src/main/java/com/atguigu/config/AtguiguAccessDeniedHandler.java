package com.atguigu.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date:2022/5/30
 * Author:夏宇
 * Description:
 */
public class AtguiguAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , AccessDeniedException e) throws IOException
            , ServletException {
        httpServletResponse.sendRedirect("/auth");
    }
}
