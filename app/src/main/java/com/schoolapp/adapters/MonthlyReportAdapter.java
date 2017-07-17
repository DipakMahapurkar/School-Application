package com.schoolapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.schoolapp.R;
import com.schoolapp.models.MonthlyReportModel;
import com.schoolapp.utils.Utils;

import java.util.List;

public class MonthlyReportAdapter extends RecyclerView.Adapter<MonthlyReportAdapter.ViewHolder>{

    private Context mContext;
    private List<MonthlyReportModel> mMonthlyReportList;

    public MonthlyReportAdapter(Context context, List<MonthlyReportModel> monthlyReportList) {
        this.mContext = context;
        this.mMonthlyReportList = monthlyReportList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_monthly_report, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.monthName.setText(mMonthlyReportList.get(position).getMothName());
        holder.gradeName.setText(mMonthlyReportList.get(position).getGradeName());

        holder.monthCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showSnackbar(mContext, view, "Month = " + mMonthlyReportList.get(position).getMothName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMonthlyReportList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView monthName, gradeName;
        CardView monthCardView;

        ViewHolder(View view) {
            super(view);
            monthCardView = (CardView) view.findViewById(R.id.exam_card_view);
            monthName = (TextView) view.findViewById(R.id.month_name_txt);
            gradeName = (TextView) view.findViewById(R.id.grade_txt);
        }
    }
}
