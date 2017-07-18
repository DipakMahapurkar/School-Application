package com.schoolapp.adapters;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.schoolapp.R;
import com.schoolapp.activity.AddHomeworkActivity;
import com.schoolapp.activity.ApplicationActivity;
import com.schoolapp.activity.AttendanceActivity;
import com.schoolapp.activity.FeeRemainderActivity;
import com.schoolapp.activity.HomeWorkActivity;
import com.schoolapp.activity.MarksheetActivity;
import com.schoolapp.activity.MonthlyReportActivity;
import com.schoolapp.activity.NoticeActivity;
import com.schoolapp.activity.TimeTableActivity;
import com.schoolapp.activity.TimelineActivity;
import com.schoolapp.activity.YearlyScheduleActivity;
import com.schoolapp.models.DashboardCatListModel;
import com.schoolapp.utils.Constant;
import com.schoolapp.utils.Utils;

import java.util.List;

public class DashboardCatListAdapter extends RecyclerView.Adapter<DashboardCatListAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    Context mContext;
    List<DashboardCatListModel> mCategoryList;

    public DashboardCatListAdapter(Context context, List<DashboardCatListModel> categoryList) {
        this.mContext = context;
        this.mCategoryList = categoryList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryNameTextView;
        ImageView categoryImageView;
        CardView catCardView;


        ViewHolder(View v) {
            super(v);
            catCardView = (CardView) v.findViewById(R.id.idCatCardView);
            categoryNameTextView = (TextView) v.findViewById(R.id.CatTextView);
            categoryImageView = (ImageView) v.findViewById(R.id.idCatImageView);
        }
    }

    @Override
    public DashboardCatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item_category, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, final int position) {
        Vholder.categoryNameTextView.setText(mCategoryList.get(position).getCatName());
        Vholder.categoryImageView.setImageResource(mCategoryList.get(position).getResourceId());
        Vholder.categoryNameTextView.bringToFront();

        Vholder.catCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "cat_id = " + mCategoryList.get(position).getCatId() + ", " + "cat_name = " + mCategoryList.get(position).getCatName());

                switch (mCategoryList.get(position).getCatId()) {
                    case 0:
                        Utils.startActivityAfterCleanup(mContext, AttendanceActivity.class, mCategoryList.get(position).getCatId(), mCategoryList.get(position).getCatName());
                        break;
                    case 1:
                        if (Constant.USER_ROLE.equals("Teacher")) {
                            Utils.customDialog(mContext, mContext.getString(R.string.str_montly_report_student_alert));
                        } else {
                            Utils.startActivityAfterCleanup(mContext, MonthlyReportActivity.class, mCategoryList.get(position).getCatId(), mCategoryList.get(position).getCatName());
                        }
                        break;
                    case 2:
                        Utils.startActivityAfterCleanup(mContext, MarksheetActivity.class, mCategoryList.get(position).getCatId(), mCategoryList.get(position).getCatName());
                        break;
                    case 3:
                        Utils.startActivityAfterCleanup(mContext, TimelineActivity.class, mCategoryList.get(position).getCatId(), mCategoryList.get(position).getCatName());
                        break;
                    case 4:
                        if (Constant.USER_ROLE.equals("Teacher")) {
                            Utils.startActivityAfterCleanup(mContext, AddHomeworkActivity.class, mCategoryList.get(position).getCatId(), mCategoryList.get(position).getCatName());
                        } else {
                            Utils.startActivityAfterCleanup(mContext, HomeWorkActivity.class, mCategoryList.get(position).getCatId(), mCategoryList.get(position).getCatName());
                        }
                        break;
                    case 5:
                        if (Constant.USER_ROLE.equals("Teacher")) {
                            Utils.customDialog(mContext, mContext.getString(R.string.str_only_for_student_alert));
                        } else {
                            Utils.startActivityAfterCleanup(mContext, FeeRemainderActivity.class, mCategoryList.get(position).getCatId(), mCategoryList.get(position).getCatName());
                        }
                        break;
                    case 6:
                        Utils.startActivityAfterCleanup(mContext, YearlyScheduleActivity.class, mCategoryList.get(position).getCatId(), mCategoryList.get(position).getCatName());
                        break;
                    case 7:
                        Utils.startActivityAfterCleanup(mContext, TimeTableActivity.class, mCategoryList.get(position).getCatId(), mCategoryList.get(position).getCatName());
                        break;
                    case 8:
                        Utils.startActivityAfterCleanup(mContext, NoticeActivity.class, mCategoryList.get(position).getCatId(), mCategoryList.get(position).getCatName());
                        break;
                    case 9:
                        Utils.startActivityAfterCleanup(mContext, ApplicationActivity.class, mCategoryList.get(position).getCatId(), mCategoryList.get(position).getCatName());
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }
}
