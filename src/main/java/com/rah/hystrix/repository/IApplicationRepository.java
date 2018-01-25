package com.rah.hystrix.repository;

import feign.Headers;
import feign.RequestLine;
import feign.Response;

public interface IApplicationRepository {

    @RequestLine("GET /search?client=ms-opera-mobile")
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept: */*"})
    Response healthCheck();
}
