package com.example.e_garage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.e_garage.Utility.NetworkChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPosts extends AppCompatActivity {
    private Bundle bundle;
    private String type;
    private List<MyPostsModel> list;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MyPostsAdapter adapter;
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
        setContentView(R.layout.activity_my_posts);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        bundle=getIntent().getExtras();
        type=bundle.getString("type");

        list=new ArrayList<>();
        recyclerView=findViewById(R.id.myPostRecyclerView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MyPostsAdapter(list,this);
        recyclerView.setAdapter(adapter);

        databaseReference.child("MyPosts").child(type).child(SplashScreen.trimmedMail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for (DataSnapshot x:snapshot.getChildren()){
                        MyPostsModel model=x.getValue(MyPostsModel.class);
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                list.add(new MyPostsModel("","","Nothing Found!","",""));
                adapter.notifyDataSetChanged();
//                    Toast.makeText(MyPosts.this, "Nothing Found !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyPosts.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}