package com.n4.scanlib.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.n4.scanlib.Constant;
import com.n4.scanlib.lisenter.OnUsbStatusChangedListener;
import com.n4.scanlib.dao.MusicBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediaStoreManager {

    private String TAG = "music-" + MediaStoreManager.class.getSimpleName();
    private static Context mContext;
    private String is_fav = "is_fav";
    private String recent_count = "recent_count";
    private String recent_time = "recent_time";
    private String uuid = "dev_uuid";
    private ContentResolver mContentResolver;
    private List<OnUsbStatusChangedListener> onUsbStatusChangedListenerList = new ArrayList<>();

    private static volatile MediaStoreManager instance = null;

    private MediaStoreManager(Context context) {
        mContext = context;
        mContentResolver = mContext.getContentResolver();
    }

    public static MediaStoreManager getMediaStoreManagerInstance(Context context) {
        if (instance == null) {
            synchronized (MediaStoreManager.class) {
                if (instance == null) {
                    instance = new MediaStoreManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void setOnUsbStateChangeListener(OnUsbStatusChangedListener listener){
        this.onUsbStatusChangedListenerList.add(listener);
    }

    public void notifyUsbState(Constant.MediaStatus mediaStatus){
        switch (mediaStatus){
            case MEDIA_ADD:

            case MEDIA_SCANNER_START:

                for(OnUsbStatusChangedListener listener:onUsbStatusChangedListenerList){
                    if(listener!=null){
                        listener.onScanStart();
                    }
                }
                break;
            case MEDIA_SCANNER_FINISH:
                for(OnUsbStatusChangedListener listener:onUsbStatusChangedListenerList){
                    if(listener!=null){
                        listener.onScanFinish();
                    }
                }
                break;
            case MEDIA_REMOVE:
                for(OnUsbStatusChangedListener listener:onUsbStatusChangedListenerList){
                    if(listener!=null){
                        listener.onUsbRemoved();
                        MusicListManager.getMusicListManagerInstance(mContext).notifyList(Constant.ALL_MUSIC);

                    }
                }
                break;
        }
    }


    public void setFavourMusic(int favour_type, String uuid, String music_name) {
        Toast.makeText(mContext, "uuid:"+uuid+"  name:"+music_name, Toast.LENGTH_SHORT).show();

        Log.d(TAG, "setFavourMusic: favour_type : " + favour_type + " uuid : " + uuid + " music name : " + music_name);
        ContentValues values = new ContentValues();
        if (favour_type == 0) {
            values.put(is_fav, "0");
        } else {
            values.put(is_fav, "1");
        }
//        mContentResolver.update(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values, "_display_name=? and dev_uuid=?", new String[]{"\"" + music_name + "\"", "\"" + uuid + "\""});
        Toast.makeText(mContext, "uuid:"+uuid+"  name:"+music_name, Toast.LENGTH_SHORT).show();

        int result = mContentResolver.update(MediaStore.Files.getContentUri("external"), values, "_display_name=? and dev_uuid=?", new String[]{music_name, uuid});


        MusicListManager.getMusicListManagerInstance(mContext).notifyList(Constant.ALL_MUSIC);
        return;
    }


    public ArrayList<MusicBean> getAllMusic(List<String> dev_uuid) {

        ArrayList<MusicBean> mMusicList = new ArrayList<MusicBean>();

        if (dev_uuid == null) {
            return mMusicList;
        }
        int dev_uuid_size = dev_uuid.size();
        int position = 0;
        if (dev_uuid_size < 1) {
            return mMusicList;
        }

        String[] projection = new String[]{MediaStore.Audio.AudioColumns._ID, MediaStore.Audio.AudioColumns.ALBUM_ID, MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.DISPLAY_NAME, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ARTIST, MediaStore.Audio.AudioColumns.ALBUM, is_fav, uuid, recent_count, MediaStore.Audio.AudioColumns.SIZE};

        String selection = "dev_uuid=?";

//        String[] selectionArgs = new String[]{dev_uuid};

        String[] selectionArgs = new String[dev_uuid_size];

        selectionArgs[0] = dev_uuid.get(position);

        Log.d(TAG, "getAllMusic: uuid : " + dev_uuid.get(position));

        position++;

        while (position < dev_uuid_size) {
            selection = selection + "or dev_uuid=? ";
            selectionArgs[position] = dev_uuid.get(position);
            Log.d(TAG, "getAllMusic: uuid : " + dev_uuid.get(position));
            position++;
        }

        Log.d(TAG, "getAllMusic: selection : " + selection);

        Cursor cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, MediaStore.Audio.AudioColumns.DISPLAY_NAME + " asc");

        int music_id = 0;

        int fileId;

        String fileName;

        String music_title;

        String filePath;

        int fileSize;

        String artist;

        String music_album;

        int favour_type;

        String dev_value;

        int recent = 0;

        while (cursor.moveToNext()) {

            music_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID));

            fileId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID));

            fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME));

            filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));

            fileSize = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.SIZE));

            music_title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE));

            artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));

            music_album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM));

            favour_type = cursor.getInt(cursor.getColumnIndex(is_fav));

            dev_value = cursor.getString(cursor.getColumnIndex(uuid));

            recent = cursor.getInt(cursor.getColumnIndex(recent_count));

