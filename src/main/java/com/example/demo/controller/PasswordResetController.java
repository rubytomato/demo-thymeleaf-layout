package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("password-reset")
@Slf4j
public class PasswordResetController {

  @GetMapping
  public String init(HttpServletRequest req, Model model) {
    log.info("#init");
    return index(req, model);
  }

  @PostMapping
  public String index(HttpServletRequest req, Model model) {
    log.info("#index");
    return "password-reset/index";
  }

}
