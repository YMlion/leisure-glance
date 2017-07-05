package com.ymlion.lib.utils;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Image、Bitmap工具类
 *
 * @author YML
 * @date 2016/10/10
 */

public class ImageUtil {

    /**
     * 压缩图片，并返回保存的图片文件
     *
     * @param source 源Bitmap
     * @param maxKB 最大大小
     * @param parentDir 父目录
     * @param fileName 文件名
     * @return 保存后的文件
     */
    public static File compressBitmap(Bitmap source, int maxKB, String parentDir, String fileName) {
        if (source == null) {
            return null;
        }
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        int quality = 100;
        source.compress(Bitmap.CompressFormat.JPEG, quality, bOut);
        while (bOut.toByteArray().length / 1024 > maxKB) {
            quality -= 10;
            if (quality == 0) {
                quality = 1;
            } else if (quality < 0) {
                break;
            }
            bOut.reset();
            source.compress(Bitmap.CompressFormat.JPEG, quality, bOut);
        }

        String path = parentDir + File.separator + fileName;
        File file = new File(path);
        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bytes = bOut.toByteArray();
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                    if (source != null && !source.isRecycled()) {
                        source.recycle();
                        source = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
}
