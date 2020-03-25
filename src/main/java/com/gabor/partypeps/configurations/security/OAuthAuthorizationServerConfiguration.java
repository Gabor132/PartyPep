package com.gabor.partypeps.configurations.security;

import com.gabor.partypeps.common.env.EnvironmentHelper;
import com.gabor.partypeps.common.props.PropertiesHelper;
import com.gabor.partypeps.enums.PropertiesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@EnableAuthorizationServer
@Order(2)
@ComponentScan({"com.gabor.partypeps.configurations", "com.gabor.partypeps.security"})
public class OAuthAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    protected static Logger logger = Logger.getLogger(OAuthAuthorizationServerConfiguration.class.getName());

    @Autowired
    private EnvironmentHelper environmentHelper;

    protected Properties getSecurityProperties() {
        return PropertiesHelper.getSecurityProperties(true, environmentHelper.getEnvironment());
    }

    public OAuthAuthorizationServerConfiguration(){
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
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        // Maybe there's a way to use config from AuthorizationServerEndpointsConfigurer endpoints?
        source.registerCorsConfiguration("/oauth/token", config);
        CorsFilter filter = new CorsFilter(source);
        security.addTokenEndpointAuthenticationFilter(filter);
        super.configure(security);
    }

}
