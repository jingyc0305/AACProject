package com.example.roomlib

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.roomlib.api.IMusicData
import com.example.roomlib.db.Music
import com.example.roomlib.db.MusicDao
import com.example.roomlib.db.MusicRoomDB



/**
 * @author: JingYuchun
 * @date: 2019/8/8 11:11
 * @desc: 音乐repo仓库 数据源
 */
class MusicRepository : IMusicData {
    private var musicDao: MusicDao? = null

    constructor(application: Application) {
        val wordRoomDB = MusicRoomDB.getInstance(application)
        musicDao = wordRoomDB?.getMusicDao()
    }
    var allMusics: LiveData<List<Music>>? = MutableLiveData()
        get() = musicDao?.queryData()

    override fun addMusic(music: Music, iMusicRoomCallBack: IMusicRoomCallBack) {
        MusicAsyncTask(musicDao, Contonst.ADD_MUSIC, iMusicRoomCallBack).execute(music)
    }

    override fun deleteMusic(music: Music, iMusicRoomCallBack: IMusicRoomCallBack) {
        MusicAsyncTask(musicDao, Contonst.DELETE_MUSIC, iMusicRoomCallBack).execute(music)
    }

    override fun deleteAllMusic(iMusicRoomCallBack: IMusicRoomCallBack) {
        MusicAsyncTask(musicDao, Contonst.DELETE_ALL_MUSIC, iMusicRoomCallBack).execute()
    }

    override fun updateMusic(music: Music, iMusicRoomCallBack: IMusicRoomCallBack) {
        MusicAsyncTask(musicDao, Contonst.UPDATE_MUSIC, iMusicRoomCallBack).execute(music)
    }

    override fun queryAllMusic(): LiveData<List<Music>>? {
        return musicDao?.queryData()
    }

    private class MusicAsyncTask(
        private val wordAsyncDao: MusicDao?,
        private var opreationType: Int?,
        private val iMusicRoomCallBack: IMusicRoomCallBack
    ) :
        AsyncTask<Music, Void, Boolean>() {

        override fun doInBackground(vararg voids: Music): Boolean? {
            try {
                when (opreationType) {
                    Contonst.ADD_MUSIC -> wordAsyncDao?.insertData(voids[0])
                    Contonst.DELETE_MUSIC -> wordAsyncDao?.deleteData(voids[0])
                    Contonst.UPDATE_MUSIC -> wordAsyncDao?.updateData(voids[0])
                    Contonst.DELETE_ALL_MUSIC -> wordAsyncDao?.deleteAll()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                iMusicRoomCallBack.onFailed()
            }

            return true
        }

        override fun onPostExecute(boolean: Boolean) {
            //super.onPostExecute(aVoid)
            iMusicRoomCallBack.onSucess()
        }
    }

}