package com.a_apps.cocoberry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adapterSaleDate   extends RecyclerView.Adapter<adapterSaleDate.itemViewholder> {



    Context context;
    static List<constructorDBorderList> list;

    static  int ls;

    static List<constructorNewOrder> newOrderList;
    adapterNewOrder adapterNewOrder;


    public adapterSaleDate
            ( Context context,List<constructorDBorderList> TempList)

    {


        this.list = TempList;
        this.context = context;


        // this.onClickInterface = onClickInterface;

    }




    @NonNull
    @Override
    public itemViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_sale, parent, false);
        itemViewholder itemViewholder=new itemViewholder(view);
        return itemViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final itemViewholder holder, final int position) {

        //final constructorItem CI = list.get(position);
        //constructorForDatabase CB=list.get(position);
        constructorDBorderList CDB=list.get(position);

        holder.orderItem.setText(CDB.getI());
        holder.orderQnty.setText(CDB.getQ());



        ls=list.size();

        //notifyDataSetChanged();






        //   Glide.with(holder.imageView.getContext()).load(CI.getImageLink()).override(50, 50).into(holder.imageView);

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


    public void updateList(ArrayList<constructorDBorderList> temp){

        list=temp;
        notifyDataSetChanged();
    }



    class itemViewholder extends RecyclerView.ViewHolder {
        TextView orderItem,orderQnty;
        ListView listOrders;
        //  CircleImageView imageView;
        //  FloatingActionButton add;

        public itemViewholder(@NonNull View itemView) {
            super(itemView);

            orderItem = itemView.findViewById(R.id.item);
            orderQnty = itemView.findViewById(R.id.qnty);



        }
    }


}


