package com.example.aac_library.widget;


import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.blankj.utilcode.util.BarUtils;
import com.example.aac_library.R;

import static android.view.Gravity.CENTER;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


/**
 * @author: JingYuchun
 * @date: 2019/7/25 17:36
 * @desc: 统一对话框
 */
public abstract class BaseDialog extends DialogFragment {

    private int mWidth = WRAP_CONTENT;
    private int mHeight = WRAP_CONTENT;
    private int mGravity = CENTER;
    private int mOffsetX = 0;
    private int mOffsetY = 0;
    private int mAnimation = R.style.DialogBaseAnimation;
    private boolean isFull = false;
    private boolean isHideStatusBar = false;
    protected DialogResultListener mDialogResultListener;
    protected DialogDismissListener mDialogDismissListener;
    protected DialogOnCloseListener mDialogOnCloseListener;
    protected DialogOnStartListener  mDialogOnStartListener;
    public BaseDialog setmDialogDismissListener(DialogDismissListener mDialogDismissListener) {
        this.mDialogDismissListener = mDialogDismissListener;
        return this;
    }

    public BaseDialog setmDialogResultListener(DialogResultListener mDialogResultListener) {
        this.mDialogResultListener = mDialogResultListener;
        return this;
    }

    public BaseDialog setmDialogCloseListener(DialogOnCloseListener mDialogOnCloseListener) {
        this.mDialogOnCloseListener = mDialogOnCloseListener;
        return this;
    }

    public BaseDialog setmDialogOnStartListener(DialogOnStartListener mDialogOnStartListener) {
        this.mDialogOnStartListener = mDialogOnStartListener;
        return this;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mDialogOnStartListener != null) {
            mDialogOnStartListener.start(this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setStyle();
        return setView(inflater, container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWidth = getArguments().getInt("width");
            mHeight = getArguments().getInt("height");
            mAnimation = getArguments().getInt("animation");
            mOffsetX = getArguments().getInt("offset_x");
            mOffsetY = getArguments().getInt("offset_y");
            mGravity = getArguments().getInt("gravity");
            isFull = getArguments().getBoolean("is_full");
            isHideStatusBar = getArguments().getBoolean("is_hide");
        }
        if (isFull)
            setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mDialogDismissListener != null) {
            mDialogDismissListener.dismiss(this);
        }
    }
    protected static Bundle getArgumentBundle(Builder builder) {
        Bundle bundle = new Bundle();
        bundle.putInt("width", builder.mWidth);
        bundle.putInt("height", builder.mHeight);
        bundle.putInt("offset_x", builder.mOffsetX);
        bundle.putInt("offset_y", builder.mOffsetY);
        bundle.putInt("gravity", builder.mGravity);
        bundle.putInt("animation", builder.mAnimation);
        bundle.putBoolean("is_full", builder.isFull);
        bundle.putBoolean("is_hide", builder.isHideStatusBar);
        return bundle;
    }

    /**
     * 设置统一样式
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setStyle() {
        //获取Window
        Window window = getDialog().getWindow();
        //无标题
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        //外部点击不可取消
        getDialog().setCanceledOnTouchOutside(false);
        // 透明背景
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置宽高
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = mWidth;
        wlp.height = mHeight;
        //设置对齐方式
        wlp.gravity = mGravity;
        //设置偏移量
        wlp.x = mOffsetX;
        wlp.y = mOffsetY;
        //设置动画
        window.setWindowAnimations(mAnimation);
        window.setAttributes(wlp);

        if(isHideStatusBar){
            //隐藏状态栏
            hideStatusBar();
            //清除焦点
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        }
    }
    protected abstract View setView(LayoutInflater inflater, @Nullable ViewGroup container);

    public static abstract class Builder<T extends Builder, D extends BaseDialog> {
        private int mWidth = WRAP_CONTENT;
        private int mHeight = WRAP_CONTENT;
        private int mGravity = CENTER;
        private int mOffsetX = 0;
        private int mOffsetY = 0;
        private int mAnimation = R.style.DialogBaseAnimation;
        private boolean isFull = false;
        private boolean isHideStatusBar = false;
        public T setWH(int mWidth, int mHeight) {
            this.mWidth = mWidth;
            this.mHeight = mHeight;
            return (T) this;
        }

        public T setGravity(int mGravity) {
            this.mGravity = mGravity;
            return (T) this;
        }

        public T setOffset(int mOffsetX, int mOffsetY) {
            this.mOffsetX = mOffsetX;
            this.mGravity = mOffsetY;
            return (T) this;
        }

        public T setAnimation(int mAnimation) {
            this.mAnimation = mAnimation;
            return (T) this;
        }

        public T isFullScreen(boolean isFull){
            this.isFull = isFull;
            return (T) this;
        }
        public T isHideStatusBar(boolean isHideStatusBar){
            this.isHideStatusBar = isHideStatusBar;
            return (T) this;
        }
        public abstract D build();

        public void reset() {
            this.mWidth = WRAP_CONTENT;
            this.mHeight = WRAP_CONTENT;
            this.mGravity = CENTER;
            this.mOffsetX = 0;
            this.mOffsetY = 0;
        }
    }

    /**
     * 隐藏状态栏
     */
    private void hideStatusBar() {
        BarUtils.setStatusBarVisibility(getDialog().getWindow(),false);
    }

    public interface DialogDismissListener {
        void dismiss(DialogFragment dialogFragment);
    }

    public interface DialogOnCloseListener {
        void close(DialogFragment dialogFragment);
    }

    public interface DialogResultListener<T> {
        void result(T result,int pos, DialogFragment dialogFragment);
    }
    public interface DialogOnStartListener {
        void start(DialogFragment dialogFragment);
    }

}
