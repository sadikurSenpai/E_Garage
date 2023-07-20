package com.example.e_garage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_garage.Utility.NetworkChangeListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductDetails extends AppCompatActivity {
    private Bundle bundle;
    private String contactNumberString,productNameString,companyNameString,sellingPriceString,MRPString,descriptionString,pictureLinkString,typeString;
    private ImageView imageView;
    private TextView productName,companyName,sellingPrice,MRP,description;
    private Button addToCart;
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
        setContentView(R.layout.activity_product_details);
        bundle=getIntent().getExtras();
        contactNumberString=bundle.getString("contactNumber");
        productNameString=bundle.getString("productName");
        companyNameString=bundle.getString("companyName");
        sellingPriceString=bundle.getString("sellingPrice");
        MRPString=bundle.getString("MRP");
        descriptionString=bundle.getString("description");
        pictureLinkString=bundle.getString("pictureLink");
        typeString=bundle.getString("type");

        imageView=findViewById(R.id.productDetailsImage);
        productName=findViewById(R.id.productDetailsProductName);
        companyName=findViewById(R.id.productDetailsCompanyName);
        sellingPrice=findViewById(R.id.productDetailsSellingPrice);
        MRP=findViewById(R.id.productDetailsMRP);
        description=findViewById(R.id.productDetailsDetails);
        addToCart=findViewById(R.id.productDetailsAddButton);


        productName.setText(productNameString);
        companyName.setText(companyNameString);
        sellingPrice.setText("Price: "+sellingPriceString+" BDT");
        MRP.setText("MRP: "+MRPString+" BDT");
        description.setText(descriptionString);
        Glide.with(this).load(pictureLinkString).into(imageView);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                String key=databaseReference.push().getKey();
                databaseReference.child("Carts").child(SplashScreen.trimmedMail)
                        .child(key).setValue(new CartModel(pictureLinkString,productNameString,companyNameString,contactNumberString,sellingPriceString,key));
                Toast.makeText(ProductDetails.this, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}