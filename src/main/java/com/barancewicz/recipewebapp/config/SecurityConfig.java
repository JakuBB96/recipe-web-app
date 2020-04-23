//package com.barancewicz.recipewebapp.config;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/","/recipes","/recipe/*/show","/console/*","/h2-console/**").permitAll()
//                .and().authorizeRequests().antMatchers("/", "/index/**", "/recipe/**", "/recipes", "/ingredients").permitAll()
//                .anyRequest().authenticated()
//                .and().authorizeRequests().antMatchers("/login","logout").permitAll()
//                .and().authorizeRequests().antMatchers("/static/css/**","/js/**", "/images/**", "/**/favicon.ico").permitAll()
//                .and().formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll()
//                .and().logout()
//                .deleteCookies("remove")
//                .invalidateHttpSession(true)
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/logout-success")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//        ;
//    }
//}
