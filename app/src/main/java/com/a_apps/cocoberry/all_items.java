package com.a_apps.cocoberry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.R.layout;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class all_items extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private static final int PICK_IMAGE_REQUEST = 234;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    onClickInterface onclickInterface;


    DatabaseReference mbase;
    adapterAllItems adapter;
    List<constructorItem> list = new ArrayList<>();
    EditText searchView;

    FloatingActionButton btnAddNewItem;

    ImageButton backBtn;







    ImageButton fav, bakes, cool, hot , others;
    final Context c = this;

    String na,pr,di,fa,ca,il;
    int caNo;

     CircleImageView imageView;
    private Uri filePath;
    Boolean favBoolean=false;

    StorageReference storageReference;
    FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);

        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#0a1f30"));

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        backBtn=findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });



        fav = findViewById(R.id.fav);
        bakes = findViewById(R.id.bakes);
        cool = findViewById(R.id.cool);
        hot = findViewById(R.id.hot);
        others = findViewById(R.id.others);


        btnAddNewItem=findViewById(R.id.btnNewItem);
        btnAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewItem();
            }
        });



        recyclerView = findViewById(R.id.allItemList);
        searchView = findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Fav();
        onClickAdd();



        bakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bakes();
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






















    //Database Fav
    private void Fav() {


        progressDialog = new ProgressDialog(all_items.this);
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

                adapter = new adapterAllItems(getApplicationContext(), list, onclickInterface);
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

        progressDialog = new ProgressDialog(all_items.this);
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

                adapter = new adapterAllItems(getApplicationContext(), list, onclickInterface);
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

        progressDialog = new ProgressDialog(all_items.this);
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

                adapter = new adapterAllItems(getApplicationContext(), list, onclickInterface);
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

        progressDialog = new ProgressDialog(all_items.this);
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

                adapter = new adapterAllItems(getApplicationContext(), list, onclickInterface);
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

        progressDialog = new ProgressDialog(all_items.this);
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

                adapter = new adapterAllItems(getApplicationContext(), list, onclickInterface);
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











    private void onClickAdd() {
        onclickInterface = new onClickInterface() {

            @Override
            public void setClick(int abc) {

             //   Toast.makeText(getApplicationContext(), "Successsssss", Toast.LENGTH_LONG).show();



            }

            @Override
            public void itemDetails(final String itemN, String P, String itemImage) {



                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.edit_item, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c, R.style.CustomDialog2);
                alertDialogBuilderUserInput.setView(mView);
                final AlertDialog dialog = alertDialogBuilderUserInput.create();


                final String[] categoryList = { "Bakes","Hot","Cool" ,"Others"};

                final ProgressDialog progressDialog2 = new ProgressDialog(mView.getContext());
                progressDialog2.setTitle("Loading");
                progressDialog2.show();


                imageView =mView.findViewById(R.id.image);
               final EditText   name=mView.findViewById(R.id.names);
               final EditText price=mView.findViewById(R.id.price);
               final EditText description=mView.findViewById(R.id.description);

               final ImageButton favBtn=mView.findViewById(R.id.favBtn);
               final Spinner spin = mView.findViewById(R.id.category);



                final ImageButton backBtn2=mView.findViewById(R.id.backBtn2);
                backBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        back2();
                    }
                });



              name.setText("This sets the text.");

                mbase = FirebaseDatabase.getInstance().getReference();
                Query queryB = mbase.child("Items").orderByChild("itemName").equalTo(itemN);

                queryB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                            constructorItem  CI = postSnapshot.getValue(constructorItem.class);

                            na=CI.getItemName();
                            pr=CI.getRate();
                            ca=CI.getCat();
                            di=CI.getDesc();
                            fa=CI.getFav();
                            il=CI.getImageLink();

                           progressDialog2.dismiss();
                        }

                      //  Toast.makeText(getApplicationContext(), na, Toast.LENGTH_LONG).show();


                        name.setText(na);
                        price.setText(pr);
                        description.setText(di);

                        if(fa.equals("True")) {
                            favBtn.setColorFilter(Color.parseColor("#FFB61B"));
                        }

                        Glide.with(imageView.getContext()).load(il).into(imageView);

                        if(ca.equals("Bakes")){
                         caNo=0;
                        }

                        if(ca.equals("Hot")){
                            caNo=1;
                        }
                        if(ca.equals("Cool")){
                            caNo=2;
                        }
                        if(ca.equals("Others")){
                            caNo=3;
                        }





                        spin.setOnItemSelectedListener(all_items.this);
                        ArrayAdapter ad = new ArrayAdapter(all_items.this,  R.layout.spinner_style, categoryList);
                        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spin.setAdapter(ad);
                        spin.setSelection(caNo);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {



                    }
                });

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showFileChooser();
                    }
                });







             //  final CircleImageView image = (CircleImageView) mView.findViewById(R.id.aiImage);
               // GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gifImageView);
               // Glide.with(imageView.getContext()).load(itemImage).into(imageView);
 /*

                final TextView itemView = mView.findViewById(R.id.itemView);
                final EditText qntyView = mView.findViewById(R.id.qntyView);


  */




                favBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (favBoolean == true) {
                            favBoolean =false;
                            fa="False";
                            favBtn.setColorFilter(Color.parseColor("#230A1F30"));
                        }


                        else {
                            favBoolean =true;
                            fa="True";
                            favBtn.setColorFilter(Color.parseColor("#FFB61B"));
                        }


                    }
                });


                final Button updateBtn = mView.findViewById(R.id.updateBtn);
                updateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (filePath == null) {

                            Query ref;
                            ref = FirebaseDatabase.getInstance().getReference().child("Items").orderByChild("itemName").equalTo(itemN);
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        ds.getRef().child("itemName").setValue(name.getText().toString());
                                        ds.getRef().child("rate").setValue(price.getText().toString());
                                        ds.getRef().child("cat").setValue(spin.getSelectedItem().toString());
                                        ds.getRef().child("desc").setValue(description.getText().toString());
                                        ds.getRef().child("fav").setValue(fa);


                                    }

                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }


                        if (filePath != null) {

                            //displaying a progress dialog while upload is going on
                            final ProgressDialog progressDialog = new ProgressDialog(c);
                            progressDialog.setTitle("Uploading");
                            progressDialog.show();

                            Toast.makeText(getApplicationContext(), "File Uploading ", Toast.LENGTH_LONG).show();



                            final StorageReference riversRef = storageReference.child(
                                    "images/"
                                            + name.getText().toString().trim());
                            riversRef.putFile(filePath)


                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                            riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(final Uri uri) {


                                                    Query ref;
                                                    ref = FirebaseDatabase.getInstance().getReference().child("Items").orderByChild("itemName").equalTo(itemN);
                                                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            for (DataSnapshot ds : snapshot.getChildren()) {
                                                                ds.getRef().child("itemName").setValue(name.getText().toString());
                                                                ds.getRef().child("rate").setValue(price.getText().toString());
                                                                ds.getRef().child("cat").setValue(spin.getSelectedItem().toString());
                                                                ds.getRef().child("desc").setValue(description.getText().toString());
                                                                ds.getRef().child("fav").setValue(fa);
                                                              ds.getRef().child("imageLink").setValue(uri.toString());


                                                            }

                                                        }


                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                                }
                                            });


                                            progressDialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                                        }
                                    })

                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {

                                            progressDialog.dismiss();

                                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();


                                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                                        }
                                    });

                        }

back2();

                    }
                });

                final Button deleteBtn = mView.findViewById(R.id.deleteBtn);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Query ref;
                        ref = FirebaseDatabase.getInstance().getReference().child("Items").orderByChild("itemName").equalTo(itemN);
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    ds.getRef().removeValue();

                                }

                                back2();

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                });

                //openProductsMain();





                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }

            @Override
            public void itemRate(String itemR) {

            }
        };
    }


    private void showFileChooser() {
        int MY_PERMISSION_STORAGE = 0;

       

            if (ContextCompat.checkSelfPermission(all_items.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(all_items.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(all_items.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
                ActivityCompat.requestPermissions(all_items.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
                // showToast("Need Permission to access and upload your Image");
            } else {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1) //You can skip this for free form aspect ratio)
                        .start(all_items.this);
            }
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                filePath=result.getUri();
                // Set uri as Image in the ImageView:
                // imageView.ser(filePath);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(bitmap);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private void back() {
        finish();
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }

    private void back2() {
        finish();
        Intent intent = new Intent(this, all_items.class);
        startActivity(intent);
    }

    private void openNewItem() {
        Intent intent=new Intent(this,add_item.class);
        startActivity(intent);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
