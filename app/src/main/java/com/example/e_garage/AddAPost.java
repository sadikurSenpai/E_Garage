package com.example.e_garage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.e_garage.Utility.NetworkChangeListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddAPost extends AppCompatActivity {
    private Bundle bundle;
    private String type;
    private EditText productName,companyName,sellingPrice,MRP,description;
    private ImageView imageView;
    private Button createButton;
    private ProgressBar progressBar;
    private String pictureLink="";
    final int code = 999;
    private DatabaseReference databaseReference;
    private NetworkChangeListener networkChangeListener=new NetworkChangeListener();
    @Override
    protected void onStart() {
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apost);
        bundle=getIntent().getExtras();
        type=bundle.getString("type");
        databaseReference= FirebaseDatabase.getInstance().getReference();
        progressBar=findViewById(R.id.addProductProgressBar);
        createButton=findViewById(R.id.addProductCreateButton);
        imageView=findViewById(R.id.addProductChoosePicture);
        productName=findViewById(R.id.addProductProductName);
        companyName=findViewById(R.id.addProductCompanyName);
        sellingPrice=findViewById(R.id.addProductSellingPicture);
        MRP=findViewById(R.id.addProductMRP);
        description=findViewById(R.id.addProductDescription);




        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productNameString=productName.getText().toString();
                String companyNameString=companyName.getText().toString();
                String sellingPriceString=sellingPrice.getText().toString();
                String MRPString=MRP.getText().toString();
                String descriptionString=description.getText().toString();
                if(productNameString.isEmpty()){
                    productName.setError("Fill Up");
                    productName.requestFocus();
                    return;
                }
                if(companyNameString.isEmpty()){
                    companyName.setError("Fill Up");
                    companyName.requestFocus();
                    return;
                }
                if(sellingPriceString.isEmpty()){
                    sellingPrice.setError("Fill Up");
                    sellingPrice.requestFocus();
                    return;
                }
                if(MRPString.isEmpty()){
                    MRP.setError("Fill Up");
                    MRP.requestFocus();
                    return;
                }
                if(descriptionString.isEmpty()){
                    description.setError("Fill Up");
                    description.requestFocus();
                    return;
                }
                if(pictureLink.isEmpty()){
                    Toast.makeText(AddAPost.this, "Upload A picture !", Toast.LENGTH_SHORT).show();
                    return;
                }

                String key=databaseReference.push().getKey();
                databaseReference.child(type).child(key).setValue(new ProductModel(SplashScreen.contactNumber,type,productNameString,companyNameString,sellingPriceString,MRPString,descriptionString,pictureLink));
                databaseReference.child("MyPosts").child(type).child(SplashScreen.trimmedMail).child(key).setValue(new MyPostsModel(type,key,productNameString,sellingPriceString,SplashScreen.contactNumber));
                Toast.makeText(AddAPost.this, "Added Post!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainPage.class));
                finish();
            }
        });
    }
    public void signUpPictureChoosingButton(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == code && resultCode == Activity.RESULT_OK && data != null) {
            progressBar.setVisibility(View.VISIBLE);
            createButton.setEnabled(false);
            Uri result = data.getData();
            String key= databaseReference.push().getKey();
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("picLink"+SplashScreen.trimmedMail+key);
            storageRef.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            pictureLink=uri.toString();

                            imageView.setImageURI(result);
                            progressBar.setVisibility(View.INVISIBLE);
                            createButton.setEnabled(true);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Couldn't Upload !!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}