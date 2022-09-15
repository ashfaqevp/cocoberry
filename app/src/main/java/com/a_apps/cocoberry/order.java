package com.a_apps.cocoberry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class order extends AppCompatActivity {



    @Override
    public void onBackPressed(){

        if (adapterNewOrder.newOrderList.isEmpty()){
            finish();
            Intent intent=new Intent(this,home.class);
            startActivity(intent);
        }
        else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(order.this);
            alertDialogBuilder.setMessage("Delete All Purchased Item!");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {



                        }
                    });



            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
        }

    }


    ImageButton backBtn;

    private RecyclerView recyclerView;
    private EditText searchView;

    LinearLayout sizing;
    Button btnSizing;
    LinearLayout hiddenView;
    CardView cardView;

    ProgressDialog progressDialog;
    static ProgressDialog progressDialogA;
    static ProgressDialog progressDialogD;

    adapterItemView adapter;
    DatabaseReference mbase;
    List<constructorItem> list = new ArrayList<>();

    onClickInterface onclickInterface;
    List<constructorNewOrder> newOrderList;
    static adapterNewOrder adapterNewOrder;
    ListView cartList;


    static int progress = 0;
    static int progressP = 0;



    static adapterOrderdList adapterOrderdList;




    static TextView totalPrice;
    static TextView cartCountView;
    static String cartCount = "0";
    Button btnOrder;

    static String orderNo="0";

    static String sale="0";
    static String orderCount="0";


    static String TDate;

    String qnty="1";


    ImageButton fav, bakes, cool, hot , others;

    final Context c = this;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#0a1f30"));


        DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
        TDate = dateFormat.format(Calendar.getInstance().getTime());




        newOrderList = new ArrayList<>();
        cartList = findViewById(R.id.cartList);
        adapterNewOrder = new adapterNewOrder(this, R.layout.new_order_item, newOrderList);
        adapterOrderdList = new adapterOrderdList(this, R.layout.orderd_list_view, newOrderList);


        backBtn=findViewById(R.id.backBtn);


        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.itemsView);

        hiddenView = findViewById(R.id.hiddenLayout);
        cardView = findViewById(R.id.bottomLayout);
        sizing = findViewById(R.id.sizing);
        btnSizing=findViewById(R.id.btnSizing);

        btnOrder = findViewById(R.id.btnOrder);
        totalPrice = findViewById(R.id.totalPrice);
        cartCountView = findViewById(R.id.cartCount);

        fav = findViewById(R.id.fav);
        bakes = findViewById(R.id.bakes);
        cool = findViewById(R.id.cool);
        hot = findViewById(R.id.hot);
        others = findViewById(R.id.others);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



        progressDialogA = new ProgressDialog(order.this);
        progressDialogA.setMessage("Saving");
        progressDialogA.setCanceledOnTouchOutside(false);
        progressDialogA.setCancelable(false);
        progressDialogA.setMax(100);
        progressDialogA.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


        progressDialogD = new ProgressDialog(order.this);
        progressDialogD.setMessage("Deleting");
        progressDialogD.setCanceledOnTouchOutside(false);
        progressDialogD.setCancelable(false);
        progressDialogD.setMax(100);
        progressDialogD.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);




        getOrderNo();

        setSizing();

        onClickAdd();

        Fav();

        checkout();



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backBtnPressed();
            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                adapter.notifyDataSetChanged();
                Fav();
            }
        });
        bakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bakes();
            }
        });
        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hot();
            }
        });
        cool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cool();
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Others();
            }
        });














        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });
    }

















    //filter
    private void filter(String str) {
        ArrayList<constructorItem> temp = new ArrayList<>();
        try {
            for (constructorItem CI : list) {
                if (CI.getItemName().toLowerCase().contains(str.toLowerCase())) {
                    temp.add(CI);

                }
            }
        } catch (Exception e) {

        }
        adapter.updateList(temp);
    }











