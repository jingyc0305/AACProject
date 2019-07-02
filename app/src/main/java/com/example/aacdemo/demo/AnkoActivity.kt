package com.example.aacdemo.demo

import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.fragmentTabHost

class AnkoActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        info { intent.getStringExtra("param") }
        MainUI().setContentView(this@AnkoActivity)

    }

    class MainUI : AnkoComponent<AnkoActivity> {

        override fun createView(ui: AnkoContext<AnkoActivity>) = with(ui) {
            verticalLayout {
                padding = dip(30)

                val editText = editText()
                editText.hint = "请输入name"
                button("anko") {
                    onClick { toast("${editText.text}是用anko创建的页面效果!") }
                    textSize = 26f
                }.lparams(width = wrapContent) {
                    horizontalMargin = dip(10)
                    horizontalGravity = Gravity.CENTER
                }
                button("dialog") {
                    onClick {
                        alert("提示", "使用anko创建的dialog") {
                            positiveButton("知道了") {
                            }
                            negativeButton("什么鬼") {
                            }
                        }.show()
                    }
                }
                button("dialog_with_list") {
                    onClick {
                        val flowers = listOf("Java", "Kotlin", "Anko", "Flutter")
                        selector("选择你最喜欢的语言", flowers) { _, i ->
                            toast("你选择了${flowers[i]}")
                        }
                    }
                }
                button("loading_dialog") {
                    onClick {
                        indeterminateProgressDialog("加载中...")
                    }
                }
                button("asynTask") {
                    onClick {
                        doAsync {
                            uiThread {
                                toast("异步任务完成 更新ui")
                            }
                        }
                    }

                }

            }.applyRecursively { view ->
                when (view) {
                    is EditText -> view.textSize = 20f
                }
            }
        }
    }
}