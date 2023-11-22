package com.experis.course.springlamiapizzeriacrud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
        //filtriamo le rotte per users e admin
        //users accede solo alla lista e ai dettagli delle pizze
        http
                .authorizeHttpRequests()
                .requestMatchers("/ingredienti/**").hasAuthority("ADMIN")
                .requestMatchers("/offerte/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/pizze/**").hasAuthority("ADMIN")
                .requestMatchers("/pizze", "/pizze/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout();
        //disabilitiamo il csrf per poter fare richieste al di fuori della nostra applicazione
        http.csrf().disable();
        //la request viene impacchettata e mandata avanti
        return http.build();
    }
}
