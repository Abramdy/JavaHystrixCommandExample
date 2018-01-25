package com.rah.hystrix.controller;

import com.rah.hystrix.service.SampleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

@Controller
public class SampleController {

    @Inject private SampleService feignClientService;

    @RequestMapping(value = "/healthCheck1", method = RequestMethod.GET, produces = "application/json")
    public boolean isAliveWithFallBack() {
        return feignClientService.healthCheck1();
    }

    @RequestMapping(value = "/healthCheck2", method = RequestMethod.GET, produces = "application/json")
    public boolean isAlive() {
        return feignClientService.healthCheck2();
    }

    @RequestMapping(value = "/healthCheck3", method = RequestMethod.GET, produces = "application/json")
    public boolean isAliveHystrixWithEnvProperty() {
        return feignClientService.healthCheck3();
    }
}
