package com.mz.file.reader.dmy.ui.open;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OpenViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OpenViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("打开文件");
    }

    public LiveData<String> getText() {
        return mText;
    }
}