package com.jimas.redis.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author liuqj
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate customRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .requestFactory(() -> {
                    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
                    HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
                    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
                    connectionManager.setMaxTotal(200);
                    connectionManager.setDefaultMaxPerRoute(100);
                    httpClientBuilder.setConnectionManager(connectionManager);
                    RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(10000).build();
                    httpClientBuilder.setDefaultRequestConfig(requestConfig);
                    clientHttpRequestFactory.setHttpClient(httpClientBuilder.build());
                    return clientHttpRequestFactory;
                })
                .build();
    }
}
