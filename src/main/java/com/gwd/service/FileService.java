package com.gwd.service;

import com.gwd.entity.File;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FileService {
    Integer add(Integer userId, MultipartFile multipartFile) throws IOException, InterruptedException;
    File get(Integer id);
    void online(String md5, Integer fileId, HttpServletResponse response) throws IOException;
    File getByIdAndUserId(Integer id,Integer userId);
}
