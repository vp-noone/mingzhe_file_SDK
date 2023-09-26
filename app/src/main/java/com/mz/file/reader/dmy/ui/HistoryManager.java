package com.mz.file.reader.dmy.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.zlylib.fileselectorlib.FileSelector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryManager {
    private final static String PREFERENCE_NAME = "superservice_ly";
    private final static String SEARCH_HISTORY="linya_history";
    // 保存搜索记录
    public static void saveSearchHistory(Context context ,String inputText) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (TextUtils.isEmpty(inputText)) {
            return;
        }
        String longHistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longHistory.split(",");
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory));
        if (historyList.size() == 1 && historyList.get(0).equals("")) {
            historyList.clear();
        }
        SharedPreferences.Editor editor = sp.edit();
        if (historyList.size() > 0) {
            for (int i = 0; i < historyList.size(); i++) {
                if (inputText.equals(historyList.get(i))) {
                    historyList.remove(i);
                    break;
                }
            }
            historyList.add(0, inputText);
            if (historyList.size() > 8) {
                historyList.remove(historyList.size() - 1);
            }
            //逗号拼接
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < historyList.size(); i++) {
                sb.append(historyList.get(i) + ",");
            }
            editor.putString(SEARCH_HISTORY, sb.toString());
            editor.commit();
        } else {
            editor.putString(SEARCH_HISTORY, inputText + ",");
            editor.commit();
        }
    }
    public static List<String> getSearchHistory(Context context){
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String longHistory =sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longHistory.split(",");
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory));
        if (historyList.size() == 1 && historyList.get(0).equals("")) {
            historyList.clear();
        }
        return historyList;
    }
}
