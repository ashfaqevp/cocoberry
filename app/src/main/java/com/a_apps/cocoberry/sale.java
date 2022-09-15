package com.a_apps.cocoberry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class sale extends AppCompatActivity {


    DatabaseReference mbase;


    Button chooseBtn;
    DatePickerDialog picker;
    String TDate,date2,sDate;

    RecyclerView todaySale,ySale;


    List<constructorDBorderList> list = new ArrayList<>();
    adapterSaleDate adapter;


    List<constructorDBorderList> list2 = new ArrayList<>();
    adapterSaleDate2 adapter2;


    List<constructorDBorderList> list3 = new ArrayList<>();
    adapterDialogSaleDate adapter3;


    final Context c = this;
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#0a1f30"));


        DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
        TDate = dateFormat.format(Calendar.getInstance().getTime());


        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE, -1);
        date2 = dateFormat.format((cal2.getTime()));


        todaySale=findViewById(R.id.tSaleView);
        todaySale.setLayoutManager(new LinearLayoutManager(this));
        todaySale.hasFixedSize();


        ySale=findViewById(R.id.ySaleView);
        ySale.setLayoutManager(new LinearLayoutManager(this));
        ySale.hasFixedSize();


        chooseBtn=findViewById(R.id.choose);



        todaySaleMethod();
        yesterdaySaleMethod();
       // customDateSale();


        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               chooseDate();
            }
        });













    }



    private void todaySaleMethod() {

        list.clear();


        DatabaseReference ref;

        mbase = FirebaseDatabase.getInstance().getReference();

        ref=mbase.child("Order List").child("Date Wise").child(TDate);
        ref.addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {


                    constructorDBorderList CDBO=postSnapshot.getValue(constructorDBorderList.class);

                    list.add(CDBO);

                }

                adapter = new adapterSaleDate(getApplicationContext(), list);
                todaySale.setAdapter(adapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // progressDialog.dismiss();


            }
        });

    }


    private void yesterdaySaleMethod() {

        DatabaseReference ref2;

        mbase = FirebaseDatabase.getInstance().getReference();

        ref2=mbase.child("Order List").child("Date Wise").child(date2);
        ref2.addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {


                    constructorDBorderList CDBO=postSnapshot.getValue(constructorDBorderList.class);

                    list2.add(CDBO);

                }

                adapter2 = new adapterSaleDate2(getApplicationContext(), list2);
                ySale.setAdapter(adapter2);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // progressDialog.dismiss();


            }
        });
    }






    private void chooseDate() {

        final Calendar cldr = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
        sDate = dateFormat.format(cldr.getTime());

        int day = cldr.get(Calendar.DAY_OF_MONTH);
        final int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(sale.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        sDate=dayOfMonth+" "+MONTHS[monthOfYear]+" "+year;

                        customDateSale();

                       // Toast.makeText(getApplicationContext(), sDate, Toast.LENGTH_LONG).show();
                    }
                }, year, month, day);
        picker.show();

    }




    private void customDateSale() {




        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_sale, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c, R.style.CustomDialog2);
        alertDialogBuilderUserInput.setView(mView);
        final AlertDialog dialogR = alertDialogBuilderUserInput.create();


        final TextView dateView = mView.findViewById(R.id.Date);

        dateView.setText(sDate);




        final RecyclerView customSaleView=mView.findViewById(R.id.cSaleView);
        customSaleView.setLayoutManager(new LinearLayoutManager(c));
        customSaleView.hasFixedSize();


        DatabaseReference ref3;

        mbase = FirebaseDatabase.getInstance().getReference();

        ref3=mbase.child("Order List").child("Date Wise").child(sDate);
        ref3.addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {


                    constructorDBorderList CDBO=postSnapshot.getValue(constructorDBorderList.class);

                    list3.add(CDBO);

                }

                adapter3 = new adapterDialogSaleDate(getApplicationContext(), list3);
                customSaleView.setAdapter(adapter3);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // progressDialog.dismiss();


            }
        });






        dialogR.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogR.show();



    }


}
