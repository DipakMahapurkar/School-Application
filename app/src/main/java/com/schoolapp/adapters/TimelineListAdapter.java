package com.schoolapp.adapters;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.master.glideimageview.GlideImageView;
import com.schoolapp.R;
import com.schoolapp.models.TimelineListModel;

import java.util.List;

import static com.schoolapp.utils.Utils.changeDateFormats;

public class TimelineListAdapter extends RecyclerView.Adapter<TimelineListAdapter.ViewHolder> {

    private List<TimelineListModel> mTimelineList;
    private Context mContext;

    public TimelineListAdapter(Context context, List<TimelineListModel> timelineList) {
        this.mTimelineList = timelineList;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView profileName, timelineDate, timelineDescription;
        GlideImageView timelineImageView;


        ViewHolder(View v) {
            super(v);
            profileName = (TextView) v.findViewById(R.id.idTeacherNameTextView);
            timelineDate = (TextView) v.findViewById(R.id.idTimelineDateTextView);
            timelineImageView = (GlideImageView) v.findViewById(R.id.idTimelineImageView);
            timelineDescription = (TextView) v.findViewById(R.id.idTimelineDescriptionTextView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_timeline_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.profileName.setText(mTimelineList.get(position).getTeacherName());
        holder.timelineDate.setText(changeDateFormats(mTimelineList.get(position).getCreatedAt()));
        holder.timelineImageView.loadImageUrl(mTimelineList.get(position).getTimelineImage());
        holder.timelineDescription.setText(mTimelineList.get(position).getTimelineText());
    }

    @Override
    public int getItemCount() {
        return mTimelineList.size();
    }
}
