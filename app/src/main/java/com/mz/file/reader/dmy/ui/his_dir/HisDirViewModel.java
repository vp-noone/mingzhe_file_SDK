package com.mz.file.reader.dmy.ui.his_dir;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HisDirViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HisDirViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}