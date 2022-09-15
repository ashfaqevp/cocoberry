package com.a_apps.cocoberry;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class all_orders extends AppCompatActivity {


    private RecyclerView recyclerView;
    static ProgressDialog progressDialog;
    DatabaseReference mbase;
    adapterAllOrders adapter;
    List<constructorForDatabase> list = new ArrayList<>();
    ImageButton backBtn;




    String TDate;
    String D;

    String date7,date6,date5,date4,date3,date2,date1;
    String date7w,date6w,date5w,date4w,date3w,date2w,date1w;
    String d1,d2,d3,d4,d5,d6,d7;


    TextView t1,t2,t3,t4,t5,t6,t7;
    TextView w1,w2,w3,w4,w5,w6,w7;

    CardView c1,c2,c3,c4,c5,c6,c7;


    FloatingActionButton   btnNewOrder;

    //Individual Orders
    onClickInterface2 onclickInterface2;
    final Context c = this;
    List<constructorDBorderList> list2 = new ArrayList<>();
    adapterDialogAllOrders adapter2;
    //adapterListFromAllOrders adapter2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#0a1f30"));

        t7=findViewById(R.id.t7);
        t6=findViewById(R.id.t6);
        t5=findViewById(R.id.t5);
        t4=findViewById(R.id.t4);
        t3=findViewById(R.id.t3);
        t2=findViewById(R.id.t2);
        t1=findViewById(R.id.t1);


        w7=findViewById(R.id.w7);
        w6=findViewById(R.id.w6);
        w5=findViewById(R.id.w5);
        w4=findViewById(R.id.w4);
        w3=findViewById(R.id.w3);
        w2=findViewById(R.id.w2);
        w1=findViewById(R.id.w1);

        c7=findViewById(R.id.c7);
        c6=findViewById(R.id.c6);
        c5=findViewById(R.id.c5);
        c4=findViewById(R.id.c4);
        c3=findViewById(R.id.c3);
        c2=findViewById(R.id.c2);
        c1=findViewById(R.id.c1);


        DateFormat dateFormat2 = new SimpleDateFormat("d MMM yyyy");
        TDate = dateFormat2.format(Calendar.getInstance().getTime());



        DateFormat dateFormat= new SimpleDateFormat("d");
        DateFormat dateFormat3= new SimpleDateFormat("EEE");

        Calendar cal7 = Calendar.getInstance();
        cal7.add(Calendar.DATE, -0);
        date7 = dateFormat.format((cal7.getTime()));
        t7.setText(date7);
        date7w = dateFormat3.format((cal7.getTime()));
        w7.setText(date7w);
        d7 = dateFormat2.format((cal7.getTime()));

        Calendar cal6= Calendar.getInstance();
        cal6.add(Calendar.DATE, -1);
        date6 = dateFormat.format((cal6.getTime()));
        t6.setText(date6);
        date6w = dateFormat3.format((cal6.getTime()));
        w6.setText(date6w);
        d6 = dateFormat2.format((cal6.getTime()));
        //Toast.makeText(getApplicationContext(), date6, Toast.LENGTH_LONG).show();

        Calendar cal5= Calendar.getInstance();
        cal5.add(Calendar.DATE, -2);
        date5 = dateFormat.format((cal5.getTime()));
        t5.setText(date5);
        date5w = dateFormat3.format((cal5.getTime()));
        w5.setText(date5w);
        d5 = dateFormat2.format((cal5.getTime()));

        Calendar cal4= Calendar.getInstance();
        cal4.add(Calendar.DATE, -3);
        date4 = dateFormat.format((cal4.getTime()));
        t4.setText(date4);
        date4w = dateFormat3.format((cal4.getTime()));
        w4.setText(date4w);
        d4 = dateFormat2.format((cal4.getTime()));

        Calendar cal3= Calendar.getInstance();
        cal3.add(Calendar.DATE, -4);
        date3 = dateFormat.format((cal3.getTime()));
        t3.setText(date3);
        date3w = dateFormat3.format((cal3.getTime()));
        w3.setText(date3w);
        d3 = dateFormat2.format((cal3.getTime()));


        Calendar cal2= Calendar.getInstance();
        cal2.add(Calendar.DATE, -5);
        date2 = dateFormat.format((cal2.getTime()));
        t2.setText(date2);
        date2w = dateFormat3.format((cal2.getTime()));
        w2.setText(date2w);
        d2 = dateFormat2.format((cal2.getTime()));

        Calendar cal1= Calendar.getInstance();
        cal1.add(Calendar.DATE, -6);
        date1 = dateFormat.format((cal1.getTime()));
        t1.setText(date1);
        date1w = dateFormat3.format((cal1.getTime()));
        w1.setText(date1w);
        d1 = dateFormat2.format((cal1.getTime()));







        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt1();
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt2();
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt3();
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt4();
            }
        });

        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt5();
            }
        });

        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt6();
            }
        });

        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dt7();
            }
        });








        backBtn=findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });





        btnNewOrder=findViewById(R.id.btnNewOrder);
        btnNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openNewOrder();
            }
        });



        recyclerView = findViewById(R.id.ordersRV);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
       // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Today();

        onClickAdd();





    }







    private void Today() {

        D=TDate;

        progressDialog = new ProgressDialog(all_orders.this);
        progressDialog.setMessage("Loading ");
        progressDialog.show();


        mbase = FirebaseDatabase.getInstance().getReference();
        Query queryB = mbase.child("Orders").orderByChild("tdate").equalTo(D);

        queryB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    constructorForDatabase CB = postSnapshot.getValue(constructorForDatabase.class);

                    list.add(CB);
                }

                adapter = new adapterAllOrders(getApplicationContext(), list,onclickInterface2);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });



    }







    private void dt1(){
        list.clear();
        adapter.notifyDataSetChanged();

        D=d1;

        progressDialog = new ProgressDialog(all_orders.this);
        progressDialog.setMessage("Loading ");
        progressDialog.show();


        mbase = FirebaseDatabase.getInstance().getReference();
        Query queryB = mbase.child("Orders").orderByChild("tdate").equalTo(D);

        queryB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    constructorForDatabase CB = postSnapshot.getValue(constructorForDatabase.class);

                    list.add(CB);
                }

                adapter = new adapterAllOrders(getApplicationContext(), list,onclickInterface2);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });



        c1.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c2.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c3.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c4.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c5.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c6.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c7.setCardBackgroundColor(Color.parseColor("#0a1f30"));

        c1.setCardBackgroundColor(Color.parseColor("#ffb61b"));


    }



    private void dt2(){
        list.clear();
        adapter.notifyDataSetChanged();

        D=d2;

        progressDialog = new ProgressDialog(all_orders.this);
        progressDialog.setMessage("Loading ");
        progressDialog.show();


        mbase = FirebaseDatabase.getInstance().getReference();
        Query queryB = mbase.child("Orders").orderByChild("tdate").equalTo(D);

        queryB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    constructorForDatabase CB = postSnapshot.getValue(constructorForDatabase.class);

                    list.add(CB);
                }

                adapter = new adapterAllOrders(getApplicationContext(), list,onclickInterface2);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });



        c1.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c2.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c3.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c4.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c5.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c6.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c7.setCardBackgroundColor(Color.parseColor("#0a1f30"));

        c2.setCardBackgroundColor(Color.parseColor("#ffb61b"));


    }


    private void dt3(){
        list.clear();
        adapter.notifyDataSetChanged();

        D=d3;

        progressDialog = new ProgressDialog(all_orders.this);
        progressDialog.setMessage("Loading ");
        progressDialog.show();


        mbase = FirebaseDatabase.getInstance().getReference();
        Query queryB = mbase.child("Orders").orderByChild("tdate").equalTo(D);

        queryB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    constructorForDatabase CB = postSnapshot.getValue(constructorForDatabase.class);

                    list.add(CB);
                }

                adapter = new adapterAllOrders(getApplicationContext(), list,onclickInterface2);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });



        c1.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c2.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c3.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c4.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c5.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c6.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c7.setCardBackgroundColor(Color.parseColor("#0a1f30"));

        c3.setCardBackgroundColor(Color.parseColor("#ffb61b"));


    }







    private void dt4(){
        list.clear();
        adapter.notifyDataSetChanged();

        D=d4;

        progressDialog = new ProgressDialog(all_orders.this);
        progressDialog.setMessage("Loading ");
        progressDialog.show();


        mbase = FirebaseDatabase.getInstance().getReference();
        Query queryB = mbase.child("Orders").orderByChild("tdate").equalTo(D);

        queryB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    constructorForDatabase CB = postSnapshot.getValue(constructorForDatabase.class);

                    list.add(CB);
                }

                adapter = new adapterAllOrders(getApplicationContext(), list,onclickInterface2);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });



        c1.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c2.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c3.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c4.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c5.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c6.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c7.setCardBackgroundColor(Color.parseColor("#0a1f30"));

        c4.setCardBackgroundColor(Color.parseColor("#ffb61b"));


    }



    private void dt5(){
        list.clear();
        adapter.notifyDataSetChanged();

        D=d5;

        progressDialog = new ProgressDialog(all_orders.this);
        progressDialog.setMessage("Loading ");
        progressDialog.show();


        mbase = FirebaseDatabase.getInstance().getReference();
        Query queryB = mbase.child("Orders").orderByChild("tdate").equalTo(D);

        queryB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    constructorForDatabase CB = postSnapshot.getValue(constructorForDatabase.class);

                    list.add(CB);
                }

                adapter = new adapterAllOrders(getApplicationContext(), list,onclickInterface2);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });



        c1.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c2.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c3.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c4.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c5.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c6.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c7.setCardBackgroundColor(Color.parseColor("#0a1f30"));

        c5.setCardBackgroundColor(Color.parseColor("#ffb61b"));


    }




    private void dt6(){
        list.clear();
        adapter.notifyDataSetChanged();

        D=d6;

        progressDialog = new ProgressDialog(all_orders.this);
        progressDialog.setMessage("Loading ");
        progressDialog.show();


        mbase = FirebaseDatabase.getInstance().getReference();
        Query queryB = mbase.child("Orders").orderByChild("tdate").equalTo(D);

        queryB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    constructorForDatabase CB = postSnapshot.getValue(constructorForDatabase.class);

                    list.add(CB);
                }

                adapter = new adapterAllOrders(getApplicationContext(), list,onclickInterface2);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });



        c1.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c2.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c3.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c4.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c5.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c6.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c7.setCardBackgroundColor(Color.parseColor("#0a1f30"));

        c6.setCardBackgroundColor(Color.parseColor("#ffb61b"));


    }




    private void dt7(){
        list.clear();
        adapter.notifyDataSetChanged();

        D=d7;

        progressDialog = new ProgressDialog(all_orders.this);
        progressDialog.setMessage("Loading ");
        progressDialog.show();


        mbase = FirebaseDatabase.getInstance().getReference();
        Query queryB = mbase.child("Orders").orderByChild("tdate").equalTo(D);

        queryB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    constructorForDatabase CB = postSnapshot.getValue(constructorForDatabase.class);

                    list.add(CB);
                }

                adapter = new adapterAllOrders(getApplicationContext(), list,onclickInterface2);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });



        c1.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c2.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c3.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c4.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c5.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c6.setCardBackgroundColor(Color.parseColor("#0a1f30"));
        c7.setCardBackgroundColor(Color.parseColor("#0a1f30"));

        c7.setCardBackgroundColor(Color.parseColor("#ffb61b"));


    }










    private void onClickAdd() {
        onclickInterface2 = new onClickInterface2() {


            @Override
            public void setClick(int abc) {

            }

            @Override
            public void orderDetails(final String orderN, String orderT) {


                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_all_orders, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c, R.style.CustomDialog2);
                alertDialogBuilderUserInput.setView(mView);
                final AlertDialog dialogPrint = alertDialogBuilderUserInput.create();


                final RecyclerView printList=mView.findViewById(R.id.orderdList);

                printList.setLayoutManager(new LinearLayoutManager(c));

                printList.hasFixedSize();


              //  printList.setAdapter(adapterOrderdList);


                final TextView printTotal = mView.findViewById(R.id.printTotal);
                final TextView printON = mView.findViewById(R.id.printOrderNo);
                final Button printBtn = mView.findViewById(R.id.printBtn);
                //final Button cancelBtn = mView.findViewById(R.id.printCancelBtn);


                printON.setText("CB"+orderN);
                printTotal.setText(orderT);

                final DatabaseReference ref;


                list2.clear();
                mbase = FirebaseDatabase.getInstance().getReference();
                //Query queryB = mbase.child("Order List").orderByChild("Order Wise").equalTo(orderN);

                ref=mbase.child("Order List").child("Order Wise").child(orderN);
                ref.addValueEventListener(new ValueEventListener()  {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {


                            constructorDBorderList CDBO=postSnapshot.getValue(constructorDBorderList.class);


                            //Toast.makeText(getApplicationContext(), CDBO.getP(), Toast.LENGTH_LONG).show();

                            list2.add(CDBO);
                            adapter2 = new adapterDialogAllOrders(getApplicationContext(), list2);
                            //adapter2.notifyDataSetChanged();
                            printList.setAdapter(adapter2);
                        }
                       // Toast.makeText(getApplicationContext(), orderN, Toast.LENGTH_LONG).show();

                      //  adapter = new adapterAllOrders(getApplicationContext(), list,onclickInterface2);




                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                       // progressDialog.dismiss();


                    }
                });



                printBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        dialogPrint.dismiss();

                    }
                });
/*
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        openNewOrder();

                    }
                });

 */

                dialogPrint.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogPrint.show();


                Toast.makeText(getApplicationContext(), "Successsssss", Toast.LENGTH_LONG).show();

            }


        };

    }



    private void printDialog() {





    }










            private void openNewOrder() {
        Intent intent=new Intent(this,order.class);
        startActivity(intent);
    }

    private void back() {
        finish();
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }





}
