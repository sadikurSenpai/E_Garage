package com.example.e_garage.ui.dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_garage.CartAdapter;
import com.example.e_garage.CartModel;
import com.example.e_garage.LogIn;
import com.example.e_garage.ModelUser;
import com.example.e_garage.R;
import com.example.e_garage.SplashScreen;
import com.example.e_garage.databinding.FragmentDashboardBinding;
import com.example.e_garage.register;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    private Button payNowButton;
    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<CartModel> list;
    private CartAdapter adapter;
    private DatabaseReference databaseReference;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        list=new ArrayList<>();
        payNowButton=binding.payNowButton;
        recyclerView=binding.cartRecyclerView;
        layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new CartAdapter(list,getContext());
        recyclerView.setAdapter(adapter);

        databaseReference.child("Carts").child(SplashScreen.trimmedMail)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        if(snapshot.exists()){
                            for(DataSnapshot x:snapshot.getChildren()){
                                CartModel model=x.getValue(CartModel.class);
                                list.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            list.add(new CartModel("","Nothing in cart yet !","","","",""));
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    payNowButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            AlertDialog.Builder dialogExit = new AlertDialog.Builder(getContext());
                                            View layout_dialog = LayoutInflater.from(getContext()).inflate(R.layout.pay_now_dialogue_box, null);
                                            dialogExit.setView(layout_dialog);
                                            dialogExit.setTitle("Payment !!");

                                            dialogExit.setPositiveButton("PAY", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i)
                                                {
                                                    databaseReference.child("Carts").child(SplashScreen.trimmedMail).removeValue();
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
                                    });


//

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}