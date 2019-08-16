package com.example.aac_library.stateview

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.example.aac_library.R
import org.w3c.dom.Text

/**
 * @author: JingYuchun
 * @date: 2019/7/24 19:49
 * @desc:
 */
class StatusLayout {
    private var contentLayout: View? = null
    private var loadingLayout: View? = null
    private var emptyLayout: View? = null
    private var errorLayout: View? = null

    @LayoutRes
    private var loadingLayoutID: Int = 0
    @LayoutRes
    private var emptyLayoutID: Int = 0
    @LayoutRes
    private var errorLayoutID: Int = 0

    private var loadingText: String = ""
    private var emptyText: String = ""
    private var errorText: String = ""

    private var emptyClickText: String = ""
    private var errorClickText: String = ""

    @DrawableRes
    private var emptyImgID: Int = 0
    @DrawableRes
    private var errorImgID: Int = 0

    private var loadingTextColorRes: Int = 0
    private var emptyTextColorRes: Int = 0
    private var errorTextColorRes: Int = 0
    private var emptyClickTextColorRes: Int = 0
    private var errorClickTextColorRes: Int = 0

    private var inflater: LayoutInflater? = null
    private var statusLayoutHelper: StatusLayoutHelper? = null
    private var statusClickListener: StatusClickListener? = null

    private fun inflater(@LayoutRes resource: Int): View {
        if (null == inflater) {
            inflater = LayoutInflater.from(contentLayout?.context)
        }
        return inflater!!.inflate(resource, null)
    }

    constructor(builder: Builder) {
        this.contentLayout = builder.contentLayout

        this.loadingLayout = builder.loadingLayout
        this.loadingLayoutID = builder.loadingLayoutID
        this.loadingText = builder.loadingText

        this.emptyLayout = builder.emptyLayout
        this.emptyLayoutID = builder.emptyLayoutID
        this.emptyText = builder.emptyText
        this.emptyImgID = builder.emptyImgID

        this.errorLayout = builder.errorLayout
        this.errorLayoutID = builder.errorLayoutID
        this.errorText = builder.errorText
        this.errorImgID = builder.errorImgID

        this.emptyClickText = builder.emptyClickText
        this.errorClickText = builder.errorClickText

        this.loadingTextColorRes = builder.loadingTextColorRes
        this.emptyTextColorRes = builder.emptyTextColorRes
        this.errorTextColorRes = builder.errorTextColorRes
        this.emptyClickTextColorRes = builder.emptyClickTextColorRes
        this.errorClickTextColorRes = builder.errorClickTextColorRes

        this.statusClickListener = builder.statusClickListener
        this.statusLayoutHelper = StatusLayoutHelper(contentLayout)
    }

    /**
     * 显示内容布局
     */
    fun showContentLayout() {
        statusLayoutHelper?.setContentLayout()
    }

    /**
     * 显示加载布局
     */
    fun showLoadingLayout() {
        createLoadingLayout()
        statusLayoutHelper?.showStatusLayout(loadingLayout!!)
    }

    private fun createLoadingLayout() {
        if (null == loadingLayout) {
            loadingLayout = inflater(loadingLayoutID)
        }
        if (!TextUtils.isEmpty(loadingText)) {
            val loadingTextView = loadingLayout!!.findViewById(R.id.tv_status_loading) as TextView
            loadingTextView?.text = loadingText
        }
    }

    /**
     * 显示空布局
     */
    fun showEmptyLayout() {
        createEmptyLayout()
        statusLayoutHelper?.showStatusLayout(emptyLayout!!)
    }

    @SuppressLint("ResourceType")
    private fun createEmptyLayout() {
        if (null == emptyLayout) {
            emptyLayout = inflater(emptyLayoutID)
        }

        val emptyTextView = emptyLayout!!.findViewById(R.id.tv_status_empty) as TextView

        if (!TextUtils.isEmpty(emptyText)) {
            emptyTextView?.text = emptyText
        }
        emptyTextView.setTextColor(emptyTextColorRes)

        if (statusClickListener == null) { //防止出错
            return
        }

        if (emptyImgID > 0) {
            val emptyImageView = emptyLayout!!.findViewById(R.id.iv_status_empty)as ImageView
            emptyImageView?.setImageResource(emptyImgID)
        }

        val emptyClickView = emptyLayout!!.findViewById(R.id.tv_click_empty) as TextView

        if (!TextUtils.isEmpty(emptyClickText)) {
            emptyClickView.text = emptyClickText
        }
        emptyClickView.setTextColor(emptyClickTextColorRes)

        emptyClickView.setOnClickListener {
            statusClickListener!!.onEmptyClick(it)
        }
    }

    /**
     * 显示错误布局
     */
    fun showErrorLayout() {
        createErrorLayout()
        statusLayoutHelper?.showStatusLayout(errorLayout!!)
    }

    @SuppressLint("ResourceType")
    private fun createErrorLayout() {
        if (null == errorLayout) {
            errorLayout = inflater(errorLayoutID)
        }
        val errorTextView = errorLayout!!.findViewById(R.id.tv_status_error) as TextView
        if (!TextUtils.isEmpty(errorText)) {
            errorTextView?.text = errorText
        }
        errorTextView.setTextColor(errorTextColorRes)
        if (statusClickListener == null) { //防止出错
            return
        }

        if (errorImgID > 0) {
            val emptyImageView = errorLayout!!.findViewById(R.id.iv_status_error) as ImageView
            emptyImageView?.setImageResource(errorImgID)
        }

        val errorClickView = errorLayout!!.findViewById(R.id.tv_click_error) as TextView

        if (!TextUtils.isEmpty(errorClickText)) {
            errorClickView.text = errorClickText
        }
        errorClickView.setTextColor(errorClickTextColorRes)

        errorClickView.setOnClickListener {
            statusClickListener!!.onErrorClick(it)
        }
    }

