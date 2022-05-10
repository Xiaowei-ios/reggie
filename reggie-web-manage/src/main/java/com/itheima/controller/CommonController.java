package com.itheima.controller;


import com.itheima.common.R;
import com.itheima.common.util.OssTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController{


    @Autowired
    OssTemplate ossTemplate;



    /**
     * 图片上传
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public R upload(MultipartFile file) throws IOException {
        return R.success(ossTemplate.upload(file.getOriginalFilename(), file.getInputStream()));
    }
}