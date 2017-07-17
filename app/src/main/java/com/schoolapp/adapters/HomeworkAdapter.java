package com.schoolapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.schoolapp.R;
import com.schoolapp.models.HomeworkModel;

import java.util.List;


public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder> {


    Context mContext;
    List<HomeworkModel> mHomeworkList;

    public HomeworkAdapter(Context context, List<HomeworkModel> homeworkList) {
        this.mContext = context;
        this.mHomeworkList = homeworkList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView homeworkSubject, homeworkDescription, homeworkDate;

        ViewHolder(View view) {
            super(view);
            homeworkSubject = (TextView) view.findViewById(R.id.homework_subject_txt);
            homeworkDate = (TextView) view.findViewById(R.id.homework_date_txt);
            homeworkDescription = (TextView) view.findViewById(R.id.homework_description_txt);
        }
    }

    @Override
    public HomeworkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_homework, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.homeworkSubject.setText(mHomeworkList.get(position).getSubjectName());
        holder.homeworkDate.setText(mHomeworkList.get(position).getCreatedAt());
        holder.homeworkDescription.setText(mHomeworkList.get(position).getHomeworkDescription());
    }

    @Override
    public int getItemCount() {
        return mHomeworkList.size();
    }
}
