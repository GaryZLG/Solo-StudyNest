package com.garyproject.mooc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

import javax.xml.crypto.Data;

public class BaseEntity {

  @Id
  @Field(type = FieldType.Long)
  private long id;
  private Date createTime;
  private Date updateTime;
  private Integer del;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getDel() {
    return del;
  }

  public void setDel(Integer del) {
    this.del = del;
  }
}
