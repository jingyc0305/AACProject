package com.example.aacdemo.wanandroid.nav;

import androidx.lifecycle.ViewModelProviders;
import com.example.aac_library.base.BaseViewModel;
import com.example.aac_library.base.view.BaseActivity;
import org.jetbrains.annotations.Nullable;

/**
 * @author: JingYuchun
 * @date: 2019/7/26 19:16
 * @desc:
 */
public class NavActivity extends BaseActivity {
    private NavViewModel navViewModel;
    @Override
    protected int initLayoutResId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initDataBinding() {

    }

    @Nullable
    @Override
    protected BaseViewModel initViewModel() {
        navViewModel = ViewModelProviders.of(this).get(NavViewModel.class);
        navViewModel.setLifecycleOwner(this);
        return navViewModel;
    }
    @Override
    protected void register() {

    }

    @Override
    protected void unRegister() {

    }
}
