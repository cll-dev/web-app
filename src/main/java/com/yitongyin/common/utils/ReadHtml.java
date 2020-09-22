package com.yitongyin.common.utils;

import java.io.*;

public class ReadHtml {

    public static String reMailString(String fileName){
        //String info="";
        StringBuffer buff=new StringBuffer();
        InputStreamReader in=null;
        BufferedReader br=null;
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(fileName);
       // File file=new File(path);
        try {
            in=new InputStreamReader(inputStream,"UTF-8");
            br=new BufferedReader(in);
            String line=null;
            while((line=br.readLine()) != null){
                //System.out.println(line);
                buff.append(line).append("\n");
            }


        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }finally{
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }

        return buff.toString();
    }

}
