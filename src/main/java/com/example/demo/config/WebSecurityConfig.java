package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  // アカウント登録時のパスワードエンコードで利用するためDI管理する。
  //@Bean
  //PasswordEncoder passwordEncoder() {
  //  return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  //}

  @Override
  public void configure(WebSecurity web) throws Exception {
    // @formatter:off
    web
        .debug(false)
        .ignoring()
          .antMatchers("/adminlte/**", "/bower_components/**", "/midpoint/**", "/app/**")
    ;
    // @formatter:on
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
        .authorizeRequests()
          .mvcMatchers("/")
            .permitAll()
          .anyRequest()
            .authenticated()
        .and()
          .formLogin()
            .defaultSuccessUrl("/")
        .and()
          .logout()
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutSuccessUrl("/")
    ;
    // @formatter:on
  }
}
