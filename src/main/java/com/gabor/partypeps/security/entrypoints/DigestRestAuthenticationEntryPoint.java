package com.gabor.partypeps.security.entrypoints;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component("digestEntryPoint")
public class DigestRestAuthenticationEntryPoint extends DigestAuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        /**
         * Should send the type of authentification needed in the WWW-Authenticate header
         **/
        httpServletResponse.setHeader("WWW-Authenticate", "Digest realm=\"" + getRealmName() + "\"");
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.println("HTTP Status 401 - " + e.getMessage() + " - pare rau bo$$ :(");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("PartyPeps");
        setKey("acegi");
        super.afterPropertiesSet();
    }
}