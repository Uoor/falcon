package com.falcon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by fanshuai on 15/4/21.
 */
public class FalconAssignTools {

    private static Properties prop = new Properties();
    static {
        InputStream in  = null;
        try {
            File file = new File("/data/env/falcon.assign.properties");
            if (file.exists()&&!file.isDirectory()){
                in = new FileInputStream("/data/env/falcon.assign.properties");
                if(in!=null) {
                    prop.load(in);
                }
            }else {
                System.out.println("not config assign with file :"+file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(in!=null){
                    in.close();
                }
            } catch (IOException e) {
                in = null;
            }
        }
    }

    public static String getProperty(String key){
        return prop.getProperty(key);
    }

    public static void main(String[] args){


        System.out.println(FalconAssignTools.class.getResource("/data/webapp/env"));
    }
}
