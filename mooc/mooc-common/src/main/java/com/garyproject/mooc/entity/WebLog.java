package com.garyproject.mooc.entity;

import lombok.Data;

@Data
public class WebLog extends BaseEntity {
  private String userId;

  private String message;

  private String operation;

  private String method;

  private String params;

  private String ip;

  private Long totalTime;

}
