package com.yong.spring.jpa.jms.commons;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 记录请求的到达、应答时间、接口耗时，并打印请求URL+入参
 * @author created by li.yong on 2018年10月18日 下午2:11:24
 */
@WebFilter(filterName = "requestInfoFilter", urlPatterns = "/*")
public class RequestInfoFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(RequestInfoFilter.class);
    private static final String UNKNOWN = "unknown";
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("RequestInfoFilter init success");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (isIgnore(req)) {
            chain.doFilter(request, response);
            return;
        }
        long start = System.currentTimeMillis();
        
        StringBuilder sb = new StringBuilder();
        sb.append(req.getRequestURL());
        sb.append(" [").append(req.getMethod()).append("]");
        sb.append(" QueryString:").append(req.getQueryString() == null ? "" : req.getQueryString());
        String ip = getIpAddr(req);
        boolean success = false;
        try {
            chain.doFilter(request, response);
            success = true;
        } finally {
            long end = System.currentTimeMillis();
            if (success) {
                logger.info("Success {} IP: {} times: {}", sb.toString(), ip, end - start);
            } else {
                logger.error("Failed {} IP: {} times: {}", sb.toString(), ip, end - start);
            }
        }
    }

    @Override
    public void destroy() {
    }
    
    private boolean isIgnore(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri == null) {
            return false;
        }
        if (uri.contains("/static") || uri.contains("/js") || uri.contains("/img") || uri.contains("/css") || uri.contains(".ico")) {
            return true;
        }
        return false;
    }
    
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (null != ip && !"".equals(ip.trim()) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (null != ip && !"".equals(ip.trim()) && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 请求被多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (ip != null && !"".equals(ip.trim()) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("remote-host");
        if (ip != null && !"".equals(ip.trim()) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}

    