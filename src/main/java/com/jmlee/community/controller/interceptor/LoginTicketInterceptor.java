package com.jmlee.community.controller.interceptor;

import com.jmlee.community.entity.LoginTicket;
import com.jmlee.community.entity.User;
import com.jmlee.community.service.UserService;
import com.jmlee.community.util.CookieUtil;
import com.jmlee.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    // 在controller被调用之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 从cookie中获取凭证
        String ticket = CookieUtil.getValue(request, "ticket");

        if (ticket != null) {
            // 去数据库查询用户登录凭证(现已改成去Redis中查找凭证，不去数据库查找了)
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            // 检查凭证是否有效
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {

                // 根据登录凭证查询用户
                User user = userService.findUserById(loginTicket.getUserId());

                // 在本次请求中持有用户（代替session存储user对象）
                hostHolder.setUser(user);

                // 构建用户认证的结果，并存入SecurityContext,以便Security进行授权
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        user.getPassword(),
                        userService.getAuthorities(user.getId())
                );
                SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
            }
        }
        // 用户未登录，这里记得返回true放行，很容易忽略
        return true;
    }

    // 在controller方法调用之后，模板引擎渲染之前调用
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            modelAndView.addObject("loginUser",user);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 手动清空ThreadLocal内部数据，防止内存泄露
        hostHolder.clear();
        SecurityContextHolder.clearContext();
    }
}
