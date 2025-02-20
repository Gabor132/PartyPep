package com.gabor.partypeps.filters;

import com.gabor.partypeps.common.env.EnvironmentHelper;
import com.gabor.partypeps.common.props.PropertiesHelper;
import com.gabor.partypeps.enums.PropertiesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Autowired
    private EnvironmentHelper environmentHelper;


    public String getFrontEndUrl() {
        return PropertiesHelper.getURLProperties(true, environmentHelper.getEnvironment()).getProperty(PropertiesEnum.FRONTEND_URL.getValue());
    }

    public String getBackEndUrl() {
        return PropertiesHelper.getURLProperties(true, environmentHelper.getEnvironment()).getProperty(PropertiesEnum.BACKEND_URL.getValue());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        //
        // Setting headers on the response so that applications from a different origin (Hostname, Domain, Port) can utilize the api
        //
        response.setHeader("Access-Control-Allow-Origin", getFrontEndUrl());
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, WWW-Authenticate, Authorization, Origin, Content-Type, Version");
        response.setHeader("Access-Control-Expose-Headers", "X-Requested-With, WWW-Authenticate, Authorization, Origin, Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        if(! request.getMethod().equals("OPTIONS")){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    public void destroy() {}
}
