package com.ntsuong.web.config;

import com.ntsuong.web.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 *
 * @author  Nguyen Thanh Suong
 * @version 1.0
 * @since   2022-06-01
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    DefaultAuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

    /*@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**",
                "/js/**", "/plugins/**").anyRequest();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz)-> authz
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "EDITOR", "AUTHOR")
                .antMatchers("/admin/authority/**", "/admin/api/authority/**").hasAnyRole("ADMIN")
                .antMatchers("/admin/user/**", "/admin/api/user/**").hasAnyRole("ADMIN")
                .antMatchers("/static/**").permitAll()
                .antMatchers("/*.js").permitAll()
                .antMatchers("/*.css").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()

                )
                .httpBasic(Customizer.withDefaults())
                .formLogin().loginPage("/login.do")
                .defaultSuccessUrl("/admin/index.do", true)
                .failureUrl("/login.do?error=true")
                .successHandler(defaultAuthenticationSuccessHandler)
                .and()
                .logout().logoutUrl("/logout.do")
                .logoutSuccessUrl("/login.do?logout=true")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .csrf().disable()
                .httpBasic().disable();
    }

    @Bean
    UserDetailsService customUserDetailsService() {
        return new CustomUserDetailsServiceImpl();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

}
