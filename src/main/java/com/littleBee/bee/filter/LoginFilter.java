package com.littleBee.bee.filter;

import com.littleBee.bee.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

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

    @Override
    public void destroy() {

    }
}
