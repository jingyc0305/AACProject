package com.example.aacdemo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Base64Util {
    public static Bitmap base64ToBitmap(String base64Str){
        byte[] decode = Base64.decode(base64Str, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decode,0,decode.length);
    }
}
