package com.mz.file.reader.dmy.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mz.file.reader.dmy.R;
import com.mz.file.reader.dmy.ui.open.OpenFragment;

import java.io.File;
import java.util.LinkedList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private String[] localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;
        public final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.name);
            imageView = view.findViewById(R.id.item_icon);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */

    public FileAdapter(String[] dataSet) {
        localDataSet = dataSet;
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, String path);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.file_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.setText(localDataSet[position]);
        String ext = localDataSet[position].substring(localDataSet[position].lastIndexOf(".") + 1);
        ImageView imageView = viewHolder.imageView;
        switch (ext) {
            case "apk":
                imageView.setImageResource(R.mipmap.apk);
                break;
            case "avi":
                imageView.setImageResource(R.mipmap.avi);
                break;
            case "doc":
            case "docx":
                imageView.setImageResource(R.mipmap.doc);
                break;

            case "mp3":
                imageView.setImageResource(R.mipmap.mp3);
                break;
            case "mp4":
            case "f4v":
                imageView.setImageResource(R.mipmap.movie);
                break;
            case "pdf":
                imageView.setImageResource(R.mipmap.pdf);
                break;
            case "ppt":
            case "pptx":
                imageView.setImageResource(R.mipmap.ppt);
                break;
            case "wav":
                imageView.setImageResource(R.mipmap.wav);
                break;
            case "xls":
            case "xlsx":
                imageView.setImageResource(R.mipmap.xls);
                break;
            case "zip":
                imageView.setImageResource(R.mipmap.zip);
                break;
            case "ext":
            default:
                if (new File(localDataSet[position]).isDirectory()) {
                    imageView.setImageResource(R.mipmap.folder);
                } else {
                    imageView.setImageResource(R.mipmap.documents);
                }
                break;
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(viewHolder.itemView, localDataSet[viewHolder.getAdapterPosition()]);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}