package com.example.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class SecureInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (handler instanceof HandlerMethod) {
      log.info("#preHandle : {}", request.getRequestURI());
      return true;
    }
    log.error("#preHandle : {}", request.getRequestURI());
    response.sendRedirect("/error");
    return false;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    log.info("#postHandle : {}", request.getRequestURI());
    if (handler instanceof HandlerMethod) {
      if (modelAndView != null) {
        modelAndView.addObject("userName", "administrator");
      } else {
        log.warn("modelAndView is null");
      }
    }
  }

}
