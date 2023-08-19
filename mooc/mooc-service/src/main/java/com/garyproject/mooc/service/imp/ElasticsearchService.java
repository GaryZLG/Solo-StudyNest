package com.garyproject.mooc.service.imp;

import com.garyproject.mooc.dao.ESCommentDao;
import com.garyproject.mooc.dao.ESUserAuthDao;
import com.garyproject.mooc.entity.Comment;
import com.garyproject.mooc.entity.UserAuth;

import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
//import co.elastic.clients.elasticsearch.core.SearchSourceBuilder;

@Service
public class ElasticsearchService {
  @Autowired
  private ESUserAuthDao esUserAuthDao;

  @Autowired
  private ESCommentDao esCommentDao;

  /*@Autowired
  private RestHighLevelClient restHighLevelClient;*/

  public void addUser(UserAuth userAuth) {
    esUserAuthDao.save(userAuth);
  }

  public UserAuth getUser(String keyword) {
    return esUserAuthDao.findByUsernameLike(keyword);
  }

  public void addComment(Comment comment) {
    esCommentDao.save(comment);
  }

  public Comment getComment(String keyword) {
    return esCommentDao.findByContentContaining(keyword);
  }

  public List<Map<String, Object>> getContents(String keyword,
                                               Integer pageNo,
                                               Integer pageSize) throws IOException {
    String[] indices = {"comments", "users"};
    SearchRequest searchRequest = new SearchRequest(indices);
    SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    //分页
    sourceBuilder.from(pageNo - 1);
    sourceBuilder.size(pageSize);
    MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "content", "username");
    sourceBuilder.query(matchQueryBuilder);
    searchRequest.source(sourceBuilder);
    sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
    //高亮
    String[] array = {"content", "username"};
    HighlightBuilder highlightBuilder = new HighlightBuilder();
    for (String key : array) {
      highlightBuilder.fields().add(new HighlightBuilder.Field(key));
    }
    highlightBuilder.preTags("<span style=\"color:red\">");
    highlightBuilder.postTags("</span>");
    sourceBuilder.highlighter(highlightBuilder);
    //搜素
    SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
    List<Map<String, Object>> res = new ArrayList<>();
    for (SearchHit hit : searchResponse.getHits()) {
      Map<String, HighlightField> highlightFields = hit.getHighlightFields();
      Map<String, Object> sourceMap = hit.getSourceAsMap();
      for (String key : array) {
        HighlightField field = highlightFields.get(key);
        if (field != null) {
          Text[] fragments = field.fragments();
          String str = Arrays.toString(fragments);
          str = str.substring(1, str.length() - 1);
          sourceMap.put(key, str);
        }
      }
      res.add(sourceMap);
    }
    return res;
  }
}
