package com.onlineordersystem.config;

import com.onlineordersystem.security.roles.SellerRole;
import com.onlineordersystem.security.roles.UserRole;
import java.util.Optional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Setter
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String SELLER_PATHS = "/seller/**";
    public static final String USER_PATHS = "/user/**";
    @Value("${server.servlet.context-path}")
    private String apiRootPath;
    private SellerRole sellerRole;
    private UserRole userRole;

    @Autowired
    public SecurityConfig(SellerRole sellerRole, UserRole userRole) {
        this.sellerRole = sellerRole;
        this.userRole = userRole;
    }

    @Bean
    AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getName);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String userAuthority = userRole.getAuthority();
        String sellerAuthority = sellerRole.getAuthority();
        http.cors()
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, apiRootPath + "/swagger-ui.html").permitAll()
            .antMatchers(HttpMethod.POST, apiRootPath + "/seller/register").permitAll()
            .antMatchers(HttpMethod.POST, apiRootPath + "/user/register").permitAll()
            .antMatchers(HttpMethod.GET, apiRootPath + SELLER_PATHS).hasRole(sellerAuthority)
            .antMatchers(HttpMethod.POST, apiRootPath + SELLER_PATHS).hasRole(sellerAuthority)
            .antMatchers(HttpMethod.PUT, apiRootPath + SELLER_PATHS).hasRole(sellerAuthority)
            .antMatchers(HttpMethod.GET, apiRootPath + USER_PATHS).hasRole(userAuthority)
            .antMatchers(HttpMethod.POST, apiRootPath + USER_PATHS).hasRole(userAuthority)
            .antMatchers(HttpMethod.PUT, apiRootPath + USER_PATHS).hasRole(userAuthority);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