//ORDER NO

    private void getOrderNo() {

      /*
        final String[] abc = new String[1];

        final DatabaseReference refOrderno;

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        refOrderno =database.getReference();

        refOrderno.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot DataSnapshot : snapshot.getChildren()) {

                    orderNo = DataSnapshot.child("orderNo").getValue(String.class);

                }

               Toast.makeText(getApplicationContext(), orderNo, Toast.LENGTH_LONG).show(); T

                String one="1";
               // orderNo = String.format("%.0f", Double.parseDouble(orderNo) + Double.parseDouble(one));

                refOrderno.child("orderNo").child("orderNo").setValue("555");
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


       */
















        final DatabaseReference ref5;
        FirebaseDatabase database5 = FirebaseDatabase.getInstance();
        ref5 = database5.getReference();
        ref5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot DataSnapshot : snapshot.getChildren()) {

                    orderNo = snapshot.child("orderNo").child("no").getValue(String.class);
                    Toast.makeText(getApplicationContext(), orderNo, Toast.LENGTH_LONG).show();


                }
                orderNo = String.format("%.0f", Double.parseDouble("1") + Double.parseDouble(orderNo));
                ref5.child("orderNo").child("no").setValue(orderNo);



            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
























    //sizing
    private void setSizing() {


       sizing.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {


               if(event.getAction() == MotionEvent.ACTION_UP){


                   if (hiddenView.getVisibility() == View.VISIBLE) {
                       TransitionManager.beginDelayedTransition(cardView,
                               new AutoTransition());
                       hiddenView.setVisibility(View.GONE);


                   } else {

                       TransitionManager.beginDelayedTransition(cardView,
                               new AutoTransition());
                       hiddenView.setVisibility(View.VISIBLE);
                       // arrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                   }

                  // return true;
               }

               return true;

           }



       });







        btnSizing.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {



                if(event.getAction() == MotionEvent.ACTION_UP){


                    if (hiddenView.getVisibility() == View.VISIBLE) {
                        TransitionManager.beginDelayedTransition(cardView,
                                new AutoTransition());
                        hiddenView.setVisibility(View.GONE);


                    } else {

                        TransitionManager.beginDelayedTransition(cardView,
                                new AutoTransition());
                        hiddenView.setVisibility(View.VISIBLE);
                        // arrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
                    }


                }


                return true;
            }
        });


    }













