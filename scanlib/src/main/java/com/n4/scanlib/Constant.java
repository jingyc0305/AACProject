package com.n4.scanlib;

public class Constant {

    public enum MediaStatus {
        MEDIA_ADD,
        MEDIA_REMOVE,
        MEDIA_SCANNER_START,
        MEDIA_SCANNER_FINISH,

    }
    //music list type
    public static final int TYPE_NO_LIST = -1;
    public static final int LOCAL_MUSIC_LIST = 0;
    public static final int CUSTOM_LIST = 1;
    public static final int ALL_MUSIC = 2;
    public static final int FAVOURITE = 3;
    public static final int RECENT_MUSIC = 4;

    //task type
    public static final int LOAD_MY_MUSIC_LIST1 = 1;
    public static final int LOAD_MY_MUSIC_LIST2 = 2;
    public static final int LOAD_MY_MUSIC_LIST3 = 3;
    public static final int LOAD_ALL_MUSIC = 4;
    public static final int LOAD_FAVOURITE = 5;
    public static final int LOAD_RECENT_MUSIC = 6;
    public static final int LOAD_LOCAL_MUSIC_LIST = 7;
    public static final int COPY_LOCAL_MUSIC = 8;
    public static final int FAVOUR_COPY_MUSIC = 9;
    public static final int LOAD_ALL_MUSIC_LIST = 10;
    public static final int SET_VR_TEXT = 11;

    //play mode
    public static final int SINGLE_CYCLE = 1;
    public static final int LIST_CYCLE = 2;
    public static final int RANDOM = 3;

    //play state
    public static final int RELEASE = 0;
    public static final int PLAY_ERR = 1;
    public static final int RESET = 2;
    public static final int PREPARE = 3;
    public static final int PLAY = 4;
    public static final int PAUSE = 5;
    public static final int STOP = 6;

    //show view type
    public static final int BT_MUSIC_VIEW = 1;
    public static final int MUSIC_VIEW = 2;
    public static final int TRUE_MUSIC = 3;
    public static final int NO_MUSIC_VIEW_TYPE = 4;

    //public
    public static final String UUID_LOCAL = "sk82_local";
    public static final long MUSIC_LIMIT_SIZE = 2 * 1024 * 1024;   //KB

    //search music show type
    public static final int SEARCH_ALL_MUSIC = 1;
    public static final int SEARCH_SONG = 2;
    public static final int SEARCH_ARTIST = 3;

    //favour_type
    public static final int TYPE_FAVOUR_MUSIC = 1;
    public static final int TYPE_FAVOUR_MUSIC_NOT = 0;

    //local music sharedpreferences
    public static final String MY_MUSIC_SHARE_PREFERENCES = "my_music_list_data";
    public static final String MY_MUSIC_DATA_LIST1 = "my_music_list1";
    public static final String MY_MUSIC_DATA_LIST2 = "my_music_list2";
    public static final String MY_MUSIC_DATA_LIST3 = "my_music_list3";

    //music max
    public static final int MUSIC_FILE_MAX = 1000;

    //local music path
    public static final String MUSIC_LOCAL_PAYH = "/sdcard/Music/";                                  // Environment.getExternalStorageDirectory().getPath()

    //local db name
    public static final String DB_NAME = "sk82_music";

    //reset type
    public static final int TYPE_NO_MUSIC = 1;                                                       //没有音乐文件
    public static final int TYPE_RESET = 2;                                                          //没有播放音乐，有音乐文件
    public static final int TYPE_PLAY_MUSIC = 3;                                                     //有音乐播放，有音乐播放

    //local music
    public static final boolean TYPE_LOCAL_MUSIC = false;                                            //true : 本地音乐（/sdcard/Music）false : (/Local_Music)

    //usb memory play
    public static final int TYPE_USB_MEMORY_PLAY = 5000;

    //vr act
    public static final int VR_NO_PLAY_ACT = 0;
    public static final int VR_PLAY = 1;
    public static final int VR_PAUSE = 2;
    public static final int VR_STOP = 3;


}
