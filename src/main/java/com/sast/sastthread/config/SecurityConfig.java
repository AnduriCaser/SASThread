package com.sast.sastthread.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sast.sastthread.exception.CustomAccessDeniedHandler;
import com.sast.sastthread.filter.CustomAuthenticationProvider;
import com.sast.sastthread.filter.JwtAuthenticationFilter;
import com.sast.sastthread.filter.JwtAuthenticationInternalFilter;
import com.sast.sastthread.jwt.JwtConfig;
import com.sast.sastthread.jwt.JwtService;
import com.sast.sastthread.repository.UserRepository;
import com.sast.sastthread.security.CustomUserDetailService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends JwtConfig {

    private final CustomUserDetailService customUserDetailService;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final JwtConfig jwtConfig;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void userAuthenticationGlobalConfig(AuthenticationManagerBuilder authenticationManagerBuilder){
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        AuthenticationManagerBuilder managerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        managerBuilder.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = managerBuilder.build();

        httpSecurity
            .csrf()
            .disable()
            .formLogin().disable()
            .authorizeHttpRequests()
            .requestMatchers("/api/v1/auth/**").permitAll()
            .requestMatchers("/api/v1/users/**").hasAnyAuthority("USER", "ADMIN")
            .requestMatchers("/api/v1/products/**").hasAnyAuthority("USER", "ADMIN")
            .requestMatchers("/api/v1/search/**").hasAnyAuthority("USER", "ADMIN")
            .requestMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
            .anyRequest().authenticated()
            .and()
            .authenticationManager(authenticationManager)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(
                    (((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
            )
            .accessDeniedHandler(new CustomAccessDeniedHandler())
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(
                    jwtService, objectMapper, jwtConfig, authenticationManager, customUserDetailService),
                    UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtAuthenticationInternalFilter(jwtService, objectMapper, jwtConfig),
                        UsernamePasswordAuthenticationFilter.class);


     return httpSecurity.build();
    }

}

