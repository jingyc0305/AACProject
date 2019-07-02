package com.example.aacdemo.asyntask

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * @author: JingYuchun
 * @date: 2019/6/26 16:20
 * @desc: 异步任务asyntask学习
 */
class AsynActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AsynTaskUI().setContentView(this@AsynActivity)

    }

    class AsynTaskUI : AnkoComponent<AsynActivity>{
        private var stateView:TextView? = null
        private var progressBar:ProgressBar? = null
        private val progress : Int = 0
        private var task:DownloadTask? = null
        override fun createView(ui: AnkoContext<AsynActivity>) = with(ui){
            verticalLayout {
                padding = dip(48)
                button("开始下载") {
                    onClick {
                        Log.d("AsynActivity","开始下载")
//                        task = DownloadTask(stateView,progressBar)
//                        task?.execute()
                       doAsync {
                           var i = 0
                           Log.d("AsynActivity","doInBackground")
                           try {
                               while (i<100){
                                   Log.d("AsynActivity", "i=$i")
                                   i++
                                   Thread.sleep(50)
                                   stateView?.text = "正在下载..."+i+"%"
                                   progressBar?.progress = i
                               }
                               stateView?.text = "下载完成"
                           } catch (e: Exception) {
                               e.printStackTrace()
                           }
                       }
                    }
                }
                stateView = textView("下载中$progress"){
                }
                progressBar = horizontalProgressBar {

                    progress = 0
                    max = 100
                }

                button("取消下载"){
                    Log.d("AsynActivity","取消下载")
                    onClick { task?.cancel(true) }
                }
            }
        }

    }

    class DownloadTask(textView: TextView?,progressBar: ProgressBar?) : AsyncTask<String, Int, String>() {
        private var textView:TextView? = null
        private var progressBar:ProgressBar? = null
        init {
            this.textView = textView
            this.progressBar = progressBar
        }
        override fun doInBackground(vararg p0: String?): String {
            var i = 0
            Log.d("AsynActivity","doInBackground")
            try {
                while (i<=100){
                    Log.d("AsynActivity", "i=$i")
                    i++
                    publishProgress(i)
                    Thread.sleep(100)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""

        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            textView?.text = "正在下载..."+values?.get(0).toString()+"%"
            progressBar?.progress = values?.get(0)?:0
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            textView?.text = "下载完成"
        }

        override fun onCancelled() {
            super.onCancelled()
            textView?.text = "下载已取消"
        }

    }
}