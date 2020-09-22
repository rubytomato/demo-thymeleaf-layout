package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FileUploadResultDto implements Serializable {
  private String fileName;
  private String processingResult;
  private List<String> resultList;

  @JsonProperty("resultPrint")
  public String resultPrint() {
    return resultList.stream().collect(Collectors.joining(System.lineSeparator()));
  }
}
