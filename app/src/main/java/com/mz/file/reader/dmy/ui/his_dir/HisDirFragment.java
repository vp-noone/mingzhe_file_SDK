package com.mz.file.reader.dmy.ui.his_dir;

import static com.mz.file.reader.dmy.ui.HistoryManager.saveSearchHistory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mz.file.reader.dmy.databinding.FragmentHistoryDirBinding;
import com.mz.file.reader.dmy.ui.FileAdapter;
import com.mz.file.reader.dmy.ui.HistoryManager;
import com.mz.file.reader.dmy.ui.open.OpenFragment;
import com.mz.file.sdk.FileSDK;
import com.zlylib.fileselectorlib.bean.EssFile;
import com.zlylib.fileselectorlib.utils.Const;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class HisDirFragment extends Fragment {

    private HisDirViewModel HisDirViewModel;
    private FragmentHistoryDirBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HisDirViewModel =
                new ViewModelProvider(this).get(HisDirViewModel.class);

        binding = FragmentHistoryDirBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = binding.hisDir;
        final Fragment fragment = this;

        List<String> list = HistoryManager.getSearchHistory(getContext());
        LinkedHashSet<String> data3 = new LinkedHashSet<String>();
        for (String s : list) {
            data3.add(new File(s).getParentFile().getAbsolutePath());
        }
        String[] str = data3.toArray(new String[data3.size()]);
        FileAdapter adapter = new FileAdapter(str);
        adapter.setOnItemClickListener(new FileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, String path) {
                OpenFragment.listFile(getContext(), fragment, path + File.separator);
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("requestCode", "" + requestCode);
        if (requestCode == 99) {
            if (data != null) {
                FileSDK.initSDK(getContext());
                ArrayList<EssFile> essFileList = data.getParcelableArrayListExtra(Const.EXTRA_RESULT_SELECTION);
                for (EssFile file : essFileList) {
                    FileSDK.openFileDialog(getContext(), new File(file.getAbsolutePath()), (res, ext) -> {
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