package com.littleBee.bee.filter;

import com.littleBee.bee.service.RedisService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log
@WebFilter
public class LoginFilter implements Filter {
    @Autowired
    RedisService redisService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String userCode = servletRequest.getParameter("userCode");
        String userName = servletRequest.getParameter("userName");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info(request.getRequestURI());

        if(whiteList(request.getRequestURI())){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if(userCode == null || userName == null){
            servletResponse.getWriter().println(new Exception("未登录"));
        }else{
            String redisUserCode = redisService.getUserLoginCode(userName);
            if(redisUserCode == null || !redisUserCode.equals(userCode)){
                servletResponse.getWriter().println(new Exception("登录过期或登录信息错误"));
            }else{
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    private boolean whiteList(String uri){
        List<String> list = new ArrayList<>();
        list.add("verification");
        list.add("login");
        list.add("register");

        for( String data : list){
            if(uri.contains(data)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
