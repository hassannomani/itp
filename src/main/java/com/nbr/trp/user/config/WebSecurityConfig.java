package com.nbr.trp.user.config;

import com.nbr.trp.user.filter.AuthEntryPointJwt;
import com.nbr.trp.user.filter.AuthTokenFilter;
import com.nbr.trp.user.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http.cors().and().csrf().disable().exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/error").permitAll()
                    //.authorizeRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/test/**").permitAll()
                .requestMatchers("/api/action/message/**").permitAll()
                .requestMatchers("/api/v1/common/file").permitAll()
                .requestMatchers("/api/v1/common/photo").permitAll()
                .requestMatchers("/api/certificate/check/**").permitAll()
                .requestMatchers("/api/certificate/get/**").permitAll()
                .requestMatchers("/api/v1/etin/tin/**").permitAll()
                .requestMatchers("/api/otp/**").permitAll()
                .requestMatchers("/api/v1/common/district").permitAll()
                .requestMatchers("/api/v1/common/division").permitAll()
                .requestMatchers("/api/v1/common/thana").permitAll()
                .requestMatchers("/api/common/bank").permitAll()
                .requestMatchers("/api/common/bankbranches/**").permitAll()
                .requestMatchers("/api/common/bankdist").permitAll()
                .requestMatchers("/api/v1/common/citycorporation").permitAll()
                .requestMatchers("/api/address/add").permitAll()
                .requestMatchers("/api/bank/add").permitAll()
                .requestMatchers("/api/v1/itp/add").permitAll()
                .requestMatchers("/api/agent/allfront").permitAll()
                .requestMatchers("/api/v1/users/roles").permitAll()
                .requestMatchers("/api/v1/users/mypassword/**").permitAll()
                .requestMatchers("/api/v1/users/register").permitAll()
                .requestMatchers("/api/v1/common/file/**").permitAll()
                .requestMatchers("/api/v1/common/photo/**").permitAll()
                .requestMatchers("/api/v1/year/find-latest").permitAll()
                .requestMatchers("/api/trpereturn/psr/**").permitAll()
                .requestMatchers("/api/v1/logs/**").permitAll()
                .requestMatchers("/api/v1/testcontroller/logs").permitAll()

                .anyRequest()
                .authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration authenticationConfiguration)
            throws Exception {

        return authenticationConfiguration
                .getAuthenticationManager();
    }
}
