package com.huotu.loanmarket.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author allan
 * @date 19/10/2017
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String LOGIN_PAGE = "/login";
    public static final String LOGIN_SUCCESS_URL = "/index";
    public static final String LOGIN_ERROR_URL = "/loginFailed";
    public static final String LOGOUT_SUCCESS_URL = "/";

    private static String[] STATIC_RESOURCE_PATH = {
            "/resource/**"
    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(STATIC_RESOURCE_PATH);
    }


    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("administrator")
                .password("hot!@#$")
                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage(LOGIN_PAGE)
                .defaultSuccessUrl(LOGIN_SUCCESS_URL)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }
}
