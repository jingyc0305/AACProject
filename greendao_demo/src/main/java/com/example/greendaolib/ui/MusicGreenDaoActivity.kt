package com.example.roomlib.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.example.greendaolib.IMusicRoomCallBack
import com.example.greendaolib.MusicBean
import com.example.greendaolib.R
import com.example.greendaolib.ui.MusicGreenDaoViewModel
import kotlinx.android.synthetic.main.activity_main_greendao.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author: JingYuchun
 * @date: 2019/8/10 13:44
 * @desc:
 */
class MusicGreenDaoActivity : AppCompatActivity() {
    private var musicGreenDaoViewModel: MusicGreenDaoViewModel? = null
    private var list: List<MusicBean>? = ArrayList()
    private var musicBeanAdapter: MusicBeanAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_greendao)
        musicGreenDaoViewModel = ViewModelProviders.of(this).get(MusicGreenDaoViewModel::class.java)

        subscribeMusicData()

        musicBeanAdapter = MusicBeanAdapter(this, list)

        room_recycle.adapter = musicBeanAdapter
        room_recycle.layoutManager = LinearLayoutManager(this)
        room_recycle.smoothScrollToPosition(0)

        //增
        green_dao_add_btn.setOnClickListener {
            var music = MusicBean()
            music.musicName = getRandomString(5)

            musicGreenDaoViewModel?.addMusic(music, object : IMusicRoomCallBack {
                override fun onFailed(error: String) {
                    Log.d("Room", "addWord onFailed:$error")
                }

                override fun onSucess() {
                    ToastUtils.showShort("插入成功")
                    subscribeMusicData()
                    room_recycle.smoothScrollToPosition(musicBeanAdapter?.itemCount!!)
                }
            })

        }
        //删
        green_dao_delete_btn.setOnClickListener {
            musicGreenDaoViewModel?.deleteMusic(list?.get(0), object : IMusicRoomCallBack {
                override fun onFailed(error: String) {
                    Log.d("Room", "addWord onFailed")
                }

                override fun onSucess() {
                    subscribeMusicData()
                    ToastUtils.showShort("删除成功")
                    room_recycle.smoothScrollToPosition(musicBeanAdapter?.itemCount!!)
                }
            })
        }
        //改
        green_dao_update_btn.setOnClickListener {
            list?.get(0)?.musicName = "GreenDao"
            musicGreenDaoViewModel?.updateMusic(list?.get(0), object : IMusicRoomCallBack {
                override fun onFailed(error: String) {
                    Log.d("Room", "addWord onFailed")
                }

                override fun onSucess() {
                    subscribeMusicData()
                    ToastUtils.showShort("修改成功")
                    room_recycle.smoothScrollToPosition(musicBeanAdapter?.itemCount!!)
                }
            })
        }
        //删全
        green_dao_delete_all_btn.setOnClickListener {
            musicGreenDaoViewModel?.deleteAllMusic(object : IMusicRoomCallBack {
                override fun onFailed(error: String) {
                    Log.d("Room", "addWord onFailed")
                }

                override fun onSucess() {
                    subscribeMusicData()
                    ToastUtils.showShort("删除全部成功")
                }
            })
        }
        //查
        musicGreenDaoViewModel?.allMusics
    }

    private fun getRandomString(len: Int): String {
        var returnStr: String
        val ch = CharArray(len)
        val rd = Random()
        for (i in 0 until len) {
            ch[i] = ((rd.nextInt(9) + 97).toChar())
        }
        returnStr = "GreenDao"+String(ch)
        return returnStr
    }

    /**
     * 监听数据变化 更新ui
     */
    private fun subscribeMusicData(){
        musicGreenDaoViewModel?.allMusics?.observe(
            this, Observer { musics ->
                //list = musics
                musicBeanAdapter?.setMuiscList(musics)
            }
        )
    }
}
