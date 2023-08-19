package com.garyproject.mooc.config;

import org.springframework.data.elasticsearch.client.erhlc.AbstractElasticsearchConfiguration;

public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
  @Override
  public org.elasticsearch.client.RestHighLevelClient elasticsearchClient() {
    return null;
  }

  /*@Bean
  @Override
  public RestHighLevelClient elasticsearchClient() {
    final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
        .connectedTo("localhost:9200")
        .build();
    return RestClients.create(clientConfiguration).rest();
  }*/
}
