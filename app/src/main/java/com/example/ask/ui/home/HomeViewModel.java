package com.example.ask.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Ask App为Android平台脆弱性分析系统漏洞利用子程序");
    }

    public LiveData<String> getText() {
        return mText;
    }
}