package com.rah.hystrix.controller;

import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixInvokableInfo;
import com.netflix.hystrix.HystrixRequestLog;
import com.rah.hystrix.Application;
import com.rah.hystrix.util.BasicHystrixUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
public class SampleControllerTest extends BasicHystrixUtil {

    @Inject private SampleController sampleController;

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testSystemHealthWithFallbackMethodCall() throws Exception {
        boolean isGoogleSearchAlive = sampleController.isAliveWithFallBack();
        HystrixInvokableInfo<?> command = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().iterator().next();
        assertEquals("HealthCheckCommand1", command.getCommandKey().name());
        assertEquals("HealthCheckGroup1", command.getCommandGroup().name());
        assertEquals("HealthCheckPool1", command.getThreadPoolKey().name());
        assertTrue(command.getExecutionEvents().contains(HystrixEventType.FALLBACK_SUCCESS));
        assertEquals(110, command.getProperties().executionTimeoutInMilliseconds().get().intValue());
        assertEquals(true, command.getProperties().executionIsolationThreadInterruptOnTimeout().get());
        assertFalse(isGoogleSearchAlive);
    }

    @Test
    public void testSystemHealth() throws Exception {
        boolean isGoogleSearchAlive = sampleController.isAlive();
        HystrixInvokableInfo<?> command = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().iterator().next();
        assertEquals("HealthCheckCommand2", command.getCommandKey().name());
        assertEquals("HealthCheckGroup2", command.getCommandGroup().name());
        assertEquals("HealthCheckPool2", command.getThreadPoolKey().name());
        assertTrue(command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
        assertEquals(3000, command.getProperties().executionTimeoutInMilliseconds().get().intValue());
        assertEquals(true, command.getProperties().executionIsolationThreadInterruptOnTimeout().get());
        assertTrue(isGoogleSearchAlive);
    }

    @Test
    public void testSystemHealthHystrixWithEnvPreoperty() throws Exception {
        boolean isGoogleSearchAlive = sampleController.isAliveHystrixWithEnvProperty();
        HystrixInvokableInfo<?> command = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().iterator().next();
        assertEquals("HealthCheckCommand", command.getCommandKey().name());
        assertTrue(command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
        assertEquals(5000, command.getProperties().executionTimeoutInMilliseconds().get().intValue());
        assertEquals(true, command.getProperties().executionIsolationThreadInterruptOnTimeout().get());
        assertTrue(isGoogleSearchAlive);
    }
}
