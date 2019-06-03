package com.example.aacdemo.base.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.example.aacdemo.base.interf.IView;
import com.example.aacdemo.base.interf.IViewModelAction;
import com.example.aacdemo.base.event.BaseActionEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements IView {
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutResId());
        initViewModelEvent();
    }



    private void initViewModelEvent(){
        List<ViewModel> viewModels  =initViewModels();
        if (viewModels != null && viewModels.size()>0) {
            observeEvent(viewModels);
        }else {
            ViewModel viewModel = initViewModel();
            if (viewModel != null) {
                List<ViewModel> modelList = new ArrayList<>();
                modelList.add(viewModel);
                observeEvent(modelList);
            }
        }

    }

    private void observeEvent(List<ViewModel> viewModels){

        for (ViewModel viewModel : viewModels) {
            if (viewModel instanceof IViewModelAction){
                IViewModelAction iViewModelAction = (IViewModelAction) viewModel;
                iViewModelAction.getActionLiveData().observe(this, baseActionEvent -> {
                    switch (baseActionEvent.getAction()){
                        case BaseActionEvent.SHOW_LOADING_DIALOG:
                            startLoading(baseActionEvent.getMessage());
                            break;
                        case BaseActionEvent.DISMISS_LOADING_DIALOG:
                            dismissLoading();
                            break;
                        case BaseActionEvent.SHOW_TOAST:
                            showToast(baseActionEvent.getMessage());
                            break;
                        case BaseActionEvent.FINISH:
                            break;
                        case BaseActionEvent.FINISH_WITH_RESULT_OK:
                            finishWithResultOk();
                            break;
                    }
                });
            }
        }
    }

    protected abstract ViewModel initViewModel();

    protected abstract int initLayoutResId();

    protected abstract void initView();


    private BaseActivity getContext(){
        return this;
    }
    protected List<ViewModel>  initViewModels(){
        return null;
    }


    @Override
    public void startLoading() {
        startLoading(null);
    }

    @Override
    public void startLoading(String msg) {

        if (progressDialog != null) {
            progressDialog.setTitle(msg);
            progressDialog.show();
        }else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public void dismissLoading() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

    @Override
    public void showToast(String msg) {

        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void finishWithResultOk() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoading();
    }
}
