package com.garyproject.mooc.aspect;

import com.alibaba.fastjson.JSON;
import com.garyproject.mooc.entity.WebLog;
import com.garyproject.mooc.support.OperLog;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;

import static freemarker.template.utility.StringUtil.JsStringEncCompatibility.JSON;

@Aspect
@Component
public class WebAspect {
  private static Logger logger = LoggerFactory.getLogger(WebAspect.class);

  //定义切点
  //在注解切入代码
  @Pointcut("@annotation(com.garyproject.mooc.support.OperLog)")
  public void logPointCut() {

  }

  //环绕通知
  @Around("logPointCut()")
  public void log(ProceedingJoinPoint proceedingJoinPoint) {
    logger.info("============");

    WebLog webLog = new WebLog();
    MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
    //获取方法
    Method method = signature.getMethod();

    OperLog operLog = method.getAnnotation(OperLog.class);
    if (operLog != null) {
      //注解上的描述
      String value = operLog.message();
      logger.info("获取操作" + value);
      webLog.setMessage(value);
    }

    String className = proceedingJoinPoint.getTarget().getClass().getName();
    logger.info("获取类名" + className);
    String methodName = method.getName();
    logger.info("获取方法名" + methodName);
    webLog.setMethod(className + "." + methodName);

    Object[] args = proceedingJoinPoint.getArgs();
    String params = com.alibaba.fastjson.JSON.toJSONString(args);
    logger.info("获取参数" + params);
    webLog.setParams(params);

    webLog.setCreateTime(new Date());
    logger.info("获取时间" + webLog.getCreateTime());

    ServletRequestAttributes attributes = (ServletRequestAttributes) org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();

    logger.info("获取url" + request.getRequestURL());
    logger.info("请求HTTP方式" + request.getMethod());
    logger.info("获取IP" + request.getRemoteAddr());
    webLog.setIp(request.getRemoteAddr());

    long start = System.currentTimeMillis();
    long time = System.currentTimeMillis() - start;
    logger.info("获取耗时" + time);
    webLog.setTotalTime(time);

    //数据插入就可以在数据库中找到

    logger.info("======请求结束======");
  }
}
