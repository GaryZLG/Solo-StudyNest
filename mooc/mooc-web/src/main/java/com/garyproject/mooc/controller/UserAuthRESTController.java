package com.garyproject.mooc.controller;

import com.garyproject.mooc.entity.Comment;
import com.garyproject.mooc.entity.UserAuth;
import com.garyproject.mooc.service.ICommentService;
import com.garyproject.mooc.service.IUserAuthService;
import com.garyproject.mooc.service.imp.ElasticsearchService;
import com.garyproject.mooc.support.OperLog;
import com.garyproject.mooc.support.UserSupport;
import com.garyproject.mooc.utils.JsonResponse;
import com.garyproject.mooc.utils.OperationType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import jdk.dynalink.Operation;

@RestController
public class UserAuthRESTController {

  @Autowired
  private IUserAuthService iUserAuthService;

  @Autowired
  private UserSupport userSupport;

  @Autowired
  private ElasticsearchService elasticsearchService;

  @Autowired
  private ICommentService iCommentService;

  @OperLog(message = "注册用户", operation = OperationType.SIGNIN)

  @PostMapping("/users")
  public JsonResponse<String> addUser(@RequestBody UserAuth userAuth) {
    iUserAuthService.insert(userAuth);
    elasticsearchService.addUser(userAuth);
    return JsonResponse.success();
  }

  @GetMapping("/users/{username}")
  public boolean getUser(@PathVariable String username) {
    return iUserAuthService.getByUsername(username);
  }

  @GetMapping("/contents")
  public JsonResponse<List<Map<String, Object>>> getContents(@RequestParam String keyword,
                                                             @RequestParam Integer pageNo,
                                                             @RequestParam Integer pageSize) throws IOException {
    List<Map<String, Object>> list = elasticsearchService.getContents(keyword, pageNo, pageSize);
    return new JsonResponse<>(list);
  }

  @GetMapping("/es-users")
  public JsonResponse<UserAuth> getUsers(@RequestParam String keyword) {
    UserAuth userAuth = elasticsearchService.getUser(keyword);
    return new JsonResponse<>(userAuth);
  }

  @PostMapping("/comment")
  public JsonResponse<String> addComment(Comment comment) {
    comment = iCommentService.insert(comment);
    elasticsearchService.addComment(comment);
    return JsonResponse.success();
  }

  @GetMapping("/es-comments")
  public JsonResponse<Comment> getComments(@RequestParam String keyword) {
    Comment comment = elasticsearchService.getComment(keyword);
    return new JsonResponse<>(comment);
  }


  @PutMapping("/users")
  public JsonResponse<String> updateUser(@RequestBody UserAuth userAuth) {
    iUserAuthService.update(userAuth);
    return JsonResponse.success();
  }

  @PostMapping("/users/login")
  public JsonResponse<String> login(@RequestBody UserAuth userAuth) {
    String token = iUserAuthService.authRest(userAuth);
    return new JsonResponse<>(token);
  }

  @GetMapping("/users")
  public JsonResponse<UserAuth> getUser() throws NoSuchAlgorithmException {
    Long userId = userSupport.getCurrentUserId();
    UserAuth userAuth = iUserAuthService.getByUserId(userId);
    return new JsonResponse<>(userAuth);
  }

}
