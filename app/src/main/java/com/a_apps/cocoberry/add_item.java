package com.a_apps.cocoberry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class add_item extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    String[] categoryList = { "Bakes","Hot","Cool" ,"Others"};


    private static final int PICK_IMAGE_REQUEST = 234;

    private EditText name,price,description;
    ImageButton favBtn;



    private Button buttonUpload;

    StorageReference storageReference;
    FirebaseStorage storage;

    private CircleImageView imageView;

    private Uri filePath;

    DatabaseReference ref1;

    Spinner spin;


    Boolean favBoolean=false;

    String Category;
    String Favoutite="False";

    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        getSupportActionBar().hide();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#0a1f30"));



        backBtn=findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        buttonUpload = findViewById(R.id.buttonUpload);


        imageView = findViewById(R.id.image);
        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        description=findViewById(R.id.description);

        favBtn=findViewById(R.id.favBtn);









        spin = findViewById(R.id.category);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(ad);


        setFavourite();




        imageView.setOnClickListener(new View.OnClickListener() {
            private int MY_PERMISSION_STORAGE;

            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(add_item.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(add_item.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(add_item.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
                    ActivityCompat.requestPermissions(add_item.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
                   // showToast("Need Permission to access and upload your Image");
                } else {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(1, 1) //You can skip this for free form aspect ratio)
                            .start(add_item.this);
                }









                //showFileChooser();
            }


        });





        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                uploadFile();
            }
        });



    }

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

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    */


    private void uploadFile() {

        if (filePath != null) {



            final String N = name.getText().toString();
            final String P = price.getText().toString().trim();
            final String C = spin.getSelectedItem().toString();
            final String D = description.getText().toString().trim();

            if (  N.length()>0   &&   P.length()>0   &&   D.length()>0   &&  C.length()>0  ) {


                //displaying a progress dialog while upload is going on
                final ProgressDialog progressDialog = new ProgressDialog(this);
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
                                    public void onSuccess(Uri uri) {
                                        constructorItem CI = new constructorItem();
                                        CI.setItemName(N);
                                        CI.setImageLink(uri.toString());
                                        CI.setRate(P);
                                        CI.setDesc(D);
                                        CI.setFav(Favoutite);
                                        CI.setCat(C);

                                        Toast.makeText(getApplicationContext(), N+P+C+D+Favoutite, Toast.LENGTH_LONG).show();

                                        ref1 = FirebaseDatabase.getInstance().getReference().child("Items");
                                        String UserId = ref1.push().getKey();
                                        ref1.child(UserId).setValue(CI);


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

            } else {

                Toast.makeText(getApplicationContext(), "Complete All Fields", Toast.LENGTH_LONG).show();

            }


        }
        else {

            Toast.makeText(getApplicationContext(), "Choose Image", Toast.LENGTH_LONG).show();
        }


    }



    private void setFavourite(){

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (favBoolean == true) {
                    favBoolean =false;
                    Favoutite="False";
                    favBtn.setColorFilter(Color.parseColor("#230A1F30"));
                }


                else {
                    favBoolean =true;
                    Favoutite="True";
                    favBtn.setColorFilter(Color.parseColor("#FFB61B"));
                }


            }
        });


    }

//back
    private void back() {
        finish();
        Intent intent = new Intent(this, all_items.class);
        startActivity(intent);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Category= categoryList[position];

        Toast.makeText(getApplicationContext(), Category, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
