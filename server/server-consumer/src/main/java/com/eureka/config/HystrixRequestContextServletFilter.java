package com.eureka.config;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: caicy
 * @Date: 2019-07-03 14:29
 * @Description: Hystrix中开启缓存开启以后会有一个初始化上下文报错所以在使用@CacheResult注解的时候需要用过滤器过滤一下
 */
@Component
@WebFilter(filterName = "hystrixRequestContextServletFilter", urlPatterns = "/*", asyncSupported = true)
public class HystrixRequestContextServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        filterChain.doFilter(servletRequest, servletResponse);
        context.close();
    }

    @Override
    public void destroy() {

    }
}
