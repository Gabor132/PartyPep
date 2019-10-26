package com.gabor.partypeps.configurations;

import com.gabor.partypeps.common.EnvironmentHelper;
import com.gabor.partypeps.common.PropertiesHelper;
import com.gabor.partypeps.enums.PropertiesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Properties;
import java.util.logging.Logger;

public abstract class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    protected static Logger logger = Logger.getLogger(AuthorizationServerConfiguration.class.getName());

    @Autowired
    private EnvironmentHelper environmentHelper;

    protected Properties getSecurityProperties() {
        return PropertiesHelper.getSecurityProperties(true, environmentHelper.getEnvironment());
    }

    public AuthorizationServerConfiguration(){
        super();
    }

    protected String getSigningKey(Properties securityProperties){
        return PropertiesHelper.getProperty(securityProperties, PropertiesEnum.SECURITY_SIGNING_KEY);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        final JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        String signingKey = getSigningKey(getSecurityProperties());
        logger.info("Signing key is: " + signingKey);
        accessTokenConverter.setSigningKey(signingKey);
        return accessTokenConverter;
    }

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public UserDetailsService userDetailsService;

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices(){
        final DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        return tokenServices;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .tokenStore(tokenStore())
            .authenticationManager(authenticationManager)
            .accessTokenConverter(accessTokenConverter())
            .userDetailsService(userDetailsService)
            .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(PropertiesHelper.getProperty(getSecurityProperties(), PropertiesEnum.SECURITY_CLIENT_ID))

                .secret(PropertiesHelper.getProperty(getSecurityProperties(), PropertiesEnum.SECURITY_CLIENT_SECRET))

                .authorizedGrantTypes("password", "refresh_token")
                .refreshTokenValiditySeconds(3600 * 24)
                .scopes("partypeps", "read", "write", "trust")
                .autoApprove("partypeps")
                .accessTokenValiditySeconds(3600);
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) throws Exception{
        security
            .checkTokenAccess("permitAll()")
            .tokenKeyAccess("permitAll()")
            .allowFormAuthenticationForClients()
            .realm("PartyPeps");
        super.configure(security);
    }

}
