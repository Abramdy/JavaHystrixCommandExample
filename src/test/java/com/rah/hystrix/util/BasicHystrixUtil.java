package com.rah.hystrix.util;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.After;
import org.junit.Before;

public abstract class BasicHystrixUtil {

    private HystrixRequestContext context;

    protected final HystrixRequestContext getHystrixContext() {
        return context;
    }

    @Before
    public void setUp() throws Exception {
        context = createContext();
    }

    @After
    public void tearDown() throws Exception {
        context.shutdown();
    }

    private HystrixRequestContext createContext() {
        HystrixRequestContext c = HystrixRequestContext.initializeContext();
        Hystrix.reset();
        return c;
    }


    protected void resetContext() {
        if (context != null) {
            context.shutdown();
        }
        context = createContext();
    }
}
