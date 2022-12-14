package com.jimas.elastic.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author liuqj
 */
@RestController
@RequestMapping("api/")
public class DicController {
    private static final String HEAD_LAST_MODIFIED = "Last-Modified";
    private static final String HEAD_ETAG = "ETag";

    @RequestMapping("extra_dic")
    public void extraDic(String dicType, HttpServletResponse response) throws IOException {
        String pathName = Objects.equals(dicType, "1") ? "extra.dic" : "stop.dic";
        ClassPathResource classPathResource = new ClassPathResource(pathName);
        final File file = classPathResource.getFile();
        final String md5Hex = DigestUtils.md5Hex(new FileInputStream(file));
        response.setHeader(HEAD_LAST_MODIFIED, md5Hex);
        response.setHeader(HEAD_ETAG, md5Hex);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        //text/plain 普通文本
        response.setContentType("text/plain;charset=UTF-8");
        try (InputStream inputStream =new FileInputStream(file); OutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } finally {
            response.flushBuffer();
        }
    }
}
