package com.gwd.Util;


import java.io.*;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public class CmdUtil {

    //  String str = "unoconv -f pdf /home/print/upload/1 /home/print/pdf/1";

    private static final String cmdPrefix = "unoconv -f pdf /home/print/upload/";

    public static void exec(String fileName,String date) throws InterruptedException {
        File file = new File(MyFileUtil.filePath+date+"//"+fileName+".pdf");
        if(!file.exists()){
            Runtime run = Runtime.getRuntime();
            Process process = null;
            try {
                process = run.exec(cmdPrefix+date+"/"+fileName);
                process.waitFor();
//                InputStream in = process.getInputStream();
//                BufferedReader bs = new BufferedReader(new InputStreamReader(in));
//                // System.out.println("[check] now size \n"+bs.readLine());
//                StringBuffer out = new StringBuffer();
//                byte[] b = new byte[8192];
//                for (int n; (n = in.read(b)) != -1; ) {
//                    out.append(new String(b, 0, n));
//                }
//                System.out.println("job result [" + out.toString() + "]");
//                in.close();
               // process.waitFor();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                process.destroy();
            }
        }
    }


    /*
    *             System.out.println(cmdPrefix+fileName);
            Process pro = null;
            Runtime runTime = Runtime.getRuntime();
            if (runTime != null) {
                try {
                    pro = runTime.exec(cmdPrefix+fileName);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }finally {
                    pro.destroy();
                }
            }
    * */
    public void executeLinuxCmd(String cmd) {
        System.out.println("got cmd job : " + cmd);

    }

}
