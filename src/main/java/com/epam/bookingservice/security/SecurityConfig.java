package com.epam.bookingservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.epam.bookingservice.security.ApplicationUserRole.ADMIN;
import static com.epam.bookingservice.security.ApplicationUserRole.ADMINTRAINEE;
import static com.epam.bookingservice.security.ApplicationUserRole.USER;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
//                .antMatchers("/bookingservice/**").hasRole(USER.name())
//                .antMatchers(HttpMethod.DELETE, "/bookingservice/**").hasAuthority(EVENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "/bookingservice/**").hasAuthority(EVENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/bookingservice/**").hasAuthority(EVENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET, "/bookingservice/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("senku")
                .password(passwordEncoder.encode("password"))
                .authorities(USER.getGrantedAuthorities())
//                .roles(USER.name())
                .build();
        UserDetails admin = User.builder()
                .username("linda")
                .password(passwordEncoder.encode("password"))
                .authorities(ADMIN.getGrantedAuthorities())
//                .roles(ADMIN.name())
                .build();
        UserDetails admintrainee = User.builder()
                .username("admintrainee")
                .password(passwordEncoder.encode("password"))
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
//                .roles(ADMINTRAINEE.name())
                .build();

        return new InMemoryUserDetailsManager(user, admin, admintrainee);
    }
}
