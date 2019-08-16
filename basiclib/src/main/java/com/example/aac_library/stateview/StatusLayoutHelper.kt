package com.example.aac_library.stateview

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
/**
 * @author: JingYuchun
 * @date: 2019/7/24 19:51
 * @desc:
 */
class StatusLayoutHelper {
    private var contentLayout: View? = null
    private var currentLayout: View? = null
    private var params: LayoutParams? = null
    private var parentLayout: ViewGroup? = null
    private var viewIndex: Int? = null

    constructor(contentLayout: View?) {
        this.contentLayout = contentLayout
        getContentLayoutParams()
    }

    /**
     * 获取contentLayout布局的参数
     */
    private fun getContentLayoutParams() {
        this.params = contentLayout?.layoutParams
        if (contentLayout?.parent != null) {
            this.parentLayout = contentLayout?.parent as ViewGroup
        } else {
            this.parentLayout = contentLayout?.rootView?.findViewById(android.R.id.content) as ViewGroup?
        }
        val count = parentLayout?.childCount
        if (count != null) {
            if (count > 0){
                for (index in 0 until count!!) {
                    if (contentLayout === parentLayout?.getChildAt(index)) {
                        this.viewIndex = index
                        break
                    }
                }
            }
        }
        this.currentLayout = this.contentLayout
    }

    fun showStatusLayout(view: View?): Boolean {
        if (null == view) {
            return false
        }
        if (currentLayout != view) {
            currentLayout = view
            val parent = view.parent as? ViewGroup
            parent?.removeView(view)
            if (parentLayout != null) {
                try {
                    if (parentLayout?.childCount!! == 1){
                        parentLayout?.removeViewAt(0)
                        parentLayout?.addView(view, 0, params)
                    }else{
                        parentLayout?.removeViewAt(viewIndex!!)
                        parentLayout?.addView(view, viewIndex!!, params)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return true
        }
        return false
    }

    fun setContentLayout(): Boolean {
        return showStatusLayout(contentLayout!!)
    }
}