package com.example.greendaolib.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.greendaolib.DataBaseHelper;
import com.example.greendaolib.IMusicRoomCallBack;
import com.example.greendaolib.MusicBean;
import com.example.greendaolib.MusicGreenDaoRepository;

import java.util.List;

/**
 * @author: JingYuchun
 * @date:
 * @desc:
 */
public class MusicGreenDaoViewModel extends AndroidViewModel {

    private MutableLiveData<List<MusicBean>> mutableLiveMusics;

    private MusicGreenDaoRepository wordRepository;

    private Application application;
    public MusicGreenDaoViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        wordRepository = new MusicGreenDaoRepository(DataBaseHelper.getInstance(application));
    }
    /**
     * 查询数据库数据
     *
     */
    public MutableLiveData<List<MusicBean>> getAllMusics(){
        mutableLiveMusics = new MutableLiveData<>();
        mutableLiveMusics.postValue(wordRepository.queryAllMusic());
        return mutableLiveMusics;
    }
    /**
     * 向数据库添加数据
     * @param word
     * @param iMusicRoomCallBack
     */
    public void addMusic(MusicBean word, IMusicRoomCallBack iMusicRoomCallBack){
        if (wordRepository != null) {
            wordRepository.addMusic(word,iMusicRoomCallBack);
        }
    }
    /**
     * 删除一条数据
     * @param word
     * @param iMusicRoomCallBack
     */
    public void deleteMusic(MusicBean word, IMusicRoomCallBack iMusicRoomCallBack){
        if (wordRepository != null) {
            wordRepository.deleteMusic(word,iMusicRoomCallBack);
        }
    }
    /**
     * 修改一条数据
     * @param word
     * @param iMusicRoomCallBack
     */
    public void updateMusic(MusicBean word, IMusicRoomCallBack iMusicRoomCallBack){
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
