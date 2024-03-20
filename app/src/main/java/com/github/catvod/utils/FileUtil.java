package com.github.catvod.utils;

import com.github.catvod.spider.Init;
import io.github.pixee.security.BoundedLineReader;
import io.github.pixee.security.SystemCommand;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {

    public static File getCacheDir() {
        return Init.context().getExternalCacheDir();  //获取应用程序配置的缓存目录
    }

    public static File getCacheFile(String fileName) {  //获取应用程序配置的缓存目录下的文件
        return new File(getCacheDir(), fileName);
    }

    public static void write(File file, String data) {
        write(file, data.getBytes());
    }

    public static void write(File file, byte[] data) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.flush();
            fos.close();
            chmod(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static File chmod(File file) {
        try {
            Process process = SystemCommand.runCommand(Runtime.getRuntime(), "chmod 777 " + file);
            process.waitFor();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return file;
        }
    }

    public static String read(File file) {
        try {
            return read(new FileInputStream(file));
        } catch (Exception e) {
            return "";
        }
    }


    public static String read(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = BoundedLineReader.readLine(br, 5_000_000)) != null) sb.append(text).append("\n");
            br.close();
            return Utils.substring(sb.toString());
        } catch (Exception e) {
            return "";
        }
    }


}
