package com.example.e_garage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyGarageServiceAdapter extends RecyclerView.Adapter<MyGarageServiceAdapter.ViewHolder> {
   private List<ServiceModel> list;
   private Context context;

    public MyGarageServiceAdapter(List<ServiceModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyGarageServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_garage_service_model,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGarageServiceAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
    holder.serviceName.setText(list.get(position).getServiceName());
    holder.servicePrice.setText(list.get(position).getServicePrice());
    holder.delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        if(list.get(position).getRandomKey().equals("")){
            return;
        }
            DatabaseReference databaseReference= FirebaseDatabase.
                    getInstance().getReference();

        databaseReference.child("Service Posts").child(SplashScreen.division).child(SplashScreen.district).child(SplashScreen.trimmedMail)
                .child(list.get(position).getRandomKey()).removeValue();
        }
    });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView serviceName,servicePrice;
        private ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName=itemView.findViewById(R.id.myGarageServiceName);
            servicePrice=itemView.findViewById(R.id.myGarageServicePrice);
            delete=itemView.findViewById(R.id.myGarageServiceDelete);

        }
    }
}
