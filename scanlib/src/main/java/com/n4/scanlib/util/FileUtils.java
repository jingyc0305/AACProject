package com.n4.scanlib.util;

import android.os.Environment;

import com.n4.scanlib.Constant;

import java.io.File;

public class FileUtils {


    /**
     *获取本地保存路径
     */
    public static String getLocalMusicPath(String music_name, String uuid) {
        String out_path = "";
        if (Constant.TYPE_LOCAL_MUSIC) {
            out_path = Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_MUSIC + "/" + uuid + "/" + music_name;
        } else {
            out_path = "/Local_Music" + "/" + uuid + "/" + music_name;
        }

        return out_path;
    }



    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }


}
