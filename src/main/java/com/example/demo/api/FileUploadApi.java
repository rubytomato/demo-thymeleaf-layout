package com.example.demo.api;

import com.example.demo.controller.FileUploadForm;
import com.example.demo.dto.FileUploadResultDto;
import com.example.demo.service.FileUploadService;
import com.example.demo.validator.FileUploadFormValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UncheckedIOException;

@RestController
@RequestMapping("api/file-upload")
@Slf4j
public class FileUploadApi {

  @Autowired
  private FileUploadService fileUploadService;

  @Autowired
  private FileUploadFormValidator multipartFileValidator;

  @InitBinder
  private void initBinderFileBucket(WebDataBinder binder) {
    binder.setValidator(multipartFileValidator);
  }

  @PostMapping("upload")
  public ResponseEntity<FileUploadResultDto> upload(@Validated @ModelAttribute FileUploadForm form, BindingResult result) {
    if (result.hasErrors()) {
      log.error("file upload error");
      for (ObjectError allError : result.getAllErrors()) {
        log.error("objectName = {} , code = {}, defaultMessage = {}, arguments = {}",
            allError.getObjectName(),
            allError.getCode(),
            allError.getDefaultMessage(),
            allError.getArguments());
      }
      for (FieldError fieldError : result.getFieldErrors()) {
        log.error("field:{}, objectName = {}, code = {}, defaultMessage = {}, arguments = {}",
            fieldError.getField(),
            fieldError.getObjectName(),
            fieldError.getCode(),
            fieldError.getDefaultMessage(),
            fieldError.getArguments());
      }
      return ResponseEntity.badRequest().build();
    }

    try {
      FileUploadResultDto resultDto = fileUploadService.upload(
          form.getFile().getOriginalFilename(),
          form.getFile().getSize(),
          form.getFile().getInputStream());
      return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }

  }

}
