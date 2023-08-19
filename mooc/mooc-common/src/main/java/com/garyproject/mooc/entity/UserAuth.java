package com.garyproject.mooc.entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "users")
public class UserAuth extends BaseEntity{

  @Field(type = FieldType.Text)
  private String username;

  @Field(type = FieldType.Text)
  private String email;
  private String passwd;
  private String confirmPasswd;

  private Integer status;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswd() {
    return passwd;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }

  public String getConfirmPasswd() {
    return confirmPasswd;
  }

  public void setConfirmPasswd(String confirmPasswd) {
    this.confirmPasswd = confirmPasswd;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
