package com.jmlee.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 统一记录服务层日志切面
 * @author jmLee
 */
@Component
@Aspect
public class ServiceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Pointcut("execution(* com.jmlee.community.service.*.*(..))")
    public void pointcut() { }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        // 日志格式：用户[ip:1.2.3.4],在 [xxx] 访问了 [com.jmlee.community.service.xxx()]
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 判断当前请求是否通过Controller层去访问Service层（即通过前端页面发起请求，而非后端内部自身的调用，如EventConsumer内部使用了MessageService）
        if (requestAttributes == null) {
            //TODO
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();

        String ip = request.getRemoteHost();
        String now = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());

        String targetMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

        logger.info(String.format("用户:[%s],在[%s]访问了[%s]方法.",ip,now,targetMethod));
    }

}
