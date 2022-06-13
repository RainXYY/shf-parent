package com.atguigu.aspect;

import com.atguigu.util.IpUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 包名:com.atguigu.aspect
 *
 * @author 夏宇
 * 日期2022-05-31  10:18
 * 切入点: 用来描述那些需要去增强/改变的方法
 * 通知: 封装了增强/改变的代码的方法
 * 切面: 存放通知的类
 */
@Component
@Aspect
public class ControllerAspect {
    /**
     * SLF4J的日志记录器对象
     */
    private Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
    @Pointcut("execution(* com.atguigu.controller.*.*(..))")
    public void controllerAction(){
    }

    /**
     * 记录操作日志:
     * 用户名 | url | 请求方式 | ip地址 | 调用的方法 | 调用方法时候的参数 | 执行时间
     * @param proceedingJoinPoint
     * @return
     */
    @Around("controllerAction()")
    public Object recordLogs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //创建StringBuilder用来拼接日志内容
        StringBuilder stringBuilder = new StringBuilder("");
        //1. 获取用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof User)) {
            //说明没登录，不记录操作
            return proceedingJoinPoint.proceed();
        }
        //如果登录了
        User user = (User) authentication.getPrincipal();
        //获取到用户名
        String username = user.getUsername();
        stringBuilder.append(username);
        //2. 获取请求相关的数据
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        //获取请求对象
        HttpServletRequest request = sra.getRequest();
        //2.1 获取url
        String url = request.getRequestURL().toString();
        stringBuilder.append("|"+url);
        //2.2 获取请求方式
        String method = request.getMethod();
        stringBuilder.append("|"+method);
        //2.3 获取IP地址
        String ipAddress = IpUtil.getIpAddress(request);
        stringBuilder.append("|"+ipAddress);
        //3. 获取切入点相关的信息
        //3.1 获取调用的方法: 类的全限定名.方法名
        Signature signature = proceedingJoinPoint.getSignature();
        String classMethod = signature.getDeclaringTypeName() + "." + signature.getName();
        stringBuilder.append("|"+classMethod);
        //3.2 获取方法的参数
        Object[] args = proceedingJoinPoint.getArgs();
        if (args != null && args.length > 0) {
            stringBuilder.append("|");
            for (int i = 0; i < args.length; i++) {
                if (i < args.length - 1) {
                    //遍历出每一个参数
                    stringBuilder.append(args[i] + ",");
                }else {
                    stringBuilder.append(args[i]);
                }
            }
        }
        long startTime = 0;
        try {
            //4. 获取执行时长
            //4.1 获取切入点执行之前的时间
            startTime = System.currentTimeMillis();
            //4.2 执行切入点
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        } finally {
            //4.3 记录执行切入点之后的时间
            long endTime = System.currentTimeMillis();
            //4.4 计算出方法的执行时长
            long executeTime = endTime - startTime;

            stringBuilder.append("|"+executeTime);
            logger.info("request logs:"+stringBuilder);
        }
    }
}