package com.sleepseek.infrastructure.security;

import com.sleepseek.user.UserFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserFacade userFacade;
    private final PasswordEncoder passwordEncoder;
    private final SecurityConstants securityConstants;

    public WebSecurityConfig(UserFacade userFacade, PasswordEncoder passwordEncoder, SecurityConstants securityConstants) {
        this.userFacade = userFacade;
        this.passwordEncoder = passwordEncoder;
        this.securityConstants = securityConstants;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/signup").permitAll()
                .antMatchers(HttpMethod.GET, "/stays/*").permitAll()
                .antMatchers(HttpMethod.GET, "/stays").permitAll()
                .antMatchers(HttpMethod.GET, "/accommodation").permitAll()
                .antMatchers(HttpMethod.GET, "/accommodation/*").permitAll()
                .antMatchers(HttpMethod.GET, "/accommodation-template").permitAll()
                .antMatchers(HttpMethod.GET, "/accommodation-template/*").permitAll()
                .antMatchers(HttpMethod.GET, "/review/*").permitAll()
                .antMatchers(HttpMethod.GET, "/review").permitAll()
                .antMatchers(HttpMethod.GET, "/stayCategories").permitAll()
                .antMatchers(HttpMethod.GET, "/stayProperties").permitAll()
                .antMatchers(HttpMethod.GET, "/accommodationProperties").permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), securityConstants))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), securityConstants))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userFacade).passwordEncoder(passwordEncoder);
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*");
            }
        };
    }
}
