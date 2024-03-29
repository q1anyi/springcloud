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
 * @Date: Created In 2019-07-10 6:59
 * @Description:
 */
@Component
public class PreSendForwardFilter extends ZuulFilter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String filterType() {
        // 前置过滤器
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 优先级,数字越大,优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 是否执行该过滤器，true代表需要过滤
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String host = request.getRemoteHost();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        log.info("请求URI：{}，HTTP Method：{}，请求IP：{}", uri, method, host);
        return null;
    }
}
