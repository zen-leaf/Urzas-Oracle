package com.mambocosmo.urzasoracle.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mambocosmo.urzasoracle.services.CustomUserDetailsService;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebMvc
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/css/**", "/js/**", "/img/**","/about").permitAll()
                .requestMatchers("/**", "/", "/auth/**", "/decks", "/collection", "/register").permitAll()
                .requestMatchers("/auth/register", "/cards").permitAll()
                .requestMatchers("/cards_details/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/admin/**", "/about").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.accessDeniedPage("//403"))
            .formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            )
            .userDetailsService(userDetailsService)
            .csrf(csrf -> csrf.disable()); 
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }
}

