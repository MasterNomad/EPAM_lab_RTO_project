package com.epam.lab.rto.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
        authentication
                .jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT `email`, `password` , true " +
                        "FROM `users` WHERE `email` = ?")
                .authoritiesByUsernameQuery("SELECT `email`, `role` " +
                        "FROM `users` WHERE `email` = ?")
                .passwordEncoder(new MyPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers("/login/**").anonymous()
                .and()
                .exceptionHandling().accessDeniedPage("/home")
                .and()
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/img/**", "/error", "/change-lang").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginProcessingUrl("/loginPost")
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                //.failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout().permitAll();
    }
}
