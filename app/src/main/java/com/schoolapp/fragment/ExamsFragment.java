package com.schoolapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schoolapp.R;
import com.schoolapp.adapters.ExamAdapter;
import com.schoolapp.models.ExamModel;

import java.util.ArrayList;
import java.util.List;

public class ExamsFragment extends Fragment {

    RecyclerView mExamRecyclerView;
    List<ExamModel> mExamList;

    public ExamsFragment() {
        // Required empty public constructor
    }

    public static ExamsFragment newInstance() {
        return new ExamsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View examsView = inflater.inflate(R.layout.fragment_exams, container, false);

        initialiseViews(examsView);

        staticExamData();

        return examsView;
    }

    private void initialiseViews(View examsView) {
        mExamRecyclerView = (RecyclerView) examsView.findViewById(R.id.exams_recycler_view);
        mExamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mExamRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void staticExamData() {
        mExamList = new ArrayList<>();

        mExamList.add(new ExamModel(1, "Unit Test 1"));
        mExamList.add(new ExamModel(2, "Unit Test 2"));
        mExamList.add(new ExamModel(3, "Unit Test 3"));
        mExamList.add(new ExamModel(4, "1st Semester"));
        mExamList.add(new ExamModel(5, "Unit Test 4"));
        mExamList.add(new ExamModel(6, "Unit Test 5"));
        mExamList.add(new ExamModel(7, "Unit Test 6"));
        mExamList.add(new ExamModel(8, "Annual Exam"));

        mExamRecyclerView.setAdapter(new ExamAdapter(getActivity(), mExamList));
    }

}
