package com.onlineordersystem.config;

import com.onlineordersystem.security.AuthEntryPointJwt;
import com.onlineordersystem.security.AuthTokenFilter;
import com.onlineordersystem.security.Authority;
import com.onlineordersystem.service.PrincipleService;
import java.util.Optional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Setter
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String SELLER_PATHS = "/seller/**";
    public static final String USER_PATHS = "/user/**";
    @Value("${server.servlet.context-path}")
    private String apiRootPath;
    private PrincipleService principleService;
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    public SecurityConfig(AuthEntryPointJwt unauthorizedHandler, PrincipleService principleService) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.principleService = principleService;
    }

    @Bean
    AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getName);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, apiRootPath + "/swagger-ui.html").permitAll()
            .antMatchers(HttpMethod.POST, apiRootPath + "/*/register").permitAll()
            .antMatchers(HttpMethod.GET, apiRootPath + SELLER_PATHS).hasAuthority(Authority.SELLER.name())
            .antMatchers(HttpMethod.POST, apiRootPath + SELLER_PATHS).hasAuthority(Authority.SELLER.name())
            .antMatchers(HttpMethod.PUT, apiRootPath + SELLER_PATHS).hasAuthority(Authority.SELLER.name())
            .antMatchers(HttpMethod.GET, apiRootPath + USER_PATHS).hasAuthority(Authority.USER.name())
            .antMatchers(HttpMethod.POST, apiRootPath + USER_PATHS).hasAuthority(Authority.USER.name())
            .antMatchers(HttpMethod.PUT, apiRootPath + USER_PATHS).hasAuthority(Authority.USER.name());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(principleService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

}
