package com.itheima.security.springmvc.interceptor;

import com.itheima.security.springmvc.model.UserDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class SimpleAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // check user request url has privilege
        Object object = request.getSession().getAttribute(UserDto.SESSION_USER_KEY);

        if (object == null) {
            writeContent(response, "Please log in");
        }

        UserDto userDto = (UserDto) object;

        String requestURI = request.getRequestURI();

        if (userDto.getAuthorities().contains("p1") && requestURI.contains("/r/r1")) {return true;}

        if (userDto.getAuthorities().contains("p2") && requestURI.contains("/r/r2")) {return true;}

        writeContent(response, "no privilege, access denied");

        return false;
    }

    private void writeContent(HttpServletResponse response, String msg) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.println(msg);
        writer.close();
        response.resetBuffer();
    }
}
