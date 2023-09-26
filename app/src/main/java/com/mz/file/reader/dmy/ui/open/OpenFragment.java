package com.mz.file.reader.dmy.ui.open;

import static com.mz.file.reader.dmy.ui.HistoryManager.saveSearchHistory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import com.mz.file.reader.dmy.databinding.FragmentOpenBinding;
import com.mz.file.sdk.FileSDK;
import com.zlylib.fileselectorlib.FileSelector;
import com.zlylib.fileselectorlib.bean.EssFile;
import com.zlylib.fileselectorlib.utils.Const;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class OpenFragment extends Fragment {

    private OpenViewModel openViewModel;
    private FragmentOpenBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        openViewModel =
                new ViewModelProvider(this).get(OpenViewModel.class);

        binding = FragmentOpenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button button = binding.textOpen;
        verifyStoragePermissions(getActivity());
        openViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                button.setText(s);
            }
        });
        final Fragment fragment = this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listFile(getContext(), fragment, Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator);
            }
        });
        return root;
    }

    public static void listFile(Context context, Fragment fragment, String path) {
        FileSDK.initSDK(context);
        boolean a = FileSDK.isInitSuccess();
        FileSelector.from(fragment).isSingle().setTargetPath(path).requestCode(99).start();
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {

            "android.permission.READ_EXTERNAL_STORAGE",

            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static void verifyStoragePermissions(Activity activity) {
        try {
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("requestCode", "" + requestCode);
        if (requestCode == 99) {
            if (data != null) {
                ArrayList<EssFile> essFileList = data.getParcelableArrayListExtra(Const.EXTRA_RESULT_SELECTION);
                for (EssFile file : essFileList) {
                    FileSDK.openFileDialog(getContext(), new File(file.getAbsolutePath()), (res, ext) -> {
                        if (ext.equals("open success")) {
                            saveSearchHistory(getContext(), file.getAbsolutePath());
                        }
                        if (res == -1) {
                            Snackbar.make(getView(), "open fail, reason : " + ext + "," + res, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}