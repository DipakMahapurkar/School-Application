package com.schoolapp.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.schoolapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MarksheetFragment extends Fragment {
    public static final String TAG = "Marksheet Fragment";

    View marksheetView;
    TextView mStudentNameTxt, mTestNameTxt;

    public MarksheetFragment() {
        // Required empty public constructor
    }

    public static MarksheetFragment newInstance() {
        return new MarksheetFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        marksheetView = inflater.inflate(R.layout.fragment_marksheet, container, false);

        initialiseViews(marksheetView);

        getBundleExamData();

        try {
            DisplayMarkSheet(new JSONObject(ReturnJsonData()));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return marksheetView;
    }

    private void initialiseViews(View marksheetView) {
        mStudentNameTxt = (TextView) marksheetView.findViewById(R.id.student_full_name_txt);
        mTestNameTxt = (TextView) marksheetView.findViewById(R.id.test_name_txt);
    }

    private void getBundleExamData() {
        int examId = getArguments().getInt("MARK_SHEET_EXAM_ID");
        String examName = getArguments().getString("MARK_SHEET_EXAM_NAME");
        Log.d(TAG, "id = " + examId + ", Exam Name = " + examName);
        if (!examName.isEmpty())
            mTestNameTxt.setText(examName);
    }

    String ReturnJsonData() {

        return "{\"SemesterData\":" +
                //Specify your columns here
                "{\"Columns\":" +
                "[" +
                "{\"ColumnName\":\"Subjects\"}," + //This column is intentionally blank. First Row, First Column will be blank
                "{\"ColumnName\":\"Max. Marks\"}," +
                "{\"ColumnName\":\"Marks Obtained\"}" +
//                "{\"ColumnName\":\"Semister3\"}," +
//                "{\"ColumnName\":\"Semister4\"}," +
//                "{\"ColumnName\":\"Semister5\"}" +
                "]," +
                "\"FooterRows\":" +
                "[" +
                "{\"Cells\":" +
                "[" +
                "{\"CellValue\":\"Total\"}," +
                "{\"CellValue\":\"400\"}," +
                "{\"CellValue\":\"287\"}" +
//                "{\"CellValue\":\"240\"}," +
//                "{\"CellValue\":\"300\"}," +
//                "{\"CellValue\":\"340\"}" +
                "]}," +

                "{\"Cells\":" +
                "[" +
                "{\"CellValue\":\"Percentage\"}," +
                "{\"CellValue\":\"\"}," +
                "{\"CellValue\":\"71.18%\"}" +
//                "{\"CellValue\":\"240\"}," +
//                "{\"CellValue\":\"300\"}," +
//                "{\"CellValue\":\"340\"}" +
                "]}" +
                "]," +

                "\"Rows\":" + //One row for each subject, Rows will have cells with data
                "[" +
                "{\"Cells\":" +
                "[" +
                "{\"CellValue\":\"Maths\"}," +
                "{\"CellValue\":\"100\"}," +
                "{\"CellValue\":\"70\"}" +
//                "{\"CellValue\":\"60\"}," +
//                "{\"CellValue\":\"70\"}," +
//                "{\"CellValue\":\"80\"}" +
                "]}," +

                "{\"Cells\":" +
                "[" +
                "{\"CellValue\":\"Science\"}," +
                "{\"CellValue\":\"100\"}," +
                "{\"CellValue\":\"60\"}" +
//                "{\"CellValue\":\"60\"}," +
//                "{\"CellValue\":\"70\"}," +
//                "{\"CellValue\":\"80\"}" +
                "]}," +
                "{\"Cells\":" +
                "[" +
                "{\"CellValue\":\"English\"}," +
                "{\"CellValue\":\"100\"}," +
                "{\"CellValue\":\"80\"}" +
//                "{\"CellValue\":\"60\"}," +
//                "{\"CellValue\":\"70\"}," +
//                "{\"CellValue\":\"80\"}" +
                "]}," +
                "{\"Cells\":" +
                "[" +
                "{\"CellValue\":\"Physics\"}," +
                "{\"CellValue\":\"100\"}," +
                "{\"CellValue\":\"57\"}" +
//                "{\"CellValue\":\"60\"}," +
//                "{\"CellValue\":\"70\"}," +
//                "{\"CellValue\":\"80\"}" +
                "]}]}}";
    }

    public TextView GetHeaderTextView(String HeaderText) {
        TextView title = new TextView(getActivity());
        title.setText(HeaderText);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setBackgroundColor(Color.WHITE);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        title.setPadding(10, 10, 10, 10);
        return title;
    }

    public TextView GetHeaderTextViewHidden(String HeaderText) {
        TextView title = new TextView(getActivity());
        title.setText(HeaderText);
        title.setGravity(Gravity.CENTER);
        title.setBackgroundColor(Color.parseColor("#A9A9A9"));
        title.setTextColor(Color.BLACK);
        // title.setTextAppearance(context, color.RowText);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setHeight(0);
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        title.setPadding(10, 10, 10, 10);
        return title;
    }

    public TextView GetItemTextView(String ItemText, String ColumnAlign) {
        TextView text = new TextView(getActivity());
        text.setText(ItemText);

        if (ColumnAlign.equals("L"))
            text.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        else if (ColumnAlign.equals("R"))
            text.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        else if (ColumnAlign.equals("C"))
            text.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        text.setPadding(10, 10, 10, 10);
        text.setTypeface(Typeface.DEFAULT);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        // text.setCompoundDrawablesWithIntrinsicBounds(0, 0,
        // R.drawable.next_image, 0);
        text.setTextColor(Color.BLACK);

        return text;
    }

    public static TextView GetItemTextViewHidden(Context context, String ItemText, String ColumnAlign) {
        TextView text = new TextView(context);
        text.setText(ItemText);
        if (ColumnAlign.equals("L"))
            text.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        else if (ColumnAlign.equals("R"))
            text.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        else if (ColumnAlign.equals("C"))
            text.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        text.setPadding(10, 10, 10, 10);
        text.setTypeface(Typeface.DEFAULT);
        text.setHeight(0);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        text.setTextColor(Color.WHITE);
        return text;
    }

    public void DisplayMarkSheet(JSONObject json) throws JSONException {
        TableLayout headerTable = (TableLayout) marksheetView.findViewById(R.id.header);
        TableLayout dataTable = (TableLayout) marksheetView.findViewById(R.id.maintable);

        headerTable.setStretchAllColumns(true);
        dataTable.setStretchAllColumns(true);

        TableRow headerRow = new TableRow(getActivity());
        TableRow invisibleHeaderRow = new TableRow(getActivity());

        JSONArray columnsArray = json.getJSONObject("SemesterData").getJSONArray("Columns");
        JSONArray rowsArray = json.getJSONObject("SemesterData").getJSONArray("Rows");
        JSONArray footerArray = json.getJSONObject("SemesterData").getJSONArray("FooterRows");

        // Create header row and add Columns
        if (!json.getJSONObject("SemesterData").isNull("Columns")) {
            headerRow.setBackgroundColor(Color.WHITE);
            for (int i = 0; i <= columnsArray.length() - 1; i++) {
                headerRow.addView(GetHeaderTextView(columnsArray.getJSONObject(i).getString("ColumnName")));
                invisibleHeaderRow.addView(GetHeaderTextViewHidden(columnsArray.getJSONObject(i).getString("ColumnName")));
            }
            headerTable.addView(headerRow);
            headerTable.addView(invisibleHeaderRow);
        }

        // Create data row and add data
        String headerText = "";
        String rowText;
        if (!json.getJSONObject("SemesterData").isNull("Rows")) {
            for (int i = 0; i <= rowsArray.length() - 1; i++) {
                TableRow dataRow = new TableRow(getActivity());
                TableRow invisibledataRow = new TableRow(getActivity());

                dataRow.setBackgroundColor(Color.WHITE);

                int cellsLength = 0;
                if (!rowsArray.getJSONObject(i).isNull("Cells"))

                {
                    cellsLength = rowsArray.getJSONObject(i).getJSONArray("Cells").length();
                    for (int k = 0; k <= cellsLength - 1; k++) {

                        dataRow.addView(GetItemTextView(rowsArray.getJSONObject(i).getJSONArray("Cells").getJSONObject(k).getString("CellValue"), "C"));
                        rowText = rowsArray.getJSONObject(i).getJSONArray("Cells").getJSONObject(k).getString("CellValue");

                        headerText = (String) ((TextView) invisibleHeaderRow.getChildAt(k)).getText();
                        if (headerText.length() > rowText.length())
                            invisibledataRow.addView(GetItemTextViewHidden(getActivity(), headerText, "C"));
                        else {
                            invisibledataRow.addView(GetItemTextViewHidden(getActivity(), rowsArray.getJSONObject(i).getJSONArray("Cells").getJSONObject(k).getString("CellValue"), "C"));
                            ((TextView) invisibleHeaderRow.getChildAt(k)).setText(rowText);
                        }

                    }
                    dataRow.setPadding(0, 5, 0, 5);
                    dataTable.addView(dataRow);
                    dataTable.addView(invisibledataRow);
                }
            }
        }

        // Create footer row and populate data
        if (!json.getJSONObject("SemesterData").isNull("FooterRows")) {
            for (int i = 0; i <= footerArray.length() - 1; i++) {
                TableRow footerRow = new TableRow(getActivity());
                footerRow.setBackgroundColor(Color.WHITE);
                if (!footerArray.getJSONObject(i).isNull("Cells")) {
                    for (int k = 0; k <= footerArray.getJSONObject(i).getJSONArray("Cells").length() - 1; k++) {
                        footerRow.addView(GetItemTextView(footerArray.getJSONObject(i).getJSONArray("Cells").getJSONObject(k).getString("CellValue"), "C"));
                        footerRow.setPadding(0, 5, 0, 5);

                        if (headerText.length() < footerArray.getJSONObject(i).getJSONArray("Cells").getJSONObject(k).getString("CellValue").length())
                            ((TextView) invisibleHeaderRow.getChildAt(k)).setText(footerArray.getJSONObject(i).getJSONArray("Cells").getJSONObject(k).getString("CellValue"));
                    }
                    dataTable.addView(footerRow);
                }
            }
        }
    }
}