//            Log.e("ryze_music", fileId + " -- " + fileName + " -- " + filePath + " -- " + favour_type + dev_uuid);

            if (fileName == null || filePath == null) {
                continue;
            }

            try {
                File f = new File(filePath);
                if (!f.exists()) {
                    Log.e(TAG, "getAllMusic: music " + filePath + " not exist");
                    continue;
                }
                if (!f.isFile()) {
                    Log.e(TAG, "getAllMusic: music " + filePath + " not file");
                    continue;
                }

            } catch (Exception e) {
                continue;
            }

            MusicBean fileItem = new MusicBean(null,fileName, music_title, artist, music_album, fileSize, filePath, favour_type, music_id, recent, dev_value,  dev_value);
            mMusicList.add(fileItem);

            Log.e(TAG,fileItem.toString());


        }

        cursor.close();
        cursor = null;

        return remove_duplicate_data_in_list(mMusicList);
    }


    //list 排重
    public ArrayList<MusicBean> remove_duplicate_data_in_list(ArrayList<MusicBean> list) {
        Log.d(TAG, "remove_duplicate_data_in_list: enter");
        ArrayList<MusicBean> out_list = new ArrayList<MusicBean>();
        if (list == null) {
            return out_list;
        }
        if (list.size() <= 0) {
            return out_list;
        }
        int list_position = 0;
        int out_list_position = 0;
        boolean flag = false;            //是否添加到list中
        for (MusicBean list_data : list) {
            if(out_list.size() > Constant.MUSIC_FILE_MAX){
                break;
            }
            if (list_position != 0) {
                for (MusicBean out_list_data : out_list) {
                    flag = true;
                    try {
//                        if (list_data.getUuid().equals(out_list_data.getUuid()) && list_data.get_music_name().equals(out_list_data.get_music_name())) {
//                            flag = false;
//                            Log.d(TAG, "remove_duplicate_data_in_list: duplicate data music name :" + list_data.get_music_name());
//                            break;
//                        }
                        /* 不同U盘和相同U盘里的同名歌曲都过滤去重*/
                        if (list_data.getMusic_name().equals(out_list_data.getMusic_name())) {
                            flag = false;
                            Log.d(TAG, "remove_duplicate_data_in_list: duplicate data music name :" + list_data.getMusic_name());
                            break;
                        }
                    }catch (Exception e){
                        flag = false;
                        Log.e(TAG, "remove_duplicate_data_in_list: err" );
                        Log.d(TAG, "remove_duplicate_data_in_list: duplicate data music name : " + list_data.getMusic_name() + " duplicate data music uuid : " + list_data.getUuid());
                        Log.d(TAG, "remove_duplicate_data_in_list: duplicate data music name : " + out_list_data.getMusic_name() + " duplicate data music uuid : " + out_list_data.getUuid());
                        e.printStackTrace();
                    }
                }
                if (flag) {
                    out_list.add(list_data);
                }
            } else {
                out_list.add(list.get(list_position));                                               //添加第一条数据
            }
            list_position++;
        }
        return out_list;
    }


}
