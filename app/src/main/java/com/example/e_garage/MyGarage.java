package com.example.e_garage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_garage.Utility.NetworkChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyGarage extends AppCompatActivity {
    private TextView garageName;
    private ImageView garageImage;
    private Button postButton;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<ServiceModel> list;
    private MyGarageServiceAdapter adapter;
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
        setContentView(R.layout.activity_my_garage);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        garageName=findViewById(R.id.myGarageName);
        garageImage=findViewById(R.id.myGarageImageView);
        postButton=findViewById(R.id.myGaragePostButton);
        garageName.setText(SplashScreen.garageName);
        Glide.with(this).load(SplashScreen.garagePictureLink).into(garageImage);

        list=new ArrayList<>();
        recyclerView=findViewById(R.id.myGarageRecyclerView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MyGarageServiceAdapter(list,this);
        recyclerView.setAdapter(adapter);

        databaseReference.child("Service Posts").child(SplashScreen.division).child(SplashScreen.district).child(SplashScreen.trimmedMail)
                        .addValueEventListener(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                         list.clear();
                                         if(snapshot.exists()){
                                             for(DataSnapshot x:snapshot.getChildren()){
                                                 ServiceModel model=x.getValue(ServiceModel.class);
                                                 list.add(model);
                                             }
                                             adapter.notifyDataSetChanged();
                                         }
                                         else{
                               list.add(new ServiceModel("","No Service Yet!","","","","","")  );
                               adapter.notifyDataSetChanged();
                                         }
                                                   }

                                                   @Override
                                                   public void onCancelled(@NonNull DatabaseError error) {

                                                   }
                                               });


                                postButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        startActivity(new Intent(MyGarage.this, AddAServiceOfGarage.class));
                                        finish();
                                    }
                                });
    }
}