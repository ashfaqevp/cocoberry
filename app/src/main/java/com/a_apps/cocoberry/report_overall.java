package com.a_apps.cocoberry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class report_overall extends AppCompatActivity {

    TextView d3,s1,s2,s3,o1,o2,o3;

    Button chooseBtn;
    DatePickerDialog picker;
    String TDate,date2,date3,sDate;

    ImageButton backBtn;


    final Context c = this;

    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};


    String sale1,sale2,sale3,saleCustom="0";
    String orders1,orders2,orders3,ordersCustom="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_overall);



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

        Calendar cal3= Calendar.getInstance();
        cal3.add(Calendar.DATE, -2);
        date3 = dateFormat.format((cal3.getTime()));


        s1=findViewById(R.id.s1);
        s2=findViewById(R.id.s2);
        s3=findViewById(R.id.s3);

        o1=findViewById(R.id.o1);
        o2=findViewById(R.id.o2);
        o3=findViewById(R.id.o3);

        d3=findViewById(R.id.d3);


        report1();
        report2();
        report3();


        chooseBtn=findViewById(R.id.choose);
        chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
                sDate = dateFormat.format(cldr.getTime());

                int day = cldr.get(Calendar.DAY_OF_MONTH);
                final int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(report_overall.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                              //  eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                sDate=dayOfMonth+" "+MONTHS[monthOfYear]+" "+year;
                                dialog();
                                Toast.makeText(getApplicationContext(), sDate, Toast.LENGTH_LONG).show();
                            }
                        }, year, month, day);
                picker.show();
            }
        });


        backBtn=findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });


    }






    private void report1() {
        final DatabaseReference refSale;
        FirebaseDatabase database5 = FirebaseDatabase.getInstance();
        refSale = database5.getReference().child("Reports");
        refSale.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot DataSnapshot : snapshot.getChildren()) {
                    sale1 = snapshot.child(TDate).child("Sale").getValue(String.class);
                    //    Toast.makeText(getApplicationContext(), orderNo, Toast.LENGTH_LONG).show();
                }

                if (sale1 != null) {
                    s1.setText(sale1);
                }

                if (sale1==null){
                    s1.setText("0");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }});


        final DatabaseReference refOrder;
        FirebaseDatabase database6 = FirebaseDatabase.getInstance();
        refOrder = database6.getReference().child("Reports");
        refOrder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot DataSnapshot : snapshot.getChildren()) {
                    orders1 = snapshot.child(TDate).child("Order").getValue(String.class);
                    //    Toast.makeText(getApplicationContext(), orderNo, Toast.LENGTH_LONG).show();
                }
                if (o1 != null) {
                    o1.setText(orders1);

                }

                if (o1==null){

                    o1.setText("0");

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }






    private void report2() {

        final DatabaseReference refSale;
        FirebaseDatabase database5 = FirebaseDatabase.getInstance();
        refSale = database5.getReference().child("Reports");
        refSale.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot DataSnapshot : snapshot.getChildren()) {

                    sale2 = snapshot.child(date2).child("Sale").getValue(String.class);
                    //    Toast.makeText(getApplicationContext(), orderNo, Toast.LENGTH_LONG).show();


                }

                if (sale2 != null) {
                    s2.setText(sale2);

                }

                if (sale2==null){
                    s2.setText("0");

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        final DatabaseReference refOrder;
        FirebaseDatabase database6 = FirebaseDatabase.getInstance();
        refOrder = database6.getReference().child("Reports");
        refOrder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot DataSnapshot : snapshot.getChildren()) {

                    orders2 = snapshot.child(date2).child("Order").getValue(String.class);
                    //    Toast.makeText(getApplicationContext(), orderNo, Toast.LENGTH_LONG).show();


                }

                if (orders2 != null) {
                    o2.setText(orders2);

                }

                if (orders2==null){

                    o2.setText("0");

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



    private void report3() {

        d3.setText(date3);

        final DatabaseReference refSale;
        FirebaseDatabase database5 = FirebaseDatabase.getInstance();
        refSale = database5.getReference().child("Reports");
        refSale.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot DataSnapshot : snapshot.getChildren()) {

                    sale3 = snapshot.child(date3).child("Sale").getValue(String.class);
                    //    Toast.makeText(getApplicationContext(), orderNo, Toast.LENGTH_LONG).show();


                }

                if (sale3 != null) {
                    s3.setText(sale3);

                }

                if (sale3==null){
                    s3.setText("0");

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        final DatabaseReference refOrder;
        FirebaseDatabase database6 = FirebaseDatabase.getInstance();
        refOrder = database6.getReference().child("Reports");
        refOrder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot DataSnapshot : snapshot.getChildren()) {

                    orders3 = snapshot.child(date3).child("Order").getValue(String.class);
                    //    Toast.makeText(getApplicationContext(), orderNo, Toast.LENGTH_LONG).show();


                }

                if (orders3 != null) {
                    o3.setText(orders3);

                }

                if (orders3==null){

                    o3.setText("0");

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void dialog(){




        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_report, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c, R.style.CustomDialog2);
        alertDialogBuilderUserInput.setView(mView);
        final AlertDialog dialogR = alertDialogBuilderUserInput.create();


        final TextView dateView = mView.findViewById(R.id.date);
        final TextView saleView = mView.findViewById(R.id.sale);
        final TextView orderView = mView.findViewById(R.id.order);


        dateView.setText(sDate);


        final DatabaseReference refSale;
        FirebaseDatabase database5 = FirebaseDatabase.getInstance();
        refSale = database5.getReference().child("Reports");
        refSale.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot DataSnapshot : snapshot.getChildren()) {

                    saleCustom = snapshot.child(sDate).child("Sale").getValue(String.class);
                    //    Toast.makeText(getApplicationContext(), orderNo, Toast.LENGTH_LONG).show();


                }

                if (saleCustom != null) {
                    saleView.setText(saleCustom);

                }

                if (saleCustom==null){
                    saleView.setText("0");

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        final DatabaseReference refOrder;
        FirebaseDatabase database6 = FirebaseDatabase.getInstance();
        refOrder = database6.getReference().child("Reports");
        refOrder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot DataSnapshot : snapshot.getChildren()) {

                    ordersCustom = snapshot.child(sDate).child("Order").getValue(String.class);
                    //    Toast.makeText(getApplicationContext(), orderNo, Toast.LENGTH_LONG).show();


                }

                if (ordersCustom != null) {
                    orderView.setText(ordersCustom);

                }

                if (ordersCustom==null){

                    orderView.setText("0");

                    }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        //  printList.setAdapter(adapterOrderdList);
/*

        final TextView printTotal = mView.findViewById(R.id.printTotal);
        final TextView printON = mView.findViewById(R.id.printOrderNo);
        final Button printBtn = mView.findViewById(R.id.printBtn);
        //final Button cancelBtn = mView.findViewById(R.id.printCancelBtn);







        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                dialogPrint.dismiss();

            }
        });


 */


        dialogR.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogR.show();

    }



    private void back() {
        finish();
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }

}
