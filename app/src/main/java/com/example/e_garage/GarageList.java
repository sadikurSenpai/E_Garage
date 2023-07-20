package com.example.e_garage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GarageList extends AppCompatActivity {
    private Bundle bundle;
    private String division,district;
    private List<GarageModel> list;
    private LinearLayoutManager  layoutManager;
    private RecyclerView recyclerView;
    private GarageAdapter adapter;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_list);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        bundle=getIntent().getExtras();
        division=bundle.getString("division");
        district=bundle.getString("district");

        list=new ArrayList<>();
        recyclerView=findViewById(R.id.garageListRecyclerView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new GarageAdapter(list,this);
        recyclerView.setAdapter(adapter);

        databaseReference.child("E-Garage").child(division).child(district)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        if(snapshot.exists()){
                            for(DataSnapshot x:snapshot.getChildren()){
                                GarageModel model=x.getValue(GarageModel.class);
                                list.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else{
                list.add(new GarageModel("","","No Garage Yet!","","",""));
                            adapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}