//onClickListener
    private void onClickAdd() {
        onclickInterface = new onClickInterface() {
            @Override
            public void setClick(int abc) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void itemDetails(final String itemN, final String itemR, final String itemImage) {
                //Toast.makeText(getApplicationContext(), itemN, Toast.LENGTH_LONG).show();


                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_qnty, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c, R.style.CustomDialog);
                alertDialogBuilderUserInput.setView(mView);
                final AlertDialog dialog = alertDialogBuilderUserInput.create();


                final CircleImageView image = (CircleImageView) mView.findViewById(R.id.aiImage);
                //GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gifImageView);
                Glide.with(image.getContext()).load(itemImage).into(image);


                final TextView itemView = mView.findViewById(R.id.itemView);
                final EditText qntyView = mView.findViewById(R.id.qntyView);
                final Button addBtn = mView.findViewById(R.id.addBtn);
                final Button cancelBtn = mView.findViewById(R.id.cancelBtn);


                itemView.setText(itemN);


                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        qnty="1";

                        if (qntyView.getText().toString().length()>0){
                        qnty = qntyView.getText().toString();}

                        Toast.makeText(getApplicationContext(), itemN + qnty, Toast.LENGTH_LONG).show();


                        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                        connectedRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                boolean connected = snapshot.getValue(Boolean.class);
                                if (connected) {


                                    progressDialogA.show();


                                    saveOrderToDatabase();
                                    saveOrderList(itemN,itemR,qnty);
                                    todayOrderList(itemN,qnty);



                                    newOrderList.add(new constructorNewOrder(itemN, qnty, itemR));
                                    adapterNewOrder.notifyDataSetChanged();

                                    adapterOrderdList.notifyDataSetChanged();

                                    cartList.setAdapter(adapterNewOrder);

                                    adapter.notifyDataSetChanged();
                                    cartCounting();
                                    adapter.adapterNewOrder.totalingBill();



                                    //Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();


                                } else {

                                    Toast.makeText(getApplicationContext(), "notConnected", Toast.LENGTH_SHORT).show();

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(order.this);
                                    alertDialogBuilder.setMessage("Network Not Connected");
                                    alertDialogBuilder.setPositiveButton("Ok",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {


                                                }
                                            });

                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    alertDialog.show();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                        dialog.dismiss();

                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });


                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }

            @Override
            public void itemRate(String itemR) {
                adapter.notifyDataSetChanged();
            }


        };

    }











    //getCountCart
    public static void cartCounting() {
        cartCount = String.valueOf(adapterNewOrder.newOrderList.size());
        cartCountView.setText(cartCount);
    }







    //Database Fav
    private void Fav() {


        progressDialog = new ProgressDialog(order.this);
        progressDialog.setMessage("Loading ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();


        mbase = FirebaseDatabase.getInstance().getReference();
        Query queryB = mbase.child("Items").orderByChild("fav").equalTo("True");

        queryB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    constructorItem CI = postSnapshot.getValue(constructorItem.class);

                    list.add(CI);
                }

                adapter = new adapterItemView(getApplicationContext(), list, onclickInterface);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });

        fav.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        bakes.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        hot.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        cool.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        others.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);

        fav.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorAccent), PorterDuff.Mode.ADD);

    }







// Database Bakes


    private void Bakes() {

        list.clear();
        adapter.notifyDataSetChanged();

        progressDialog = new ProgressDialog(order.this);
        progressDialog.setMessage("Loading ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();


        mbase = FirebaseDatabase.getInstance().getReference();
        Query queryB = mbase.child("Items").orderByChild("cat").equalTo("Bakes");

        queryB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    constructorItem CI = postSnapshot.getValue(constructorItem.class);

                    list.add(CI);
                }

                adapter = new adapterItemView(getApplicationContext(), list, onclickInterface);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });

        fav.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        bakes.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        hot.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        cool.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        others.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);

        bakes.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorAccent), PorterDuff.Mode.ADD);

    }


// Database Hot


    private void Hot() {

        list.clear();
        adapter.notifyDataSetChanged();

        progressDialog = new ProgressDialog(order.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ");
        progressDialog.show();


        mbase = FirebaseDatabase.getInstance().getReference();
        Query queryB = mbase.child("Items").orderByChild("cat").equalTo("Hot");

        queryB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    constructorItem CI = postSnapshot.getValue(constructorItem.class);

                    list.add(CI);
                }

                adapter = new adapterItemView(getApplicationContext(), list, onclickInterface);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });

        fav.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        bakes.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        hot.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        cool.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        others.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);

        hot.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorAccent), PorterDuff.Mode.ADD);

    }



// Database Cool

    private void Cool() {

        list.clear();
        adapter.notifyDataSetChanged();

        progressDialog = new ProgressDialog(order.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Loading ");
        progressDialog.show();


        mbase = FirebaseDatabase.getInstance().getReference();
        Query queryB = mbase.child("Items").orderByChild("cat").equalTo("Cool");

        queryB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    constructorItem CI = postSnapshot.getValue(constructorItem.class);

                    list.add(CI);
                }

                adapter = new adapterItemView(getApplicationContext(), list, onclickInterface);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });




        fav.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        bakes.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        hot.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        cool.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        others.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);

        cool.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorAccent), PorterDuff.Mode.ADD);



    }




