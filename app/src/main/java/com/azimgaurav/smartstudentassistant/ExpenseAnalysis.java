package com.azimgaurav.smartstudentassistant;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class ExpenseAnalysis extends AppCompatActivity {

    int food_value=10,money_value=50,shopping_value=30,other_value=10;
    TextView pie_month;
    Date dt=new Date();
    String str_pie_month="August 2018" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_analysis);
        PieChart pieChart = (PieChart) findViewById(R.id.chart_expense_analysis);
        pieChart.setUsePercentValues(true);
        pie_month=(TextView) findViewById(R.id.chart_month);
        // IMPORTANT: ViIn a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(food_value, 0));
        yvalues.add(new Entry(shopping_value, 1));
        yvalues.add(new Entry(money_value, 2));
        yvalues.add(new Entry(other_value, 3));
        PieDataSet dataSet = new PieDataSet(yvalues, "Expenses Analyser");

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("Food");
        xVals.add("Shopping");
        xVals.add("Money Exchange");
        xVals.add("Others");


        pie_month.setText(str_pie_month);
        PieData data = new PieData(xVals, dataSet);
        // In Percentage term
        data.setValueFormatter(new PercentFormatter());
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(data);
        pieChart.setDescription("Expenses Analyser");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setHoleRadius(25f);

        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        // pieChart.setOnChartValueSelectedListener(this);

        pieChart.animateXY(1400, 1400);

    }
}