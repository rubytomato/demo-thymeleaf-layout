package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("pageHeader", "Index");
    model.addAttribute("isSubMenuOpen", "false");
    model.addAttribute("userName", "administrator");
    return "index";
  }
}
