package com.garyproject.mooc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan(value = "com/garyproject/mooc/dao")
@SpringBootApplication
@EnableScheduling
public class MoocApplication {

  public static void main(String[] args) {
    SpringApplication.run(MoocApplication.class, args);
  }

}
