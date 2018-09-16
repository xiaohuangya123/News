package com.xhy.reload.news.utils;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileUtils {

    /**
     * 将字符串写入到文本文件中
     * 文件名称不可以包含路径，默认存放在/data/data/<packagename>/files/文件夹下
     */
    public static void writeToLocalFile(String fileName, String content) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = MyApplication.getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));

            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}