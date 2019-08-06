package com.example.aacdemo.wanandroid.home.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.blankj.utilcode.util.ToastUtils
import com.example.aac_library.base.BaseViewModel
import com.example.aac_library.base.view.BaseFragment
import com.example.aacdemo.R
import com.example.aacdemo.demo.TimerViewModel
import com.example.business_library.bean.MusicInfo
import com.example.business_library.bean.MusicState
import com.example.business_library.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.frag_home.*
import kotlinx.android.synthetic.main.frag_nav.*


/**
 * @author: JingYuchun
 * @date: 2019/6/27 15:04
 * @desc:
 */
class NavFragment : BaseFragment() {
    var homeViewModel: HomeViewModel? = null
    var musicInfo: MusicInfo? = null
    var currMusicProgress : Int = 0
    private lateinit var timerViewModel: TimerViewModel
    private var pauseTime:Long = 0
    override fun initViewModel(): BaseViewModel? {
        homeViewModel = getViewModelForFragment(this, HomeViewModel::class.java)
        return homeViewModel
    }

    override fun initLayoutResId(): Int {
        return R.layout.frag_nav
    }

    override fun initView() {
        //定时器
        subscribTimer()
        //事件监听
        play_btn.setOnClickListener {
            //播放状态控制
            when (homeViewModel?.currMusicState) {
                MusicState.PREPARE -> {
                    play_btn.setImageResource(android.R.drawable.ic_media_pause)
                    //播放控制
                    homeViewModel?.playBTMusic()
                    homeViewModel?.setMusicState(MusicState.PLAYING)
                    ToastUtils.showShort("开始播放")
                    showMusicInfo()
                    timerViewModel?.timerStart()
                }
                MusicState.PLAYING -> {
                    play_btn.setImageResource(android.R.drawable.ic_media_play)
                    homeViewModel?.setMusicState(MusicState.PAUSE)
                    homeViewModel?.pauseMusic()
                    pauseTime =  timerViewModel.timerPause()
                    ToastUtils.showShort("暂停")
                }
                MusicState.ERROR -> {
                    homeViewModel?.pauseMusic()
                    homeViewModel?.setMusicState(MusicState.PAUSE)
                    ToastUtils.showShort("暂停")
                    play_btn.setImageResource(android.R.drawable.ic_media_play)
                    pauseTime =  timerViewModel.timerPause()
                }
                MusicState.COMPLETE -> {
                    homeViewModel?.pauseMusic()
                    homeViewModel?.setMusicState(MusicState.PAUSE)
                    ToastUtils.showShort("暂停")
                    play_btn.setImageResource(android.R.drawable.ic_media_play)
                    pauseTime =  timerViewModel.timerPause()
                }
                MusicState.PAUSE -> {
                    play_btn.setImageResource(android.R.drawable.ic_media_pause)
                    homeViewModel?.playBTMusic()
                    homeViewModel?.setMusicState(MusicState.PLAYING)
                    ToastUtils.showShort("开始播放")
                    showMusicInfo()
                    timerViewModel?.timerStart()
                }
            }

        }
        play_next.setOnClickListener {
            currMusicProgress = 0
            homeViewModel?.playNext()
            showMusicInfo()
            timerViewModel.timerCancle()
            timerViewModel.timerStart()
            homeViewModel?.setMusicState(MusicState.PLAYING)
        }
        play_pre.setOnClickListener {
            currMusicProgress = 0
            homeViewModel?.playPre()
            showMusicInfo()
            timerViewModel.timerCancle()
            timerViewModel.timerStart()
            homeViewModel?.setMusicState(MusicState.PLAYING)
        }
    }

    override fun initData() {
        startLoading()
        android.os.Handler().postDelayed({
            dismissLoading()
        }, 2000)

        //获取音乐信息
        musicInfo = homeViewModel?.getMusicInfo()
        //初始化播放准备状态
        homeViewModel?.currMusicState = MusicState.PREPARE

    }

    override fun initDataBinding() {
    }
    private fun subscribTimer() {
        timerViewModel = ViewModelProviders.of(this).get(TimerViewModel::class.java)
        timerViewModel.elapsedTime.observe(
            this,
            Observer {
                    time ->
                if (time>=100){
                    timerViewModel.timerCancle()
                    playComplete()
                }
                music_progressBar.progress = time.toInt()
                music_play_time.text = "正在播放 "+time.toInt()
            }
        )
    }
    private fun showMusicInfo(){
        music_name_tv.text = musicInfo?.musicName
        music_lrc_tv.text = musicInfo?.musicLrc
    }
    private fun playComplete(){
        music_name_tv.text = "播放已结束"
        music_lrc_tv.text = ""
    }
}