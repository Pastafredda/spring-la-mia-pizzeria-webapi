package com.experis.course.springlamiapizzeriacrud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    //bean serve per collegare le classi
    //configurazione su come avere un UserDetailService
    @Bean
    public DatabaseUserDetailsService userDetailsService() {
        //richiamiamo il costruttore
        return new DatabaseUserDetailsService();
    }

    //Come avere un PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //configurazione AuthenticationProvider
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        //creo un DaoAuthenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //gli assegno UserDetailService
        provider.setUserDetailsService(userDetailsService());
        //gli assegno passwordEncoder
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    //securityFilterChain ci serve per capire chi può visualizzare e chi no
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //in queste 5 righe di codice diciamo
        http
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .and().logout();
        //la request viene impacchettata e mandata avanti
        return http.build();
    }
}
