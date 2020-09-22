package com.example.demo.controller;

import com.example.demo.config.CustomEnvProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class IndexController {

  @Autowired
  private CustomEnvProperties customEnv;

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("pageHeader", "Index");
    log.info("value1 : {}", customEnv.getValue1());
    return "index";
  }
}
