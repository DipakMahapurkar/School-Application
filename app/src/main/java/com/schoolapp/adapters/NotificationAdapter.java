package com.schoolapp.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.amulyakhare.textdrawable.TextDrawable;
//import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.schoolapp.R;
import com.schoolapp.models.NoticeModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private static final String TAG = "Notification Adapter";
    private Context mContext;
    private List<NoticeModel> mNoticeList;

    private String letter;
    private ColorGenerator generator = ColorGenerator.MATERIAL;

    public NotificationAdapter(Context context, List<NoticeModel> noticeList) {
        this.mContext = context;
        mNoticeList = noticeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_notification, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the first letter of list item
        letter = String.valueOf(mNoticeList.get(position).getNoticeTitle().charAt(0));
        //        Create a new TextDrawable for our image's background
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(letter, generator.getRandomColor());

        holder.letterImageView.setImageDrawable(drawable);

        holder.noticeTitle.setText(mNoticeList.get(position).getNoticeTitle());
        holder.noticeDescription.setText(mNoticeList.get(position).getNoticeDescription());
        holder.noticeDate.setText(mNoticeList.get(position).getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return mNoticeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView letterImageView;
        TextView noticeTitle, noticeDescription, noticeDate;

        ViewHolder(View view) {
            super(view);
            letterImageView = (ImageView) view.findViewById(R.id.letter_image_view);
            noticeTitle = (TextView) view.findViewById(R.id.notice_title_txt);
            noticeDescription = (TextView) view.findViewById(R.id.notice_description_txt);
            noticeDate = (TextView) view.findViewById(R.id.notice_date_txt);
        }
    }
}
