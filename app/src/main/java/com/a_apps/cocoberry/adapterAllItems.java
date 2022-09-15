package com.a_apps.cocoberry;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class adapterAllItems   extends RecyclerView.Adapter<adapterAllItems.itemViewholder> {



    Context context;
    List<constructorItem> list;

    onClickInterface onClickInterface;



    public adapterAllItems
            ( Context context,List<constructorItem> TempList,onClickInterface onClickInterface)

    {

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
                .inflate(R.layout.list_all_items, parent, false);
        itemViewholder itemViewholder=new itemViewholder(view);
        return itemViewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final itemViewholder holder, final int position) {


        DatabaseReference mbase;


        //final constructorItem CI = list.get(position);
        final constructorItem CI=list.get(position);

        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickInterface.setClick(position);
                onClickInterface.itemDetails(CI.getItemName(),CI.getRate(),CI.getImageLink());
                onClickInterface.itemRate(CI.getRate());
            }
        });


        holder.aiItem.setText(CI.getItemName());
        holder.aiPrice.setText("Price : "+CI.getRate());
        Glide.with(holder.imageView.getContext()).load(CI.getImageLink()).diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(120, 120)
                .thumbnail(0.1f)

                //.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .centerCrop()
                .dontAnimate()
                .dontTransform()
                .placeholder(R.drawable.cocoberry_logo)
                .priority(Priority.IMMEDIATE)

               // .encodeFormat(Bitmap.CompressFormat.PNG)
               // .format(DecodeFormat.DEFAULT)

.into(holder.imageView);





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


    public void updateList(ArrayList<constructorItem> temp){

        list=temp;
        notifyDataSetChanged();
    }



    class itemViewholder extends RecyclerView.ViewHolder {
        TextView aiItem,aiPrice;
        CircleImageView imageView;
        CardView click;


        public itemViewholder(@NonNull View itemView) {
            super(itemView);

            aiItem = itemView.findViewById(R.id.aiItem);
            aiPrice = itemView.findViewById(R.id.aiPrice);
            imageView = itemView.findViewById(R.id.aiImage);
            click=itemView.findViewById(R.id.clickCard);








        }
    }


}




