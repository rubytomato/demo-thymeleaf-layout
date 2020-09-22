package com.example.demo.controller;

import com.example.demo.dto.FileUploadResultDto;
import com.example.demo.service.FileUploadService;
import com.example.demo.validator.FileUploadFormValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("file-upload")
@Slf4j
public class FileUploadController {

  @Autowired
  private FileUploadService fileUploadService;

  @Autowired
  private FileUploadFormValidator multipartFileValidator;

  @InitBinder
  private void initBinderFileBucket(WebDataBinder binder) {
    binder.setValidator(multipartFileValidator);
  }

  @GetMapping
  public String init(HttpServletRequest req, Model model) {
    log.info("#init");
    FileUploadForm form = new FileUploadForm();
    return index(form, req, model);
  }

  @PostMapping
  public String index(@ModelAttribute FileUploadForm form, HttpServletRequest req, Model model) {
    log.info("#index");
    Map<String, String> fileTypeList = Map.of("pdf", "PDF", "csv", "CSV", "dat", "DAT");
    model.addAttribute("fileTypeList", fileTypeList);
    model.addAttribute("fileUploadForm", form);
    return "file-upload/index";
  }

  @PostMapping("upload")
  public String upload(@Validated @ModelAttribute FileUploadForm form, BindingResult result, HttpServletRequest req, Model model) {
    log.info("#upload");
    if (result.hasErrors()) {
      log.error("file upload error");
      for (ObjectError allError : result.getAllErrors()) {
        log.error("defaultMessage = {} , objectName = {} , code = {}", allError.getDefaultMessage(), allError.getObjectName(), allError.getCode());
      }
      model.addAttribute("errorMessage", "必須入力項目が指定されていません。");
      return index(form, req, model);
    }

    // ファイル未指定
    if (form.getFile() == null || form.getFile().getOriginalFilename().isEmpty()) {
      log.error("file name null");
    }

    // ファイル未指定
    if (form.getFile() == null || form.getFile().getSize() == 0L) {
      log.error("file size zero");
      model.addAttribute("errorMessage", "アップロードファイルの指定に誤りがあります");
      ObjectError error = new FieldError("fileUploadForm", "file", "アップロードファイルが指定されていません");
      result.addError(error);
      return index(form, req, model);
    }

    FileUploadResultDto dto = null;
    try {
      dto = fileUploadService.upload(form.getFile().getOriginalFilename(),
          form.getFile().getSize(),
          form.getFile().getInputStream());
      model.addAttribute("result", dto);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "file-upload/upload";
  }

}
