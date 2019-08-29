package com.gabor.partypeps.configurations.Security;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler { }
