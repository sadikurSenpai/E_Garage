package com.example.e_garage.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_garage.Accessories;
import com.example.e_garage.AllGarage;
import com.example.e_garage.Location;
import com.example.e_garage.LogIn;
import com.example.e_garage.R;
import com.example.e_garage.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private CardView accessories,modifiedVehicles,allGarage;
    private TextView eGarage;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        allGarage=binding.activityMainAllGarage;
        accessories=binding.activityMainAccessories;
        modifiedVehicles=binding.activityMainModifiedVehicles;
        eGarage=binding.activityMainEGarage;

        allGarage.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                    startActivity(new Intent(getContext(), AllGarage.class));
                                         }
                                     });

                accessories.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getContext(), Accessories.class);
                        i.putExtra("type", "Accessories");
                        startActivity(i);
                    }
                });

        modifiedVehicles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(getContext(), Accessories.class);
                i1.putExtra("type","Modified Vehicles");
                startActivity(i1);
            }
        });

        eGarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(getContext(), Location.class);
                startActivity(i2);
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}