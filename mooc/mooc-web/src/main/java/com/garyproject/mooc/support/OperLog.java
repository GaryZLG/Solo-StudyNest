package com.garyproject.mooc.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperLog {
  /**
   * 介绍
   * @return
   */
  String message();

  /**
   * 日志类型
   * @return
   */
  String operation();


}
