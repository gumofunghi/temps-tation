package com.gumo.temps.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception{
        //authentication manager
        auth.inMemoryAuthentication()
            .withUser("user1")
            .password(passwordEncoder().encode("user1Pass"))
            .roles("USER")
            .and()
            .withUser("user2")
            .password(passwordEncoder().encode("user2Pass"))
            .roles("USER")
            .and()
            .withUser("admin")
            .password(passwordEncoder().encode("adminPass"))
            .roles("ADMIN");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception{
        //http builder config for authorise requests
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/").hasAnyRole()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login_attempt")
                .defaultSuccessUrl("/homepage.html", true)
                .failureUrl("/login.html?error=true")
            .and()
                .logout()
                .logoutUrl("/logout_attempt")
                .deleteCookies("JSESSIONID");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
