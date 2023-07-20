package com.example.e_garage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_garage.Utility.NetworkChangeListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInAs extends AppCompatActivity {
    private Button sellerButton,buyerButton;
    private FirebaseAuth mAuth;
    private String mail="";
    private String trimmed="";
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
        setContentView(R.layout.activity_log_in_as);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        sellerButton=findViewById(R.id.logInAsSeller);
        buyerButton=findViewById(R.id.logInAsBuyer);

        mAuth=FirebaseAuth.getInstance();
        mail=mAuth.getCurrentUser().getEmail();
        trimmed=mail.replace("@","");
        trimmed=trimmed.replace(".","");

        sellerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Users").child(trimmed).child("occupation").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String s=snapshot.getValue(String.class);
                            if(s.equals("seller")){
                                databaseReference.child("Users").child(trimmed).child("logInAs").setValue("seller");
                                startActivity(new Intent(getApplicationContext(),SplashScreen.class));
                            }
                            else{
                                AlertDialog.Builder dialogExit = new AlertDialog.Builder(LogInAs.this);
                                View layout_dialog = LayoutInflater.from(LogInAs.this).inflate(R.layout.pay_now_dialogue_box_1, null);
                                dialogExit.setView(layout_dialog);
                                dialogExit.setTitle("Payment !!");
                                dialogExit.setMessage("Please pay 500 tk");

                                dialogExit.setPositiveButton("PAY", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        databaseReference.child("Users").child(trimmed).child("logInAs").setValue("seller");
                                        databaseReference.child("Users").child(trimmed).child("occupation").setValue("seller");
                                        startActivity(new Intent(getApplicationContext(),SplashScreen.class));
                                    }
                                });

                                dialogExit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog dialog = dialogExit.create();
                                dialog.show();
                                dialog.setCancelable(false);
                                dialog.getWindow().setGravity(Gravity.CENTER);
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        buyerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Users").child(trimmed).child("logInAs").setValue("buyer");
                startActivity(new Intent(getApplicationContext(),SplashScreen.class));
            }
        });




    }
}