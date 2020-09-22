package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "app.custom.env")
@Validated
@Data
public class CustomEnvProperties {

  @NotNull
  private String value1;

  @NotNull
  private String value2;

  @NotNull
  private String value3;

}
