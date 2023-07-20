package com.example.e_garage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_garage.Utility.NetworkChangeListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Accessories extends AppCompatActivity {
    private Button postButton;
    private TextView title;
    private Bundle bundle;
    private String type;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AccessoriesAdapter adapter;
    private List<ProductModel> list;
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
        setContentView(R.layout.activity_accessories);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        title=findViewById(R.id.accessoriesTitle);
        bundle=getIntent().getExtras();
        type=bundle.getString("type");
        title.setText(type);
        this.setTitle(type);
        postButton=findViewById(R.id.accessoriesPostButton);
        if(SplashScreen.logInAs.equals("buyer")){
            postButton.setEnabled(false);
            postButton.setAlpha(0f);
        }

        list=new ArrayList<>();
        recyclerView=findViewById(R.id.accessoriesRecyclerView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new AccessoriesAdapter(list,this);
        recyclerView.setAdapter(adapter);

        databaseReference.child(type).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if(snapshot.exists()){
                    for (DataSnapshot x:snapshot.getChildren()){
                        ProductModel model=x.getValue(ProductModel.class);
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }
                else {
                   list.add(new ProductModel(SplashScreen.contactNumber,type,"No product Yet !","","","","",""));
                adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Accessories.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void AddAccessories(View view) {
        Intent intent=new Intent(this,AddAPost.class);
        intent.putExtra("type",type);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.myPosts:
                if(SplashScreen.logInAs.equals("seller")){
                    Intent iMP=new Intent(this,MyPosts.class);
                    iMP.putExtra("type",type);
                    startActivity(iMP);
                }
                else{
                    Toast.makeText(this, "Not Available!", Toast.LENGTH_SHORT).show();
                }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}