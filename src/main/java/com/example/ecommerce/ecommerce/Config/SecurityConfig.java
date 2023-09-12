package com.example.ecommerce.ecommerce.Config;

import com.example.ecommerce.ecommerce.Handler.YourCustomAuthenticationSuccessHandler;
import com.example.ecommerce.ecommerce.Implementation.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private YourCustomAuthenticationSuccessHandler successHandler;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService getDetailsService() {
    return new CustomUserDetailsService();
}
@Bean
public DaoAuthenticationProvider getDaoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
}
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/home","/signin", "/register", "/About","/saveUser","/home",
                        "/resources","/css","/images","/javascript","fragments").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN").
                antMatchers("/users/**").hasRole("USERS")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/userLogin")
                .successHandler(successHandler).permitAll();
        return http.build();


    }

}
