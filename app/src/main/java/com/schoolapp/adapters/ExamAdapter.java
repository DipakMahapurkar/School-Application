package com.schoolapp.adapters;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.schoolapp.R;
import com.schoolapp.activity.MarksheetActivity;
import com.schoolapp.models.ExamModel;

import java.util.List;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {

    private static final String TAG = "Exam Adapter";
    private Context mContext;
    private List<ExamModel> mExamList;

    public ExamAdapter(Context context, List<ExamModel> examList) {
        this.mContext = context;
        this.mExamList = examList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView examName;
        CardView examCardView;

        ViewHolder(View view) {
            super(view);
            examName = (TextView) view.findViewById(R.id.subject_name_txt);
            examCardView = (CardView) view.findViewById(R.id.exam_card_view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_exams, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.examName.setText(mExamList.get(position).getExamName());

        holder.examCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Exam Id = " + mExamList.get(position).getExamId() + ", Exam Name = " + mExamList.get(position).getExamName());
                ((MarksheetActivity)mContext).showMarksheetFragment(mExamList.get(position).getExamId(), mExamList.get(position).getExamName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mExamList.size();
    }
}
