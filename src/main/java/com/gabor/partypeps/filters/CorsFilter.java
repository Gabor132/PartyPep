package com.gabor.partypeps.filters;

import com.gabor.partypeps.common.props.PropertiesHelper;
import com.gabor.partypeps.enums.ProfilesEnum;
import com.gabor.partypeps.enums.PropertiesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
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


    @Bean("profileEnum")
    @Profile("DEV")
    public ProfilesEnum getENV(){
        return ProfilesEnum.DEV;
    }

    @Bean("profileEnum")
    @Profile("IT")
    public ProfilesEnum getIT(){
        return ProfilesEnum.IT;
    }

    @Bean("profileEnum")
    @Profile("PROD | default")
    public ProfilesEnum getProd(){
        return ProfilesEnum.PROD;
    }

    @Autowired
    private ProfilesEnum profilesEnum;

    public String getFrontEndUrl() {
        return PropertiesHelper.getURLProperties(true, profilesEnum).getProperty(PropertiesEnum.FRONTEND_URL.getValue());
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
