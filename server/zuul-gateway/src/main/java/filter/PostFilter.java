package filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: caicy
 * @Date: 2019-07-13 15:18
 * @Description:
 */
@Component
public class PostFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(PostFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        log.info("进入post过滤器");
        System.out.println(ctx.getResponseBody());
        ctx.setResponseBody("post后置数据");
        int i = 1 / 0;
        return null;
    }
}
