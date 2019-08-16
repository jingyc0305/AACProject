package com.example.greendaolib

import android.os.AsyncTask
import com.example.greendaolib.api.IMusicData

/**
 * @author: JingYuchun
 * @date: 2019/8/8 11:11
 * @desc: 音乐repo仓库 数据源
 */
class MusicGreenDaoRepository(var dataBaseHelper: DataBaseHelper) : IMusicData {

//    var allMusics: List<MusicBean>? = null
//        get() = dataBaseHelper?.queryData()

    override fun addMusic(music: MusicBean, iMusicRoomCallBack: IMusicRoomCallBack) {
        MusicAsyncTask(dataBaseHelper, Contonst.ADD_MUSIC, iMusicRoomCallBack).execute(music)
    }

    override fun deleteMusic(music: MusicBean, iMusicRoomCallBack: IMusicRoomCallBack) {
        MusicAsyncTask(dataBaseHelper, Contonst.DELETE_MUSIC, iMusicRoomCallBack).execute(music)
    }

    override fun deleteAllMusic(iMusicRoomCallBack: IMusicRoomCallBack) {
        MusicAsyncTask(dataBaseHelper, Contonst.DELETE_ALL_MUSIC, iMusicRoomCallBack).execute()
    }

    override fun updateMusic(music: MusicBean, iMusicRoomCallBack: IMusicRoomCallBack) {
        MusicAsyncTask(dataBaseHelper, Contonst.UPDATE_MUSIC, iMusicRoomCallBack).execute(music)
    }

    override fun queryAllMusic(): List<MusicBean>? {
        return dataBaseHelper.queryData()
    }

    private class MusicAsyncTask(
        private val dataBaseHelper: DataBaseHelper?,
        private var opreationType: Int?,
        private val iMusicRoomCallBack: IMusicRoomCallBack
    ) :
        AsyncTask<MusicBean, Void, Boolean>() {

        override fun doInBackground(vararg voids: MusicBean): Boolean? {
            try {
                when (opreationType) {
                    Contonst.ADD_MUSIC -> dataBaseHelper?.insertData(voids[0])
                    Contonst.DELETE_MUSIC -> dataBaseHelper?.deleteData(voids[0])
                    Contonst.UPDATE_MUSIC -> dataBaseHelper?.updateData(voids[0])
                    Contonst.DELETE_ALL_MUSIC -> dataBaseHelper?.deleteAll()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                e.message?.let { iMusicRoomCallBack.onFailed(it) }
            }

            return true
        }

        override fun onPostExecute(boolean: Boolean) {
            super.onPostExecute(boolean)
            iMusicRoomCallBack.onSucess()
        }
    }

}