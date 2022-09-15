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


public class adapterDialogAllOrders   extends RecyclerView.Adapter<adapterDialogAllOrders.itemViewholder> {



    Context context;
    static  List<constructorDBorderList> list;

    static  int ls;

    static List<constructorNewOrder> newOrderList;
    adapterNewOrder adapterNewOrder;


    public adapterDialogAllOrders
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
                .inflate(R.layout.orderd_list_view, parent, false);
        itemViewholder itemViewholder=new itemViewholder(view);
        return itemViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final itemViewholder holder, final int position) {

        //final constructorItem CI = list.get(position);
        //constructorForDatabase CB=list.get(position);
        constructorDBorderList CBDO=list.get(position);





        String NPID = String.valueOf(position + 1);


        holder.name.setText(CBDO.getI());
        holder.qnty.setText(CBDO.getQ());
        holder. rate.setText(CBDO.getR());
        holder.price.setText(CBDO.getP());


        holder.no.setText(NPID);
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
        TextView no,name,rate,qnty,price;


        public itemViewholder(@NonNull View itemView) {
            super(itemView);

            no = itemView.findViewById(R.id.oNo);
            name = itemView.findViewById(R.id.oItem);
            rate = itemView.findViewById(R.id.oRate);
            qnty = itemView.findViewById(R.id.oQnty);
            price = itemView.findViewById(R.id.oPrice);




        }
    }


}


