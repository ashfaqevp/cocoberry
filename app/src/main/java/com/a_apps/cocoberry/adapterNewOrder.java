package com.a_apps.cocoberry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.a_apps.cocoberry.order.TDate;
import static com.a_apps.cocoberry.order.orderNo;
import static com.a_apps.cocoberry.order.totalPrice;


public class adapterNewOrder extends ArrayAdapter<constructorNewOrder> {
        static List<constructorNewOrder> newOrderList;
        static Context context;
        int i;

        static String STotal;
        static Double Dtotal2;
        static String QTotal;




        public adapterNewOrder(@NonNull Context context, int i, List<constructorNewOrder> newOrderList) {
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
            view = layoutInflater.inflate(R.layout.new_order_item, null);


            TextView no = view.findViewById(R.id.no);
            TextView name = view.findViewById(R.id.itemName);
            TextView qnty = view.findViewById(R.id.itemQnty);
            TextView price = view.findViewById(R.id.itemPrice);
            ImageButton removeBtn = view.findViewById(R.id.imageView4);


            String NPID = String.valueOf(position + 1);

            name.setText(newOrderClass.getI());
            qnty.setText(newOrderClass.getQ());
            price.setText(newOrderClass.getR());

            Double DNPQ = Double.parseDouble(newOrderClass.getQ());
            Double DNPR = Double.parseDouble(newOrderClass.getR());
            Double DNPT = DNPQ * DNPR;
            final String SNPT = String.valueOf(DNPT);



            price.setText(SNPT);
            no.setText(NPID);





















            totalingBill();


            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   // order.progressDialogOrder.show();



                    removeOrder(position);
                }


            });




            return view;
        }




        private void removeOrder(int position) {

            final constructorNewOrder newBIllOrder=newOrderList.get(position);






//set ORDER DETAILS
            final DatabaseReference ref;
            ref = FirebaseDatabase.getInstance().getReference().child("Orders");
            Query query = ref.orderByChild("orderNo").equalTo(orderNo);
            query.addListenerForSingleValueEvent (new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                        for (DataSnapshot DataSnapshot : snapshot.getChildren()) {

                            DataSnapshot.getRef().child("total").setValue(totalPrice.getText().toString())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            order.ProgressDialogDelete();
                                        }
                                    }); }


                }
                @Override
                public void  onCancelled(@NonNull DatabaseError error) {

                }
            });























//DELETE ORDER LIST








            final DatabaseReference refd;


            refd = FirebaseDatabase.getInstance().getReference().child("Order List").child("Order Wise").child(orderNo);
            Query qd=refd .orderByChild("i").equalTo(newBIllOrder.getI());

             qd.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                        String qntyOf= snapshot.child("q").getValue(String.class);

                        if(qntyOf.equals(newBIllOrder.getQ())
                               // snapshot.child(newBIllOrder.getQ()).exists()
                        ) {
                            snapshot.getRef().removeValue() .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    order.ProgressDialogDelete();
                                }
                            });
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });






//UPDATE TODAY ORDER LIST

                final DatabaseReference ref4;
                FirebaseDatabase database4=FirebaseDatabase.getInstance();
                ref4 =database4.getReference().child("Order List").child("Date Wise").child(TDate);
                Query query7 = ref4.orderByChild("i").equalTo(newBIllOrder.getI());
                query7.addListenerForSingleValueEvent (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        for (DataSnapshot DataSnapshot : snapshot.getChildren()) {


                            String ab = DataSnapshot.child("q").getValue(String.class);

                            DataSnapshot.getRef().child("q").setValue(String.format("%.0f", Double.parseDouble(ab) - Double.parseDouble(newBIllOrder.getQ())))
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            order.ProgressDialogDelete();
                                        }
                                    });

                        }

                    }
                    @Override
                    public void  onCancelled(@NonNull DatabaseError error) {

                    }
                });
































            final constructorNewOrder newOrderClass=newOrderList.get(position);


            newOrderList.remove(position);


            if (newOrderList.isEmpty()){
                totalPrice.setText("0.00");
            }
            else{
                totalingBill();
            }


            order.cartCounting();
            notifyDataSetChanged();





}









    public static void totalingBill() {

        if (!newOrderList.isEmpty()) {
            Double Dtotal = 0.0;

            for (int x = 0; x < newOrderList.size(); x++) {
               // Dtotal += Double.parseDouble(newBillList.get(x).getNSQnty()) * Double.parseDouble(newBillList.get(x).getNSPrice());

                Dtotal += Double.parseDouble(newOrderList.get(x).getQ()) * Double.parseDouble(newOrderList.get(x).getR());


                Dtotal2 = Dtotal;


                if (newOrderList.isEmpty()) {
                    STotal = "";
                    QTotal = "";
                }

            }


            STotal = String.format("%.2f", Dtotal);
            totalPrice.setText(STotal);

        } else {
            STotal = "";


        }

    }











}
