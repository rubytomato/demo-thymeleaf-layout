package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("master")
public class MasterController {

  @GetMapping
  public String init(Model model) {
    return index(model);
  }

  @PostMapping
  public String index(Model model) {
    model.addAttribute("masterList", getMasterList());
    return "master/index";
  }

  @GetMapping("/select/{value}")
  public String view(@PathVariable("value") String value, Model model) {
    model.addAttribute("masterList", getMasterList());

    Header id = new Header("id", "number");
    Header name = new Header("name", "string");
    Header from = new Header("from", "date");
    List<Header> headers = List.of(id, name, from);

    Map<String, Object> row1 = new HashMap<>();
    row1.put("id", 100L);
    row1.put("name", "aaa");
    row1.put("from", LocalDateTime.now());

    Map<String, Object> row2 = new HashMap<>();
    row2.put("id", 100L);
    row2.put("name", "aaa");
    row2.put("from", LocalDateTime.now());

    List<Map<String, Object>> dataset = new ArrayList<>();
    dataset.add(row1);
    dataset.add(row2);

    Body body = new Body();
    body.name = value;
    body.headers = headers;
    body.dataset = dataset;

    model.addAttribute("body", body);

    model.addAttribute("masterName", "master." + value + ".name");
    model.addAttribute("value", value);
    return "master/view";
  }

  private Map<String, String> getMasterList() {
    Map<String, String> masterList = new HashMap<>();
    masterList.put("company", "master.company.name");
    masterList.put("employee", "master.employee.name");
    masterList.put("grade", "master.grade.name");
    return masterList;
  }

  private static class Body {
    private String name;
    private List<Header> headers;
    private List<Map<String, Object>> dataset;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public List<Header> getHeaders() {
      return headers;
    }

    public void setHeaders(List<Header> headers) {
      this.headers = headers;
    }

    public List<Map<String, Object>> getDataset() {
      return dataset;
    }

    public void setDataset(List<Map<String, Object>> dataset) {
      this.dataset = dataset;
    }
  }

  private static class Header {
    private String name;
    private String type;
    public Header(String name, String type) {
      this.name = name;
      this.type = type;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }
  }

}

