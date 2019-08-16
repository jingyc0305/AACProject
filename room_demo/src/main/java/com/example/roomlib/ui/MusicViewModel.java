package com.example.roomlib.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.roomlib.IMusicRoomCallBack;
import com.example.roomlib.MusicRepository;
import com.example.roomlib.db.Music;

import java.util.List;

/**
 * @author: JingYuchun
 * @date:
 * @desc:
 */
public class MusicViewModel extends AndroidViewModel {

    private LiveData<List<Music>> mutableLiveMusics;

    private MusicRepository wordRepository;

    private Application application;
    public MusicViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }
    /**
     * 获取数据库数据
     * @return
     */
    public LiveData<List<Music>> getTableLiveMusics() {
        return mutableLiveMusics;
    }

    /**
     * 查询数据库数据
     *
     */
    public LiveData<List<Music>> getAllMusics(){
        wordRepository = new MusicRepository(application);
        return mutableLiveMusics = wordRepository.getAllMusics();
    }
    /**
     * 向数据库添加数据
     * @param word
     * @param iMusicRoomCallBack
     */
    public void addMusic(Music word, IMusicRoomCallBack iMusicRoomCallBack){
        if (wordRepository != null) {
            wordRepository.addMusic(word,iMusicRoomCallBack);
        }
    }
    /**
     * 删除一条数据
     * @param word
     * @param iMusicRoomCallBack
     */
    public void deleteMusic(Music word, IMusicRoomCallBack iMusicRoomCallBack){
        if (wordRepository != null) {
            wordRepository.deleteMusic(word,iMusicRoomCallBack);
        }
    }
    /**
     * 修改一条数据
     * @param word
     * @param iMusicRoomCallBack
     */
    public void updateMusic(Music word, IMusicRoomCallBack iMusicRoomCallBack){
        if (wordRepository != null) {
            wordRepository.updateMusic(word,iMusicRoomCallBack);
        }
    }
    /**
     * 删除全部数据
     * @param iMusicRoomCallBack
     */
    public void deleteAllMusic(IMusicRoomCallBack iMusicRoomCallBack){
        if (wordRepository != null) {
            wordRepository.deleteAllMusic(iMusicRoomCallBack);
        }
    }
}
