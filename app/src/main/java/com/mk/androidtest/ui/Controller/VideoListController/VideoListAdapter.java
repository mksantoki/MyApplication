package com.mk.androidtest.ui.Controller.VideoListController;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mk.androidtest.Models.Data;
import com.mk.androidtest.R;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int VIEW_ITEM = 1;
    public static final int VIEW_PROG = 0;

    private final OnItemClickListener listener;
    private List<Data> data;
    private Context context;

    public VideoListAdapter(Context context, List<Data> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
        this.context = context;
    }

    public void setData(List<Data> data, int pagenumber) {
        if (pagenumber == 0) {
            this.data = data;
            notifyDataSetChanged();
        } else {
            this.data.addAll(data);
            notifyItemInserted(this.data.size() - data.size());
        }
    }

    public void addLoading() {
        if ((data.size() % 20) == 0) {
            //add null , so the adapter will check view_type and show progress bar at bottom
            data.add(null);
            //adapter.addLoader();
            notifyItemInserted(data.size() - 1);
        }
    }

    public void removeAllData() {
        data.clear();
        notifyDataSetChanged();
    }

    public void removeLoading() {
        try {
            data.remove(data.size() - 1);
            notifyItemRemoved(data.size() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videolist, null);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            return new ViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false);
            return new ProgressViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.click(data.get(position), listener);
            String images = data.get(position).getImages().getFixed_height_small_still().getUrl();

            Glide.with(context)
                    .load(images)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true).placeholder(R.mipmap.iv_holder)
                    .into(viewHolder.background);
            //TODO MK 9/30/2017 ==> NOTE :- to play gif
        } else {
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public interface OnItemClickListener {
        void onClick(Data Item, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView background;

        public ViewHolder(View itemView) {
            super(itemView);
            background = (ImageView) itemView.findViewById(R.id.image);

        }


        public void click(final Data data, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(data, getAdapterPosition());
                }
            });
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(final View itemView) {
            super(itemView);
        }

    }


    @Override
    public int getItemViewType(int position) {
        if (data.get(position) != null) {
            return VIEW_ITEM;
        } else {
            return VIEW_PROG;
        }
    }
}
