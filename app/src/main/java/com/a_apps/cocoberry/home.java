package com.a_apps.cocoberry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class home extends AppCompatActivity {

    FloatingActionButton btn1;
    ImageButton btn2,btn3;


    GraphView graphView;
    static String sale8="0";
    static String sale7="0",sale6="0",sale5="0",sale4="0",sale3="0",sale2="0",sale1="0";
    int s8=0,s7=0,s6=0,s5=0,s4=0,s3=0,s2=0,s1=0;
    LineChart lineChart;
    LineData lineData;
    List<Entry> entryList = new ArrayList<>();


    LinearLayout c1;
    CardView c2;

    ImageButton reportButton,salesButton;

    private long pressedTime;


    static String TDate;
    String date7,date6,date5,date4,date3,date2,date1;



    private PieChart pieChart;


    ArrayList<constructorDBorderList> list2 = new ArrayList<>();

    ArrayList<PieEntry> entries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#0a1f30"));


        DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
        TDate = dateFormat.format(Calendar.getInstance().getTime());


        Calendar cal7 = Calendar.getInstance();
        cal7.add(Calendar.DATE, -1);
        date7 = dateFormat.format((cal7.getTime()));

        Calendar cal6= Calendar.getInstance();
        cal6.add(Calendar.DATE, -2);
        date6 = dateFormat.format((cal6.getTime()));

        Calendar cal5= Calendar.getInstance();
        cal5.add(Calendar.DATE, -3);
        date5 = dateFormat.format((cal5.getTime()));

        Calendar cal4= Calendar.getInstance();
        cal4.add(Calendar.DATE, -4);
        date4 = dateFormat.format((cal4.getTime()));

        Calendar cal3= Calendar.getInstance();
        cal3.add(Calendar.DATE, -5);
        date3 = dateFormat.format((cal3.getTime()));

        Calendar cal2= Calendar.getInstance();
        cal2.add(Calendar.DATE, -6);
        date2 = dateFormat.format((cal2.getTime()));

        Calendar cal1= Calendar.getInstance();
        cal1.add(Calendar.DATE, -7);
        date1 = dateFormat.format((cal1.getTime()));

        reportButton=findViewById(R.id.report_more);
        salesButton=findViewById(R.id.sales_more);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReport();
            }
        });

        salesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSale();
            }
        });




        c1=findViewById(R.id.c1);
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReport();

            }
        });

        c2=findViewById(R.id.c2);
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSale();

            }
        });


        btn1=findViewById(R.id.newOrderBtn);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);





        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openNewOrder();

            }
        });





        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAllItems();

            }
        });






        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAllOrders();

            }
        });




        pieChart = findViewById(R.id.activity_main_piechart);
        lineChart = findViewById(R.id.lineChart);










        final DatabaseReference refSale;
        FirebaseDatabase database5 = FirebaseDatabase.getInstance();
        refSale = database5.getReference().child("Reports");
        refSale.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot DataSnapshot : snapshot.getChildren()) {

                    sale8 = snapshot.child(TDate).child("Sale").getValue(String.class);
                    sale7=snapshot.child(date7).child("Sale").getValue(String.class);
                    sale6=snapshot.child(date6).child("Sale").getValue(String.class);
                    sale5 = snapshot.child(date5).child("Sale").getValue(String.class);
                    sale4=snapshot.child(date4).child("Sale").getValue(String.class);
                    sale3=snapshot.child(date3).child("Sale").getValue(String.class);
                    sale2 = snapshot.child(date2).child("Sale").getValue(String.class);
                    sale1=snapshot.child(date1).child("Sale").getValue(String.class);
                   



                   // entryList.add(new Entry(3,s8));



                }


                if (sale8!=null) {
                    s8= Integer.parseInt(sale8);
                }


                if (sale7!=null) {
                    s7= Integer.parseInt(sale7);
                }


                if (sale6!=null) {
                    s6= Integer.parseInt(sale6);
                }



                if (sale5!=null) {
                    s5= Integer.parseInt(sale5);
                }


                if (sale4!=null) {
                    s4= Integer.parseInt(sale4);
                }


                if (sale3!=null) {
                    s3= Integer.parseInt(sale3);
                }




                if (sale2!=null) {
                    s2= Integer.parseInt(sale2);
                }


                if (sale1!=null) {
                    s1= Integer.parseInt(sale1);
                }




                graph();

                }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











        final DatabaseReference mbase;



        mbase = FirebaseDatabase.getInstance().getReference().child("Order List").child("Date Wise").child(TDate);
        //Query queryB = mbase.child("Order List").child("Order Wise").orderByChild("orderNo").equalTo(D);


        //all_orders.progressDialog.show();
        mbase.addListenerForSingleValueEvent(new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    //constructorDBorderList CDBO=new constructorDBorderList();


                    constructorDBorderList CDBO = postSnapshot.getValue(constructorDBorderList.class);


                    list2.add(CDBO);

                    String i =CDBO.getI();
                    String q =CDBO.getQ();
                    // snapshot.child("i").getValue(String.class);
                    //  String q = snapshot.child("q").getValue(String.class);
                    int qty= Integer.parseInt(q);

                    // */

                    //  String i="Ashfaqe";
                    //   int qty =6;

                    entries.add(new PieEntry(qty, i));
                    // entries.add(new PieEntry(45, "Coffee"));

                }


                setupPieChart();
                loadPieChartData();

                // all_orders.progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // progressDialog.dismiss();
                // all_orders.progressDialog.dismiss();


            }
        });














    }


    private  void graph(){

        entryList.add(new Entry(1,s1));
        entryList.add(new Entry(2,s2));
        entryList.add(new Entry(3,s3));
        entryList.add(new Entry(4,s4));
        entryList.add(new Entry(5,s5));

        entryList.add(new Entry(6,s6));
        entryList.add(new Entry(7,s7));
        entryList.add(new Entry(8,s8));

        /*
        entryList.add(new Entry(3,31));
        entryList.add(new Entry(4,24));

        entryList.add(new Entry(5,30));
        entryList.add(new Entry(6,20));
        entryList.add(new Entry(7,25));


         */

        LineDataSet lineDataSet = new LineDataSet(entryList,"country");
        lineDataSet.setColors(Color.parseColor("#ffb61b"));
        //lineDataSet.setFillAlpha(110);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);








        lineChart.setAutoScaleMinMaxEnabled(true);

        // remove axis
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setEnabled(false);
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setEnabled(false);

        // hide legend
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        lineChart.getDescription().setEnabled(false);



        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {0x58FFB61B,0x1F2E42});
        gd.setCornerRadius(0f);

        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillDrawable(gd);










        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineChart.setVisibleXRangeMaximum(10);
        lineChart.invalidate();









        graphView = findViewById(R.id.idGraphView);

        // on below line we are adding data to our graph view.
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                // on below line we are adding
                // each point on our x and y axis.
                new DataPoint(0, 4),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 9),
                new DataPoint(4, 2),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });




        graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graphView.getGridLabelRenderer().reloadStyles();
        series.setColor(Color.parseColor("#ffb61b"));
        series.setBackgroundColor(Color.parseColor("#58FFB61B"));

        //graphView.getGridLabelRenderer().setHorizontalLabelsColor(R.color.colorPrimary);
        // graphView.getGridLabelRenderer().setVerticalLabelsColor(R.color.colorShade);

        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        GradientDrawable gd2 = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {0xFF616261,0xFF131313});
        gd.setCornerRadius(0f);



        //  graphView.setBackground(gd);







        series.setDrawBackground(true);
        graphView.addSeries(series);









    }







    private void openNewOrder() {
        Intent intent=new Intent(this,order.class);
        startActivity(intent);
    }

    private void openReport() {
        Intent intent=new Intent(this,report_overall.class);
        startActivity(intent);
    }

    private void openSale() {
        Intent intent=new Intent(this,sale.class);
        startActivity(intent);
    }





    private void openAllItems() {
        Intent intent=new Intent(this,all_items.class);
        startActivity(intent);
    }




    private void openAllOrders() {
        Intent intent=new Intent(this,all_orders.class);
        startActivity(intent);
    }














    private void setupPieChart() {





        pieChart.setDrawSliceText(false); // To remove slice text
        pieChart.setDrawMarkers(false); // To remove markers when click
        pieChart.setDrawEntryLabels(false); // To remove labels from piece of pie
        pieChart.getDescription().setEnabled(false); // To remove description of pie

       // pieChart.setDrawHoleEnabled(false);

        pieChart.setHoleColor(getResources().getColor(R.color.colorShade));

        pieChart.setHoleRadius(65);



        Legend legend = pieChart.getLegend();
        legend.setTextSize(12);
        legend.setDrawInside(false);
        legend.setTextColor(getResources().getColor(R.color.colorPrimary));
        legend.setWordWrapEnabled(true);
        legend.setForm(Legend.LegendForm.CIRCLE);


       // l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER); // set vertical alignment for legend
       // legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); // set horizontal alignment for legend
      //  legend.setOrientation(Legend.LegendOrientation.VERTICAL); // set orientation for legend



    }

    private void loadPieChartData() {

      /*
        entries.add(new PieEntry(25, "Tea"));
        entries.add(new PieEntry(15, "Lime"));
        entries.add(new PieEntry(5, "Shawarma"));
        entries.add(new PieEntry(8, "Burger"));
        entries.add(new PieEntry(4, "Pizza"));
        entries.add(new PieEntry(2, "Juice"));
        entries.add(new PieEntry(9, "Sandwitch"));
        entries.add(new PieEntry(3, "Egg Roast"));

       */



        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }


      /*

        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);



       */



        PieDataSet pieDataSet = new PieDataSet(entries,"");
       // pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        pieDataSet.setColors(new int[]{Color.parseColor("#fa4524"),
                Color.parseColor("#9663c8"),
                Color.parseColor("#0078bf"),
                Color.parseColor("#fe7d00"),
                Color.parseColor("#fff500"),
                Color.parseColor("#01a802"),
                Color.parseColor("#dd0a5f"),
                Color.parseColor("#cbd2df"),



        });


       // pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
      //  pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        //pieDataSet.setValueTextSize(12);
         pieDataSet.setValueTextColor(getResources().getColor(R.color.colorPrimary));




        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
    }




    private  void pie2(){
















    }






    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }






}
