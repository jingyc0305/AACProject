package com.example.aacdemo.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import com.example.aac_library.base.view.BaseActivity;
import com.example.aac_library.base.BaseViewModel;
import com.example.aacdemo.R;

public class NewssActivity extends BaseActivity {
    private NewsViewModel mNewsViewModel;

    @Override
    protected int initLayoutResId() {
        return R.layout.activity_newss;
    }

    @Override
    protected void initDataBinding() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected BaseViewModel initViewModel() {
        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        mNewsViewModel.setLifecycleOwner(this);
        return mNewsViewModel;
    }
    @Override
    protected void register() {

    }

    @Override
    protected void unRegister() {

    }
}
