package com.a_apps.cocoberry;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class adapterAllOrders   extends RecyclerView.Adapter<adapterAllOrders.itemViewholder> {



    Context context;
    List<constructorForDatabase> list;
    onClickInterface2 onClickInterface2;


    adapterListFromAllOrders adapter;



    List<constructorNewOrder> newOrderList;
    adapterNewOrder adapterNewOrder;


    public adapterAllOrders
            ( Context context,List<constructorForDatabase> TempList,onClickInterface2 onClickInterface2)

    {

        this.list = TempList;
        this.context = context;

       this.onClickInterface2 = onClickInterface2;

    }





    @NonNull
    @Override
    public itemViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_recyclerview, parent, false);
        itemViewholder itemViewholder=new itemViewholder(view);
        return itemViewholder;
    }

    @Override 
    public void onBindViewHolder(@NonNull final itemViewholder holder, final int position) {


        DatabaseReference mbase;


        //final constructorItem CI = list.get(position);
        final constructorForDatabase CB=list.get(position);

        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface2.setClick(position);
                onClickInterface2.orderDetails(CB.getOrderNo(),CB.getTotal());

            }
        });

        holder.orderNo.setText("CB"+CB.orderNo);
        holder.total.setText(CB.getTotal());


        int ls =adapterListFromAllOrders.ls;









        mbase = FirebaseDatabase.getInstance().getReference().child("Order List").child("Order Wise").child(CB.orderNo);
        //Query queryB = mbase.child("Order List").child("Order Wise").orderByChild("orderNo").equalTo(D);

        final List<constructorDBorderList> list2 = new ArrayList<>();
        //all_orders.progressDialog.show();
        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    //constructorDBorderList CDBO=new constructorDBorderList();


                    constructorDBorderList CDBO = postSnapshot.getValue(constructorDBorderList.class);


                    list2.add(CDBO);

                }

                adapter = new adapterListFromAllOrders(context.getApplicationContext(), list2);
                holder.listOrders.setAdapter(adapter);
                // all_orders.progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // progressDialog.dismiss();
                // all_orders.progressDialog.dismiss();


            }
        });



        // String l= String.valueOf(ls);


        String l =String.valueOf(list2.size());

        // if (ls>1){
        holder.moreIf.setText("More   "+l);
        // }


        // holder.listOrders.setAdapter(adapterListFromAllOrders);





/*
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.setClick(position);
                onClickInterface.itemDetails(CI.getItemName(),CI.getRate(),CI.getImageLink());
                onClickInterface.itemRate(CI.getRate());
            }
        });


 */





    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void updateList(ArrayList<constructorForDatabase> temp){

        list=temp;
        notifyDataSetChanged();
    }



    class itemViewholder extends RecyclerView.ViewHolder {
        TextView orderNo,total,moreIf;
        RecyclerView listOrders;
        CardView click;
        //  CircleImageView imageView;
        //  FloatingActionButton add;

        public itemViewholder(@NonNull View itemView) {
            super(itemView);

            orderNo = itemView.findViewById(R.id.aoOrderNo);
            total = itemView.findViewById(R.id.aoTotal);
            moreIf = itemView.findViewById(R.id.moreIf);
            listOrders=itemView.findViewById(R.id.list_orders);
            click=itemView.findViewById(R.id.clickCard);

            // holder.listOrders.setLayoutManager(new LinearLayoutManager(holder.listOrders.getContext()));
            // holder.listOrders.setHasFixedSize(true);

            listOrders.setLayoutManager(new LinearLayoutManager(context));
            listOrders.setHasFixedSize(true);








        }
    }


}


