package com.example.demo.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class FileUploadForm implements Serializable {
  @NotNull
  @Size(min = 1, max = 255)
  private String email;
  @NotNull
  @Size(min = 1, max = 255)
  private String password;
  @NotNull
  @Size(min = 1, max = 255)
  private String fileType;

  private MultipartFile file;
}
