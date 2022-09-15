package com.a_apps.cocoberry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;





      public class adapterItemView   extends RecyclerView.Adapter<adapterItemView.itemViewholder> {



    Context context;
    List<constructorItem> list;

    adapterNewOrder adapterNewOrder;
    onClickInterface onClickInterface;

    public adapterItemView
            ( Context context,List<constructorItem> TempList,onClickInterface onClickInterface){

        this.list = TempList;
        this.context = context;

        this.onClickInterface = onClickInterface;

    }





    @NonNull
    @Override
    public itemViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_view, parent, false);
        itemViewholder itemViewholder=new itemViewholder(view);
        return itemViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final itemViewholder holder, final int position) {
        final constructorItem CI = list.get(position);
        holder.name.setText(CI.getItemName());
        holder.rate.setText(CI.getRate());
        Glide.with(holder.imageView.getContext()).load(CI.getImageLink())  .thumbnail(0.1f).override(50, 50).into(holder.imageView);


        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.setClick(position);
                onClickInterface.itemDetails(CI.getItemName(),CI.getRate(),CI.getImageLink());
                onClickInterface.itemRate(CI.getRate());
            }
        });

      /*  holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newOrderList.add(new constructorNewOrder("Q", "item", "rate",""));
                adapterNewOrder.notifyDataSetChanged();

            }
        });

       */



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void updateList(ArrayList<constructorItem>temp){

        list=temp;
        notifyDataSetChanged();
    }



    class itemViewholder extends RecyclerView.ViewHolder {
        TextView name,rate;
        CircleImageView imageView;
        FloatingActionButton add;

        public itemViewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.itemName);
            rate = itemView.findViewById(R.id.itemPrice);
            imageView=itemView.findViewById(R.id.image);
            add=itemView.findViewById(R.id.add);

        }
    }


}

