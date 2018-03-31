package com.store.BookStore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "userDetailService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/login"))
                .and().authorizeRequests()
                .antMatchers("/home").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/customers").hasRole("ADMIN")
                .antMatchers("/adminBooks").hasRole("ADMIN")
                .antMatchers("/saveBook").hasRole("ADMIN")
                .antMatchers("/editBook").hasRole("ADMIN")
                .and().formLogin().defaultSuccessUrl("/home")
                .loginPage("/login").and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logout.done").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/*.css");
        web.ignoring().antMatchers("/*.js");
    }
}
