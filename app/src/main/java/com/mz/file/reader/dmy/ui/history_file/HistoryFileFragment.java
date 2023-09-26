package com.mz.file.reader.dmy.ui.history_file;

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
import com.mz.file.reader.dmy.databinding.FragmentHistoryFileBinding;
import com.mz.file.reader.dmy.ui.FileAdapter;
import com.mz.file.reader.dmy.ui.HistoryManager;
import com.mz.file.reader.dmy.ui.open.OpenFragment;
import com.mz.file.sdk.FileSDK;
import com.zlylib.fileselectorlib.FileSelector;
import com.zlylib.fileselectorlib.bean.EssFile;
import com.zlylib.fileselectorlib.utils.Const;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class HistoryFileFragment extends Fragment {

    private HistoryFileViewModel historyFileViewModel;
    private FragmentHistoryFileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyFileViewModel =
                new ViewModelProvider(this).get(HistoryFileViewModel.class);

        binding = FragmentHistoryFileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.hisFile;
        List<String> list = HistoryManager.getSearchHistory(getContext());

        String[] str = list.toArray(new String[list.size()]);
        FileAdapter adapter = new FileAdapter(str);
        adapter.setOnItemClickListener(new FileAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, String path) {
                FileSDK.initSDK(getContext());
                FileSDK.openFileDialog(getContext(), new File(path), (res, ext) -> {
                    if (res == -1) {
                        Snackbar.make(getView(), "open fail, reason : " + ext + "," + res, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}