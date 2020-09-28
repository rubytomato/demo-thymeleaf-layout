package com.example.demo.controller;

import com.example.demo.ui.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
  public String banana(@RequestParam(value = "allDataNum", defaultValue = "100") String allDataNum,
                       @RequestParam(value = "pageNo", defaultValue = "1") String pageNo, Model model) {
    model.addAttribute("pageHeader", "Banana");

    Page page = new Page(Integer.valueOf(allDataNum), 10, Integer.valueOf(pageNo));
    model.addAttribute("page", page);
    String info = String.format("first:%d, last:%d, all:%d", page.getFirstPageNo(), page.getLastPageNo(), page.getAllPageNum());
    model.addAttribute("pageInfo", info);
    return "sub-menu/banana";
  }
  @GetMapping("cherry")
  public String cherry(Model model) {
    model.addAttribute("pageHeader", "Cherry");
    return "sub-menu/cherry";
  }
}
