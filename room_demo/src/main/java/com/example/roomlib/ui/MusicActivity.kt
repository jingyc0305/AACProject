package com.example.roomlib.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.example.roomlib.IMusicRoomCallBack
import com.example.roomlib.R
import com.example.roomlib.db.Music
import kotlinx.android.synthetic.main.activity_main_room.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author: JingYuchun
 * @date: 2019/8/10 13:44
 * @desc:
 */
class MusicActivity : AppCompatActivity() {

    private var musicViewModel: MusicViewModel? = null
    private var list: List<Music>? = ArrayList()
    private var musicAdapter: MusicAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_room)
        musicViewModel = ViewModelProviders.of(this).get(MusicViewModel::class.java)

        subscribeMusicData()
        musicAdapter = MusicAdapter(this, list)

        room_recycle.adapter = musicAdapter
        room_recycle.layoutManager = LinearLayoutManager(this)
        room_recycle.smoothScrollToPosition(0)

        room_add_btn.setOnClickListener {
            var music = Music()
            music?.musicName = getRandomString(10)
            musicViewModel?.addMusic(music, object : IMusicRoomCallBack {
                override fun onFailed() {
                    Log.d("Room", "addWord onFailed")
                }

                override fun onSucess() {
                    room_recycle.smoothScrollToPosition(musicAdapter?.itemCount!!)
                }
            })

        }
        room_delete_btn.setOnClickListener {
            musicViewModel?.deleteMusic(list?.get(0),object : IMusicRoomCallBack {
                override fun onFailed() {
                    Log.d("Room", "addWord onFailed")
                }

                override fun onSucess() {
                    ToastUtils.showShort("删除成功")
                    subscribeMusicData()
                    room_recycle.smoothScrollToPosition(musicAdapter?.itemCount!!)
                }
            })
        }
        room_update_btn.setOnClickListener {
            musicViewModel?.updateMusic(list?.get(0),object : IMusicRoomCallBack {
                override fun onFailed() {
                    Log.d("Room", "addWord onFailed")
                }

                override fun onSucess() {
                    ToastUtils.showShort("修改成功")
                    subscribeMusicData()
                    room_recycle.smoothScrollToPosition(musicAdapter?.itemCount!!)
                }
            })
        }
        room_delete_all_btn.setOnClickListener {
            musicViewModel?.deleteAllMusic(object : IMusicRoomCallBack {
                override fun onFailed() {
                    Log.d("Room", "addWord onFailed")
                }

                override fun onSucess() {
                    subscribeMusicData()
                    ToastUtils.showShort("删除全部成功")
                }
            })
        }
        //加载数据
        musicViewModel?.allMusics
    }

    private fun subscribeMusicData() {
        musicViewModel?.allMusics?.observe(
            this, Observer { musics ->
                list = musics
                musicAdapter?.setMuiscList(musics)
            }
        )
    }

    private fun getRandomString(len: Int): String {
        var returnStr: String
        val ch = CharArray(len)
        val rd = Random()
        for (i in 0 until len) {
            ch[i] = ((rd.nextInt(9) + 97).toChar())
        }
        returnStr = String(ch)
        return returnStr
    }
}
