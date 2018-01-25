package com.rah.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.rah.hystrix.repository.IApplicationRepository;
import feign.Response;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class SampleService {

    @Inject
    IApplicationRepository applicationRepository;

    @HystrixCommand(fallbackMethod = "getHealthCheckFallback", groupKey = "HealthCheckGroup1", commandKey = "HealthCheckCommand1", threadPoolKey = "HealthCheckPool1", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "110")})
    public Boolean healthCheck1() {
        System.out.println("Entering in health check service.");
        return getStatus();
    }

    @HystrixCommand(fallbackMethod = "getHealthCheckFallback", groupKey = "HealthCheckGroup2", commandKey = "HealthCheckCommand2", threadPoolKey = "HealthCheckPool2", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")})
    public Boolean healthCheck2() {
        System.out.println("Entering in health check service.");
        return getStatus();
    }

    private Boolean getStatus() {
        try {
            Response response = applicationRepository.healthCheck();
            if (response.status() == 200) {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public Boolean getHealthCheckFallback() {
        System.out.println("In fallback method");
        return false;
    }

}
