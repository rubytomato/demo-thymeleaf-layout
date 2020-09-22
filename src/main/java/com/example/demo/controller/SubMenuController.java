package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sub-menu")
@Slf4j
public class SubMenuController {
  @GetMapping("apple")
  public String apple(Model model) {
    model.addAttribute("pageHeader", "Apple");
    return "sub-menu/apple";
  }
  @GetMapping("banana")
  public String banana(Model model) {
    model.addAttribute("pageHeader", "Banana");
    return "sub-menu/banana";
  }
  @GetMapping("cherry")
  public String cherry(Model model) {
    model.addAttribute("pageHeader", "Cherry");
    return "sub-menu/cherry";
  }
}