// Database Hot


    private void Others() {

        list.clear();
        adapter.notifyDataSetChanged();

        progressDialog = new ProgressDialog(order.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ");
        progressDialog.show();


        mbase = FirebaseDatabase.getInstance().getReference();
        Query queryB = mbase.child("Items").orderByChild("cat").equalTo("Others");

        queryB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    constructorItem CI = postSnapshot.getValue(constructorItem.class);

                    list.add(CI);
                }

                adapter = new adapterItemView(getApplicationContext(), list, onclickInterface);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();


            }
        });

        fav.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        bakes.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        hot.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        cool.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);
        others.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorShade), PorterDuff.Mode.ADD);

        others.getBackground().setColorFilter(cool.getContext().getResources().getColor(R.color.colorAccent), PorterDuff.Mode.ADD);

    }



















// SAVE ORDER

    private void saveOrderToDatabase() {

        final DatabaseReference ref;

        ref = FirebaseDatabase.getInstance().getReference().child("Orders");

        Query query = ref.orderByChild("orderNo").equalTo(orderNo);
        query.addListenerForSingleValueEvent (new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if(snapshot.exists()){
                for (DataSnapshot DataSnapshot : snapshot.getChildren()) {

                    DataSnapshot.getRef().child("total").setValue(totalPrice.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                     ProgressDialogProgress();
                                }
                            }); }





            }   if(! snapshot.exists()){

                constructorForDatabase CD = new constructorForDatabase();


                CD.setOrderNo(orderNo);
                CD.setTDate(TDate);
                CD.setTotal(totalPrice.getText().toString());



                String UserId = ref.push().getKey();
                ref.child(UserId).setValue(CD)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {


                                 ProgressDialogProgress();
                            }
                        });
            }

        }
        @Override
        public void  onCancelled(@NonNull DatabaseError error) {

        }
    });
    }








//SAVE  ORDER VISE LIST
    private void saveOrderList(final String itemN, final String itemR, final String qnty){

        final DatabaseReference ref3;
        FirebaseDatabase database3=FirebaseDatabase.getInstance();
       ref3 =database3.getReference().child("Order List").child("Order Wise");
       ref3.addListenerForSingleValueEvent (new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {


               constructorDBorderList CDBO=new constructorDBorderList();

               String qxr =String.format("%.2f", Double.parseDouble(itemR) * Double.parseDouble(qnty));

               CDBO.setI(itemN);
               CDBO.setP(qxr);
               CDBO.setQ(qnty);
               CDBO.setR(itemR);

               String UserId = ref3.push().getKey();
               ref3.child(orderNo).child(UserId).setValue(CDBO).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       ProgressDialogProgress();
                   }
               });


                                                              }

           @Override
           public void  onCancelled(@NonNull DatabaseError error) {

           }
       });



    }




//UPDATE TODAY ORDER LIST
    private void  todayOrderList(final String itemN, final String qnty) {


        final DatabaseReference ref4;

        FirebaseDatabase database4=FirebaseDatabase.getInstance();
        ref4 =database4.getReference().child("Order List").child("Date Wise").child(TDate);
        Query query7 = ref4.orderByChild("i").equalTo(itemN);
        query7.addListenerForSingleValueEvent (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.exists()){

                    for (DataSnapshot DataSnapshot : snapshot.getChildren()) {




                        String ab = DataSnapshot.child("q").getValue(String.class);

                        DataSnapshot.getRef().child("q").setValue(String.format("%.0f", Double.parseDouble(ab) + Double.parseDouble(qnty)))
                                .addOnSuccessListener(new OnSuccessListener<Void>() {


                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        ProgressDialogProgress();
                                    }
                                }); }





                }   if(! snapshot.exists()){

                    constructorDBorderList CDBO = new constructorDBorderList();
                    CDBO.setI(itemN);

                    CDBO.setQ(qnty);


                    String UserId = ref4.push().getKey();
                    ref4.child(UserId).setValue(CDBO)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                 ProgressDialogProgress();
                                }
                            });
                }

            }
            @Override
            public void  onCancelled(@NonNull DatabaseError error) {

            }
        });
    }














