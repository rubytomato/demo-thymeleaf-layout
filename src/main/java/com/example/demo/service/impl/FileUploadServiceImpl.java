package com.example.demo.service.impl;

import com.example.demo.dto.FileUploadResultDto;
import com.example.demo.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

  @Override
  public FileUploadResultDto upload(String fileName, long fileSize, InputStream inputStream) {
    log.info("original name:{} size:{}", fileName, fileSize);
    FileUploadResultDto dto = new FileUploadResultDto();
    dto.setFileName(fileName);
    dto.setProcessingResult("成功:10件 失敗:5件");
    dto.setResultList(getResult());

    return dto;
  }

  private List<String> getResult() {
    List<String> result = new ArrayList<>();
    result.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    result.add("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
    result.add("ccccccccccccccccccccccccccccccccccccccccccccccccc");
    result.add("ddddddddddddddddddddddddddddddddddddddddddddddddd");
    result.add("eeeeeeeeeeeeeeeee");
    result.add("ffffffffffff");
    result.add("gggggggggggggggggggggg");
    result.add("hhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
    result.add("iiiiiiiiiiiiiii");
    result.add("jjjjjjjj");
    result.add("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
    result.add("llllllllllllllllllllll");
    result.add("mmm");
    result.add("n");
    result.add("ooooooooooooooooooooooooooooooooooooo");
    result.add("ppppppppp");
    result.add("qqqqqqq");
    result.add("rrr");
    result.add("ssss");
    result.add("tt");
    result.add("uuuuuuuuuuuuuuuuuuuuuuuu");
    result.add("vvvvvvvvvvvvvvvvvv");
    result.add("wwwwww");
    result.add("xxx");
    result.add("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
    result.add("zzzzzzzzzzzzzzzzzzzzzz");
    //abcdefghijklmnopqrstuvwxyz
    return result;
  }

}
