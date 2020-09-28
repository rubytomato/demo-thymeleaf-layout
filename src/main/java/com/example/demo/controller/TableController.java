package com.example.demo.controller;

import com.example.demo.ui.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("table")
@Slf4j
public class TableController {

  @GetMapping
  public String init(HttpServletRequest req, Model model) {
    log.info("#init");
    return index(req, model);
  }

  @PostMapping
  public String index(HttpServletRequest req, Model model) {
    log.info("#index");
    req.getParameterMap().forEach((k,v) -> {
      log.info("***req*** name:{} value:{}", k, Arrays.toString(v));
    });
    int pageNo = getPage(req);
    int allDataNum = 17;
    model.addAttribute("items", getItems(pageNo));

    Page page = new Page(allDataNum, 5, pageNo);
    model.addAttribute("page", page);
    String info = String.format("first:%d, last:%d, current:%d, all:%d", page.getFirstPageNo(), page.getLastPageNo(), page.getCurrentPageNo(), page.getAllPageNum());
    model.addAttribute("pageInfo", info);

    return "table/index";
  }

  @PostMapping("select")
  public String select(HttpServletRequest req, Model model) {
    log.info("#select");
    req.getParameterMap().forEach((k,v) -> {
      log.info("***req*** name:{} value:{}", k, Arrays.toString(v));
    });
    model.addAttribute("items", getItems(getPage(req)));
    return "table/select";
  }

  private int getPage(HttpServletRequest req) {
    String _page = req.getParameter("_page");
    int page = 1;
    if (_page != null && _page.length() > 0) {
      page = Integer.parseInt(_page);
    }
    return page;
  }

  private List<Map<String, String>> getItems(int page) {
    List<Map<String, String>> items = new ArrayList<>();
    items.add(getRow("I100", "Cm19rhqIw", "gpdm3ug0sPAhqnto", "i100@example.com"));
    items.add(getRow("I101", "wy5nf0zMZ", "5ltKzoEiv7cnxoei", "i101@example.com"));
    items.add(getRow("I102", "kZ0tpci4M", "JwkcurmDlsub05kw", "i102@example.com"));
    items.add(getRow("I103", "hglBu38eI", "hrLDmdjs7al6mthz", "i103@example.com"));
    items.add(getRow("I104", "YtkR85len", "bieptncybkW8ql4Y", "i104@example.com"));
    items.add(getRow("I105", "9Soxp5Kwu", "29xjeuziehxoEheP", "i105@example.com"));
    items.add(getRow("I106", "5OakAoc9w", "nvlfpEjajrNdnf85", "i106@example.com"));
    items.add(getRow("I107", "hV05Jzprl", "i2kqubLf7vieiwmT", "i107@example.com"));
    items.add(getRow("I108", "gMtlci67G", "81yqOTnxprmaoepd", "i108@example.com"));
    items.add(getRow("I109", "sus86MtyC", "l3NfmZkzhv0wyels", "i109@example.com"));
    items.add(getRow("I110", "w0ZN6kdmc", "t7FIembuglcnwh2h", "i110@example.com"));
    items.add(getRow("I111", "ZkgMgka96", "tk6irjqifjC7sHep", "i111@example.com"));
    items.add(getRow("I112", "7fO1jeiVl", "kaiwuelE81kaMvnd", "i112@example.com"));
    items.add(getRow("I113", "EnfY1ut8q", "clGh1Oxiqutpz8fl", "i113@example.com"));
    items.add(getRow("I114", "06pgoRPqn", "b9ryVl2uskzksUvu", "i114@example.com"));
    items.add(getRow("I115", "Tpel20xOd", "ivk5ychtpW0tKcic", "i115@example.com"));
    items.add(getRow("I116", "7Eh4pwhwP", "cPvmslQn5kdutyv7", "i116@example.com"));
    // 0  0 -  4
    // 1  5 -  9
    // 2 10 - 14
    // 3 15 - 19
    return items.subList(page * 5, (page * 5) + 5);
  }

  private Map<String, String> getRow(String id, String key1, String key2, String email) {
    return Map.of("userId", id, "key1", key1, "key2", key2, "email", email);
  }

}
