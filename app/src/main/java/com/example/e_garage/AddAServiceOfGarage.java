package com.example.e_garage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_garage.Utility.NetworkChangeListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAServiceOfGarage extends AppCompatActivity {
    private EditText serviceName,servicePrice;
    private String serviceNameString,servicePriceString;
    private Button addButton;
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
        setContentView(R.layout.activity_add_aservice_of_garage);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        serviceName=findViewById(R.id.addAServiceOfGarageServiceName);
        servicePrice=findViewById(R.id.addAServiceOfGarageServicePrice);
        addButton=findViewById(R.id.addAServiceOfGarageAddButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceNameString=serviceName.getText().toString();
                servicePriceString=servicePrice.getText().toString();
                if(serviceNameString.isEmpty()){
                    serviceName.setError("Please enter !");
                    serviceName.requestFocus();
                    return;
                }
                if(servicePriceString.isEmpty()){
                    servicePrice.setError("Please enter !");
                    servicePrice.requestFocus();
                    return;
                }

                String key=databaseReference.push().getKey();
                databaseReference.child("Service Posts").child(SplashScreen.division).child(SplashScreen.district)
                        .child(SplashScreen.trimmedMail).child(key)
                        .setValue(new ServiceModel(key,serviceNameString,servicePriceString,SplashScreen.trimmedMail,SplashScreen.contactNumber,SplashScreen.garagePictureLink,SplashScreen.garageName) );
                Toast.makeText(AddAServiceOfGarage.this, "Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddAServiceOfGarage.this,MyGarage.class));
                finish();
            }
        });
    }
}