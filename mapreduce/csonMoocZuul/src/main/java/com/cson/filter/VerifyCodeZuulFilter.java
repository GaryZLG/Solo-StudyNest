package com.cson.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 本代码来自 Cspiration，由 @Cspiration 提供
 * - 讲师：Edward Shi
 * - 官方网站：https://cspiration.com
 * - 版权所有，禁止转发和分享
 */
@Component
public class VerifyCodeZuulFilter extends ZuulFilter {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    //是否过滤
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String serialNumber = request.getParameter("serialNumber");
        return !StringUtil.isNullOrEmpty(serialNumber);
    }

    //过滤方法
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String serialNumber = request.getParameter("serialNumber");
        String verifyCode = request.getParameter("verifyCode");
        String redisVerifyCode = redisTemplate.opsForValue().get(serialNumber);
        if (!verifyCode.equals(redisVerifyCode)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            requestContext.getResponse().setContentType("application/json");
            String msg = "{\n" +
                    "   \"success\":false,\n" +
                    "   \"msg\": \"Code is incorrect\"\n" +
                    "}";
            requestContext.setResponseBody(msg);
        }
        return null;
    }
}
