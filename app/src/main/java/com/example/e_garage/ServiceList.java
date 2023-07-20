package com.example.e_garage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import com.example.e_garage.Utility.NetworkChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceList extends AppCompatActivity {
    private Bundle bundle;
    private String division,district,mail;
    private LinearLayoutManager layoutManager;
    private List<ServiceModel> list;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ServiceListAdapter adapter;
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
        setContentView(R.layout.activity_service_list);
        bundle=getIntent().getExtras();
        division=bundle.getString("division");
        district=bundle.getString("district");
        mail=bundle.getString("trimmedMail");
        databaseReference= FirebaseDatabase.getInstance().getReference();
        list=new ArrayList<>();
        recyclerView=findViewById(R.id.serviceListRecyclerView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ServiceListAdapter(list,this);
        recyclerView.setAdapter(adapter);

        databaseReference.child("Service Posts").child(division).child(district)
                .child(mail).addValueEventListener(new ValueEventListener() {
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

    }
}