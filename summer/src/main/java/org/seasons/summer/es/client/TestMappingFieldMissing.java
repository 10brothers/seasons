package org.seasons.summer.es.client;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

/**
 * ES的mapping缺少字段时，RESTClient如何响应
 */
public class TestMappingFieldMissing {

    public static void main(String[] args) {

        RestClientBuilder builder = RestClient.builder(HttpHost.create("http://10.13.65.170:9205"));

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder.build());

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("risk_ctrl_case");
        searchRequest.types("doc");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("a", 1)));
        sourceBuilder.sort("b", SortOrder.DESC);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse search = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