    class Builder {

        var contentLayout: View? = null
        var loadingLayout: View? = null
        var emptyLayout: View? = null
        var errorLayout: View? = null

        @LayoutRes
        var loadingLayoutID: Int = 0
        @LayoutRes
        var emptyLayoutID: Int = 0
        @LayoutRes
        var errorLayoutID: Int = 0

        var loadingText: String = ""
        var emptyText: String = ""
        var errorText: String = ""

        var emptyClickText: String = ""
        var errorClickText: String = ""

        @DrawableRes
        var emptyImgID: Int = 0
        @DrawableRes
        var errorImgID: Int = 0

        var loadingTextColorRes: Int = 0
        var emptyTextColorRes: Int = 0
        var errorTextColorRes: Int = 0
        var emptyClickTextColorRes: Int = 0
        var errorClickTextColorRes: Int = 0

        var statusClickListener: StatusClickListener? = null

        constructor(contentLayout: View) {
            this.contentLayout = contentLayout
            this.loadingLayoutID = R.layout.layout_loading
            this.emptyLayoutID = R.layout.layout_empty
            this.errorLayoutID = R.layout.layout_error
            this.emptyTextColorRes = R.color.title
            this.errorTextColorRes = R.color.title
            this.emptyClickTextColorRes = R.color.click
            this.errorClickTextColorRes = R.color.click
        }

        fun build(): StatusLayout {
            return StatusLayout(this)
        }

        fun setOnLoadingLayout(@LayoutRes loadingLayoutID: Int): Builder {
            this.loadingLayoutID = loadingLayoutID
            return this
        }

        fun setOnLoadingLayout(loadingLayout: View): Builder {
            this.loadingLayout = loadingLayout
            return this
        }

        fun setOnLoadingText(loadingText: String): Builder {
            this.loadingText = loadingText
            return this
        }

        fun setOnLoadingtext(@StringRes loadingTextStringRes: Int): Builder {
            this.loadingText = contentLayout?.context?.resources?.getString(loadingTextStringRes)!!
            return this
        }

        fun setOnLoadingTextColor(loadingTextColorRes: Int): Builder {
            this.loadingTextColorRes = loadingTextColorRes
            return this
        }

        fun setOnEmptyLayout(@LayoutRes emptyLayoutID: Int): Builder {
            this.emptyLayoutID = emptyLayoutID
            return this
        }

        fun setOnEmptyLayout(emptyLayout: View): Builder {
            this.emptyLayout = emptyLayout
            return this
        }

        fun setOnEmptyImg(@DrawableRes emptyImgID: Int): Builder {
            this.emptyImgID = emptyImgID
            return this
        }

        fun setOnEmptyText(emptyText: String): Builder {
            this.emptyText = emptyText
            return this
        }

        fun setOnEmptyText(@StringRes emptyTextStringRes: Int): Builder {
            this.emptyText = contentLayout?.context?.resources?.getString(emptyTextStringRes)!!
            return this
        }

        fun setOnEmptyClickText(emptyClickText: String): Builder {
            this.emptyClickText = emptyClickText
            return this
        }

        fun setOnEmptyClickText(@StringRes emptyClickTextStringRes: Int): Builder {
            this.emptyClickText = contentLayout?.context?.resources?.getString(emptyClickTextStringRes)!!
            return this
        }

        fun setOnEmptyTextColor(emptyTextStringRes: Int): Builder {
            this.emptyTextColorRes = emptyTextStringRes
            return this
        }

        fun setOnEmptyClickTextColor(emptyClickTextColorRes: Int): Builder {
            this.emptyClickTextColorRes = emptyClickTextColorRes
            return this
        }

        fun setOnErrorLayout(@LayoutRes errorLayoutID: Int): Builder {
            this.errorLayoutID = errorLayoutID
            return this
        }

        fun setOnErrorLayout(errorLayout: View): Builder {
            this.errorLayout = errorLayout
            return this
        }

        fun setOnErrorImg(@DrawableRes errorImgID: Int): Builder {
            this.errorImgID = errorImgID
            return this
        }

        fun setOnErrorText(errorText: String): Builder {
            this.errorText = errorText
            return this
        }

        fun setOnErrorText(@StringRes errorTextStringRes: Int): Builder {
            this.errorText = contentLayout?.context?.resources?.getString(errorTextStringRes)!!
            return this
        }

        fun setOnErrorClickText(errorClickText: String): Builder {
            this.errorClickText = errorClickText
            return this
        }

        fun setOnErrorClickText(@StringRes errorClickTextStringRes: Int): Builder {
            this.errorClickText = contentLayout?.context?.resources?.getString(errorClickTextStringRes)!!
            return this
        }

        fun setOnErrorTextColor(errorTextStringRes: Int): Builder {
            this.errorTextColorRes = errorTextStringRes
            return this
        }

        fun setOnErrorClickTextColor(errorClickTextColorRes: Int): Builder {
            this.errorClickTextColorRes = errorClickTextColorRes
            return this
        }

        fun setOnStatusClickListener(listener: StatusClickListener): Builder {
            this.statusClickListener = listener
            return this
        }
    }
}