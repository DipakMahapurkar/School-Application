package com.schoolapp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.schoolapp.R;
import com.schoolapp.adapters.DashboardCatListAdapter;
import com.schoolapp.models.DashboardCatListModel;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    int[] sampleImages = {R.drawable.attendance_image, R.drawable.report, R.drawable.marksheet, R.drawable.timeline, R.drawable.homework_image, R.drawable.reminder, R.drawable.schedule, R.drawable.notice, R.drawable.application, R.drawable.about_image};
    String[] catNames = {"Attendance", "Monthly Report", "Marksheet", "Timeline", "Homework", "Fee Remainder", "Yearly Schedule", "Timetable", "Notice", "Applications"};

    RecyclerView mCategoryGridView;
    Context context;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    List<DashboardCatListModel> mCategoryList;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View homeView = inflater.inflate(R.layout.fragment_home, container, false);

        initialiseView(homeView);

        //Change 2 to your choice because here 2 is the number of Grid layout Columns in each row.
        recyclerViewLayoutManager = new GridLayoutManager(context, 2);
        mCategoryGridView.setLayoutManager(recyclerViewLayoutManager);

        setupCatGridView();

        return homeView;
    }

    private void setupCatGridView() {
        mCategoryList = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            mCategoryList.add(new DashboardCatListModel(i, sampleImages[i], catNames[i]));
        }
        mCategoryGridView.setAdapter(new DashboardCatListAdapter(getActivity(), mCategoryList));
    }

    private void initialiseView(View homeView) {
        mCategoryGridView = (RecyclerView) homeView.findViewById(R.id.idCategoryGridView);
    }
}
