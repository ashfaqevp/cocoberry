package com.a_apps.cocoberry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;



import java.util.List;



public class adapterOrderdList extends ArrayAdapter<constructorNewOrder> {
    static List<constructorNewOrder> newOrderList;
    static Context context;
    int i;

    static String STotal;
    static Double Dtotal2;
    static String QTotal;


    public adapterOrderdList(@NonNull Context context, int i, List<constructorNewOrder> newOrderList) {
        super(context, i, newOrderList);
        this.context = context;
        this.i = i;
        this.newOrderList = newOrderList;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {



        View view = convertView;
        constructorNewOrder newOrderClass = newOrderList.get(position);
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.orderd_list_view, null);


        TextView no = view.findViewById(R.id.oNo);
        TextView name = view.findViewById(R.id.oItem);
        TextView rate = view.findViewById(R.id.oRate);
        TextView qnty = view.findViewById(R.id.oQnty);
        TextView price = view.findViewById(R.id.oPrice);



        String NPID = String.valueOf(position + 1);

        name.setText(newOrderClass.getI());
        qnty.setText(newOrderClass.getQ());
        rate.setText(newOrderClass.getR());

        Double DNPQ = Double.parseDouble(newOrderClass.getQ());
        Double DNPR = Double.parseDouble(newOrderClass.getR());
        Double DNPT = DNPQ * DNPR;
        final String SNPT = String.valueOf(DNPT);


        price.setText(SNPT);
        no.setText(NPID);









        return view;

    }

}




