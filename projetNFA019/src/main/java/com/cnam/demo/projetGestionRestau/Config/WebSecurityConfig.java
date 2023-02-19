package com.cnam.demo.projetGestionRestau.Config;

import com.cnam.demo.projetGestionRestau.Service.CustomUserDetailsService;
import com.cnam.demo.projetGestionRestau.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/accueil").authenticated()
                .antMatchers("/**/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .usernameParameter("email")
                .defaultSuccessUrl("/accueil")
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/accueil").hasRole("ADMIN")
//                .antMatchers("/user").hasRole("USER")
//                //.antMatchers("/**/**").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .usernameParameter("email")
//                .defaultSuccessUrl("/accueil")
//                .permitAll()
//                .and()
//                .logout().logoutSuccessUrl("/").permitAll();
//    }

    //.loginPage("/login")
    //.loginProcessingUrl("/login")
}