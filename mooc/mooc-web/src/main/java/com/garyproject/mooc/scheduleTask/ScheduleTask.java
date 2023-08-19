package com.garyproject.mooc.scheduleTask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Component
public class ScheduleTask {

  // 每隔1秒执行一次
  //@Scheduled(cron = "*/1 * * * * ?")

  // 每隔1秒执行一次
  //@Scheduled(fixedRate = 1000)

  // 上一次执行完毕时间点之后1秒再执行
  @Scheduled(fixedDelay = 1000)
  public void sayHello() {
    System.out.println("Hello, Gary!");
  }
}
