package com.garyproject.mooc.utils;

public class JsonResponse<T> {
  //成功失败的代码
  private String code;
  private String msg;
  private T data;

  public JsonResponse(String code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public JsonResponse(T data) {
    this.data = data;
    msg = "success";
    code = "0";
  }

  public static <T> JsonResponse<T> success() {
    return new JsonResponse<T>(null);
  }

  public static JsonResponse<String > success(String data) {
    return new JsonResponse<>(data);
  }

  //失败
  public static <T> JsonResponse<T> fail() {
    return new JsonResponse<T>("1",null);
  }

  public static JsonResponse<String> fail(String code, String msg) {
    return new JsonResponse<String>(code, msg);
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
