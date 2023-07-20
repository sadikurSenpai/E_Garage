package com.example.e_garage.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.e_garage.LogIn;
import com.example.e_garage.SplashScreen;
import com.example.e_garage.databinding.FragmentNotificationsBinding;
import com.google.firebase.auth.FirebaseAuth;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private ImageView profilePicture;
    private TextView profileName,profileContactNumber,profileGarageName;
    private Button logOutButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        profilePicture=binding.profilePicture;
        profileName=binding.profileName;
        profileContactNumber=binding.profileContactNumber;
        profileGarageName=binding.profileGarageName;
        logOutButton=binding.logoutButton;

        Glide.with(this).load(SplashScreen.pictureLink).into(profilePicture);
        profileName.setText(SplashScreen.name);
        profileContactNumber.setText(SplashScreen.contactNumber);
        profileGarageName.setText(SplashScreen.garageName);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth ProfileAuth=FirebaseAuth.getInstance();
                ProfileAuth.signOut();
                startActivity(new Intent(getContext(), LogIn.class));
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