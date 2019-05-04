package com.gwd.service.impl;

import com.gwd.Util.ChangeSize;
import com.gwd.Util.CmdUtil;
import com.gwd.Util.MyFileUtil;
import com.gwd.dao.FileDao;
import com.gwd.entity.File;
import com.gwd.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service("fileService")
public class FileServiceImpl implements FileService {

    @Resource
    private FileDao fileDao;

    public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Integer add(Integer userId, MultipartFile uploadFile) throws IOException, InterruptedException {
        String name = uploadFile.getOriginalFilename();
        Date createTime = new Date();
        String date = formatter.format(createTime);
        String md5 = MyFileUtil.OutFile(uploadFile,date);
        String size = ChangeSize.fileSizeConver(uploadFile.getSize());
        Integer page = 0;
        /*    public File(String userId, String size, String md5, String name) {*/
        File file = new File(userId,size,md5,name,page,createTime);
        fileDao.insert(file);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Integer page = 0;
                if(!name.contains(".pdf")){
                    try {
                        CmdUtil.exec(md5,date);  // 转pdf
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    page  = MyFileUtil.CountPdf(md5,date);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                file.setPage(page);
                fileDao.updateById(file);
            }
        });
        t.setDaemon(false);
        t.start();
        return file.getId();
    }

    @Override
    public File get(Integer id) {
        return fileDao.selectById(id);
    }

    @Override
    public void online(String md5, Integer fileId, HttpServletResponse response) throws IOException {
        File file = fileDao.selectById(fileId);
        if(file != null && file.getMd5() .equals(md5) ){
            MyFileUtil.Online(file.getMd5(),file.getName(),formatter.format(file.getCreateTime()),response);
        }
    }

    @Override
    public File getByIdAndUserId(Integer id, Integer userId) {
        return fileDao.getByIdAndUserId(id, userId);
    }
}