package com.kt.edu.common.filter;

import com.kt.edu.common.wrapper.RequestBodyTransformWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RequestTransFormFilter implements Filter {
    @Override
    public void init(
            FilterConfig filterConfig
    ) throws ServletException {
        log.info("RequestTransFormFilter init()");
    }

    @Override
    public void destroy() {

        log.info("RequestTransFormFilter destroy()");
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String requestURI = req.getRequestURI();

        if (((HttpServletRequest) request).getRequestURI().contains("swagger-ui")
                || ((HttpServletRequest) request).getRequestURI().contains("system")
                || ((HttpServletRequest) request).getRequestURI().contains("actuator")
                || ((HttpServletRequest) request).getRequestURI().contains("api-docs")) {
            chain.doFilter(request, response);
            return;
        }

        log.info("[{}] RequestTransFormFilter doFilter Start", requestURI);

        try {
            RequestBodyTransformWrapper requestWrapper = new RequestBodyTransformWrapper(req);
            chain.doFilter(requestWrapper, response);
        } finally {
            log.info("[{}] RequestTransFormFilter doFilter End", requestURI);
        }
    }
}