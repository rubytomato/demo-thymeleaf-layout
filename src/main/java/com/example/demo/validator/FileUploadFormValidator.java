package com.example.demo.validator;

import com.example.demo.controller.FileUploadForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FileUploadFormValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return FileUploadForm.class.isAssignableFrom(aClass);
  }

  @Override
  public void validate(Object target, Errors errors) {
    FileUploadForm fileUploadForm = (FileUploadForm) target;
    // ファイル未指定
    if (fileUploadForm.getFile().getOriginalFilename().isEmpty()) {
      errors.rejectValue("file", "app.file.upload.name.empty.message");
    }
    // 0バイトファイル
    if (fileUploadForm.getFile().getSize() == 0L) {
      errors.rejectValue("file", "app.file.upload.size.empty.message");
    }
    // ファイルタイプ未入力
    if (fileUploadForm.getFileType() == null || fileUploadForm.getFileType().isEmpty()) {
      errors.rejectValue("fileType", "app.file.upload.name.empty.message");
    }
    // パスワード未入力
    if (fileUploadForm.getPassword() == null || fileUploadForm.getPassword().isEmpty()) {
      errors.rejectValue("password", "app.file.upload.name.empty.message");
    }
    // メールアドレス未入力
    if (fileUploadForm.getEmail() == null || fileUploadForm.getEmail().isEmpty()) {
      errors.rejectValue("email", "app.file.upload.name.empty.message");
    }
  }

}
