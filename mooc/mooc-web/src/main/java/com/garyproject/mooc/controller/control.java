package com.garyproject.mooc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class control {

  //private static Logger logger = LoggerFactory.getLogger(control.class);

  @RequestMapping("helloword")
  public String hello() {
    log.info("hello world 日志");
    return "HelloWorld";
  }

}
