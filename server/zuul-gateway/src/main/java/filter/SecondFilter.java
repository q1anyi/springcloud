package filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: caicy
 * @Date: 2019-07-13 15:16
 * @Description:
 */
@Component
public class SecondFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(SecondFilter.class);

    @Override
    public String filterType() {
        // 前置过滤器
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 优先级，数字越大，优先级越低
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        log.info("second过滤器!");
        return null;
    }
}
