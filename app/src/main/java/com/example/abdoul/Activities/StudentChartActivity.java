package com.example.abdoul.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abdoul.ikpmdproject.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Models.KeuzeModel;
import Models.UserModel;
import Models.getIP;


public class StudentChartActivity extends AppCompatActivity {

    private float[] yData = new float[2];
    private String[] xData = {"Totale studenten", "Inschrijvingen"};
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_chart);


        Intent i = getIntent();
        int aantalStudenten = (int) i.getSerializableExtra("aantal");

        String s = (String) i.getSerializableExtra("Vak");
        KeuzeModel vak = new Gson().fromJson(s, KeuzeModel.class);

        pieChart = (PieChart) findViewById(R.id.chart);

        yData[0] = (float) aantalStudenten;
        yData[1] = (float) vak.getInschrijvingen() - aantalStudenten;

        pieChart.setRotationEnabled(true);

        pieChart.setTransparentCircleAlpha(0);


        addDataSet();


    }

    private void addDataSet() {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        String[] labels = {"Aantal inschrijvingen", "Overgebleven inschrijvingen"};
        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , labels[i]));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Aantal inschrijvingen");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(20);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(119,160,166));
        colors.add(Color.rgb(214,183,29));

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }


}
