package com.littleBee.bee.filter;

import com.littleBee.bee.service.RedisService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        log.info(request.getRequestURI());
        //以下三行解决跨域问题
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,content-type,token, userId");
        response.setHeader("Access-Control-Allow-Credentials","true");

        if(whiteList(request.getRequestURI())){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = request.getHeader("token");
        String userId = request.getHeader("userId");


        if(token == null || userId == null){
            servletResponse.getWriter().println(new Exception("未登录"));
        }else{
            String redisUserCode = redisService.getUserLoginCode(userId);
            if(redisUserCode == null || !redisUserCode.equals(token)){
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