// CHECKOUT BUTTON

    private void checkout(){





        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(order.this);
        alertDialogBuilder.setMessage("Order " + cartCountView.getText().toString() + " items ?");
        alertDialogBuilder.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        printDialog();
                        updateReport();



                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();



            }
        });


    }






// DILOGE PRINT

    private void printDialog() {





        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
        View mView = layoutInflaterAndroid.inflate(R.layout.print_view, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c, R.style.CustomDialog2);
        alertDialogBuilderUserInput.setView(mView);
        final AlertDialog dialogPrint = alertDialogBuilderUserInput.create();

        dialogPrint.setCanceledOnTouchOutside(false);
        dialogPrint.setCancelable(false);


        final ListView printList=mView.findViewById(R.id.orderdList);
        printList.setAdapter(adapterOrderdList);


        final TextView printTotal = mView.findViewById(R.id.printTotal);
        final TextView printON = mView.findViewById(R.id.printOrderNo);
        final Button printBtn = mView.findViewById(R.id.printBtn);
        final Button cancelBtn = mView.findViewById(R.id.printCancelBtn);
        final ImageButton homeBtn=mView.findViewById(R.id.home);


        printON.setText("CB"+orderNo);
        printTotal.setText(totalPrice.getText().toString());

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHome();
            }
        });


        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                dialogPrint.dismiss();

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               openNewOrder();

            }
        });

        dialogPrint.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogPrint.show();


    }





// Update Report

    private void updateReport(){




        final DatabaseReference refSale;
        FirebaseDatabase database5 = FirebaseDatabase.getInstance();
        refSale = database5.getReference().child("Reports");
        refSale.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot DataSnapshot : snapshot.getChildren()) {

                    sale = snapshot.child(TDate).child("Sale").getValue(String.class);
                //    Toast.makeText(getApplicationContext(), orderNo, Toast.LENGTH_LONG).show();


                }

                if (sale != null) {
                    sale = String.format("%.0f", Double.parseDouble(totalPrice.getText().toString()) + Double.parseDouble(sale));
                    refSale.child(TDate).child("Sale").setValue(sale);
                }

                if (sale==null){
                    refSale.child(TDate).child("Sale").setValue(String.format("%.0f", Double.parseDouble(totalPrice.getText().toString())));
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

                    orderCount = snapshot.child(TDate).child("Order").getValue(String.class);
                    //    Toast.makeText(getApplicationContext(), orderNo, Toast.LENGTH_LONG).show();


                }

                if (orderCount != null) {
                    orderCount = String.format("%.0f", Double.parseDouble(cartCountView.getText().toString()) + Double.parseDouble(orderCount));
                    refOrder.child(TDate).child("Order").setValue(orderCount);
                }

                if (orderCount==null){
                    refOrder.child(TDate).child("Order").setValue(String.format("%.0f", Double.parseDouble(cartCountView.getText().toString())));
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }











// Open New Order
    private void openNewOrder() {
        Intent intent = new Intent(this, order.class);
        startActivity(intent);
    }

    private void openHome() {
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }






    public void ProgressDialogProgress() {
        progress += 10;
        String  b= String.valueOf(progress);

       Toast.makeText(getApplicationContext(), b, Toast.LENGTH_SHORT).show();
        if (progress == 30) {
            progressDialogA.dismiss();
            progress = 0;
        }

    }

    public static void ProgressDialogDelete() {
        progressP += 10;
        if (progressP == 30) {
            progressDialogA.dismiss();
            progressP = 0;
        }

    }






// ON BACK BTN PRESSD

    private void backBtnPressed(){




        if (adapterNewOrder.newOrderList.isEmpty()){
            finish();
            Intent intent=new Intent(this,home.class);
            startActivity(intent);
        }
        else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(order.this);
            alertDialogBuilder.setMessage("Delete All Purchased Item!");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
        }

    }






}
