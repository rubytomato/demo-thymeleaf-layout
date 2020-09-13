package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpTests {

  @Test
  public void replace() {
    String array[] = {
        "ttc-employee",
        "ttc-outsource",
        "aaa-employee",
        "aaa-outsource",
        "bbb-employee",
        "bbb-outsource",
        "administrator",
        "helpdesk",
        "auth-infra",
        "ccc-ddd-employee",
        "ccc-ddd-outsource"
    };

    String array2[] = {
        "aaa.bbb@au.com",
        "bbb.ccc@au.com",
        "ccc.ddd@au.com",
        "aaa.bbb@docomo.ne.jp",
        "bbb.ccc@docomo.ne.jp",
        "ccc.ddd@docomo.ne.jp",
        "aaa.bbb@softbank.ne.jp",
        "bbb.ccc@softbank.ne.jp",
        "ccc.ddd@softbank.ne.jp",
        "dummy string",
        "ノイズ",
        "aaa.bbb.ccc-docomo.ne.jp"
    };

    //String regex = "^(.+)(-employee|-outsource)$";
    //String regex = "^((?!ttc))-.+$";
    //String regex = "^(ttc)-(employee|outsource)$";
    //String regex = "^((?!ttc).+)(-employee|-outsource)$";
    String regex = "^((?!bbb).+)@(au.com|docomo.ne.jp)$";
    Pattern p = Pattern.compile(regex);

    for (String email : array2) {
      System.out.print("email [" + email + "] ---> ");
      Matcher m = p.matcher(email);
      if (m.find()) {
        String g1 = m.group(1);
        String g2 = m.group(2);
        //System.out.println("found g1=" + g1 + " g2=");
        System.out.println("match: " + g1 + "@" + g2);
      } else {
        System.out.println("not match");
      }
    }

  }

}
