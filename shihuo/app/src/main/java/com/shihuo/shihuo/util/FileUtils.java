package com.shihuo.shihuo.util;

import android.util.Log;

import java.io.File;

/**
 * Created by cm_qiujiaheng on 2016/12/6.
 */

public class FileUtils {

    public static String getFileName(String result) {
        File tempFile = new File(result.trim());
        String fileName = tempFile.getName();
        Log.i("takePhoto", "takeSuccess： fileName = " + fileName);
        return fileName;
    }
}
