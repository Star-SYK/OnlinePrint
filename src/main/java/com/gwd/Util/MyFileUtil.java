package com.gwd.Util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;


/** 
* @Description: OnlinePrint 
* @Param:  
* @return:  
* @Author: ChenYu
* @Date: 2019/5/4 
*/
public class MyFileUtil {

    /*本地部署时改到服务器路径*/
    //static final String filePath = "D:\\打印项目\\";

     public static final String filePath = "//home//print//upload//" ; //服务器上的路径



    public static String OutFile(MultipartFile file,String date) throws IOException {
        File dir = new File(filePath+date);
        if(!dir.exists()){
            dir.mkdir();
        }
        String newFileName = null;
        try {
            newFileName = DigestUtils.md5Hex(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        File path = null;
        if(file.getOriginalFilename().contains(".pdf")){
           path = new File(filePath+date+"//"+newFileName+".pdf");
        }else {
            path = new File(filePath+date+"//"+newFileName);
        }
        if(!path.exists()){
            path.createNewFile();
            InputStream in = file.getInputStream();
            FileOutputStream out = new FileOutputStream(path);
            byte[] media = new byte[1024];
            int length = in.read(media, 0, 1024);
            while(length  != -1) {
                out.write(media, 0, length);
                length = in.read(media, 0, 1024);
            }
            in.close();
            out.close();
        }
        return newFileName;
    }



    public static int CountPdf(String fileName,String date) throws IOException {
        File pdfFile = new File(filePath+date+"//"+fileName+".pdf");
        PDDocument document = null;
        int pages = 0;
        if(pdfFile.exists()){
            try {
                document = PDDocument.load(pdfFile);
                pages = document.getNumberOfPages();
                System.out.println(pages);
            }
            catch(Exception e) {
                System.out.println(e);
            }finally {
                document.close();
            }
        }
        return pages;
    }


    public static void Online(String md5,String fileName,String date,HttpServletResponse response) throws IOException {
        File f = new File(filePath+date+"//"+md5+".pdf");
        if(f.exists()){
            BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
            byte[] buf = new byte[1024];
            int len = 0;
            response.reset(); // 非常重要
            URL u = new URL("file:///" + filePath+date+"//"+md5+".pdf");
            response.setContentType(u.openConnection().getContentType());
            response.setHeader("Content-Disposition", "inline; filename=" + fileName);
            OutputStream out = response.getOutputStream();
            while ((len = br.read(buf)) > 0)
                out.write(buf, 0, len);
            br.close();
            out.close();
        }
    }


    public static void download(HttpServletRequest request, HttpServletResponse response,String path,String fileName)
            throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        File filename = new File(path);
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName, "utf-8"));
        OutputStream out = response.getOutputStream();
        FileInputStream fileinput = new FileInputStream(filename);
        byte[] charbuffer = new byte[1024];
        int length = 0;
        while ((length=fileinput.read(charbuffer))!=-1) {
            out.write(charbuffer, 0, length);
        }

    }





}
