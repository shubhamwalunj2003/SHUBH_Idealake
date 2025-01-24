package com.timescape.authentication_service.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfigLocal {

    @Value("${ldap.urls}")
    private String ldapUrls;

    @Value("${ldap.base.dn}")
    private String ldapBaseDn;

    @Value("${ldap.username}")
    private String ldapSecurityPrincipal;

    @Value("${ldap.password}")
    private String ldapPrincipalPassword;

    @Value("${ldap.user.dn.pattern}")
    private String ldapUserDnPattern;

    @Value("${ldap.user.searchBase}")
    private String ldapUserSearchBase;

    @Value("${ldap.user.searchFilter}")
    private String ldapUserSearchFilter;

    @Value("${ldap.group.searchFilter}")
    private String ldapGroupSearchFilter;

    @Value("${ldap.group.searchbase}")
    private String ldapGroupSearchBase;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    public WebSecurityConfigLocal(JwtRequestFilter jwtRequestFilter) {
      this.jwtRequestFilter = jwtRequestFilter;
  }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        new AntPathRequestMatcher("*/api/auth/*"),
                        new AntPathRequestMatcher("/**/*.svg"),
                        new AntPathRequestMatcher("/**/*.png"),
                        new AntPathRequestMatcher("/**/*.gif"),
                        new AntPathRequestMatcher("/**/*.jpg"),
                        new AntPathRequestMatcher("/**/*.html"),
                        new AntPathRequestMatcher("/**/*.css"),
                        new AntPathRequestMatcher("/**/*.js"),
                        new AntPathRequestMatcher("/")
                ).permitAll()
                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver(AuthenticationManager authenticationManager) {
        return request -> authenticationManager;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .userDnPatterns(ldapUserDnPattern)
                .groupSearchBase(ldapGroupSearchBase)
                .contextSource()
                .url(ldapUrls + ldapBaseDn)
                .and()
                .passwordCompare()
                .passwordAttribute(ldapPrincipalPassword);
    }
}
