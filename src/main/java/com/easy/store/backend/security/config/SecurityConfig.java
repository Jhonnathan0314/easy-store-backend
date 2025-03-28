package com.easy.store.backend.security.config;

import com.easy.store.backend.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationError authenticationError,
                                                   AuthorizationError authorizationError
    ) throws Exception {
        return http
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest ->
                    authRequest
                        //Category
                        .requestMatchers(HttpMethod.POST, "/api/v1/category/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/category/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/category/**").hasAuthority("ADMIN")
                        //Subcategory
                        .requestMatchers(HttpMethod.POST, "/api/v1/subcategory/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/subcategory/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/subcategory/**").hasAuthority("ADMIN")
                        //Product
                        .requestMatchers(HttpMethod.POST, "/api/v1/product/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/product/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/product/**").hasAuthority("ADMIN")
                        //Payment type
                        .requestMatchers(HttpMethod.POST, "/api/v1/payment-type/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/payment-type/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/payment-type/**").hasAuthority("ADMIN")
                        //Purchase
                        .requestMatchers(HttpMethod.PUT, "/api/v1/purchase/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/purchase/**").hasAuthority("ADMIN")
                        //Account
                        .requestMatchers("/api/v1/account/**").hasAuthority("ADMIN")
                        //Account has user
                        .requestMatchers("/api/v1/account_has_user/**").hasAuthority("ADMIN")
                        //User
                        .requestMatchers("/api/v1/user/**").hasAuthority("ADMIN")
                        //Role
                        .requestMatchers("/api/v1/role/**").hasAuthority("ADMIN")
                        //S3
                        .requestMatchers(HttpMethod.POST, "/api/v1/s3/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/s3/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/s3/**").hasAuthority("ADMIN")
                        //Account
                        .requestMatchers("/api/v1/code/**").hasAuthority("ADMIN")
                        //Open
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/v1/email/forgot-password").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationError)
                        .accessDeniedHandler(authorizationError)
                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://127.0.0.1:4200",
                "http://localhost:4000", "http://127.0.0.1:4000",
                "http://localhost:5173", "http://127.0.0.1:5173",
                "https://easy-store-frontend-production.up.railway.app",
                "https://easy-store-frontend.railway.internal",
                "https://easy-store.jonatan-projects.com"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Accept", "Content-Type", "Authorization", "Create-By", "Update-By"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/auth/**", configuration);
        source.registerCorsConfiguration("/api/v1/**", configuration);
        return source;
    }

}
