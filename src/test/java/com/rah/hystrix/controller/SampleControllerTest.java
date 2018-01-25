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
    public void testSystemHealth1() throws Exception {
        boolean isGoogleSearchAlive = sampleController.isAlive1();
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
    public void testSystemHealth2() throws Exception {
        boolean isGoogleSearchAlive = sampleController.isAlive2();
        HystrixInvokableInfo<?> command = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().iterator().next();
        assertEquals("HealthCheckCommand2", command.getCommandKey().name());
        assertEquals("HealthCheckGroup2", command.getCommandGroup().name());
        assertEquals("HealthCheckPool2", command.getThreadPoolKey().name());
        assertTrue(command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
        assertEquals(3000, command.getProperties().executionTimeoutInMilliseconds().get().intValue());
        assertEquals(true, command.getProperties().executionIsolationThreadInterruptOnTimeout().get());
        assertTrue(isGoogleSearchAlive);
    }


    /*@Test
    public void testSystemHealth() throws Exception {
        boolean isGoogleSearchAlive = sampleController.isAlive1();
        HystrixInvokableInfo<?> command = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().iterator().next();
        assertEquals("HealthCheckCommand", command.getCommandKey().name());
        assertEquals("HealthCheckGroup", command.getCommandGroup().name());
        assertEquals("HealthCheckPool", command.getThreadPoolKey().name());
        assertTrue(command.getExecutionEvents().contains(HystrixEventType.FALLBACK_SUCCESS));
        // assert properties
        assertEquals(110, command.getProperties().executionTimeoutInMilliseconds().get().intValue());
        assertEquals(true, command.getProperties().executionIsolationThreadInterruptOnTimeout().get());

        *//*Field field = command.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("threadPool");
        field.setAccessible(true);
        HystrixThreadPool threadPool = (HystrixThreadPool) field.get(command);

        Field field2 = HystrixThreadPool.HystrixThreadPoolDefault.class.getDeclaredField("properties");
        field2.setAccessible(true);
        HystrixThreadPoolProperties properties = (HystrixThreadPoolProperties) field2.get(threadPool);

        assertEquals(30, (int) properties.coreSize().get());
        assertEquals(101, (int) properties.maxQueueSize().get());
        assertEquals(2, (int) properties.keepAliveTimeMinutes().get());
        assertEquals(15, (int) properties.queueSizeRejectionThreshold().get());
        assertEquals(1440, (int) properties.metricsRollingStatisticalWindowInMilliseconds().get());
        assertEquals(12, (int) properties.metricsRollingStatisticalWindowBuckets().get());*//*

        assertFalse(isGoogleSearchAlive);
    }*/
}
