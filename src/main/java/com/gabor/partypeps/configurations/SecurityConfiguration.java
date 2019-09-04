package com.gabor.partypeps.configurations;

import com.gabor.partypeps.security.entrypoints.DigestRestAuthenticationEntryPoint;
import com.gabor.partypeps.security.handlers.AuthenticationFailureHandler;
import com.gabor.partypeps.security.handlers.AuthenticationSuccessHandler;
import com.gabor.partypeps.services.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import javax.sql.DataSource;

/**
 * The Spring security Configuration Class
 */
@Configuration
@EnableWebSecurity
@ComponentScan({"com.gabor.partypeps.configurations", "com.gabor.partypeps.security"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    public DigestRestAuthenticationEntryPoint digestEntryPoint;

    @Autowired
    public UserSecurityService userDetailsService;

    @Autowired
    public DataSource dataSource;

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public DigestAuthenticationFilter digestAuthenticationFilter(){
        DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
        filter.setAuthenticationEntryPoint(digestEntryPoint);
        filter.setPasswordAlreadyEncoded(true);
        filter.setUserDetailsService(userDetailsService);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilter(digestAuthenticationFilter())
                .authorizeRequests()
                .antMatchers("/*/**").authenticated()
                .antMatchers("/check/**").permitAll()
                .antMatchers("/admin/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .httpBasic()
                .and()
                .formLogin()
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

}