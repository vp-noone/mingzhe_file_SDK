package com.mz.file.reader.dmy.ui.history_file;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryFileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HistoryFileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}