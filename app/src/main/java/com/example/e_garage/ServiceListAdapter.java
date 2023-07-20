package com.example.e_garage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {
    private List<ServiceModel> list;
    private Context context;

    public ServiceListAdapter(List<ServiceModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.service_list_model,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.serviceName.setText(list.get(position).getServiceName());
        holder.servicePrice.setText(list.get(position).getServicePrice());
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(list.get(position).getRandomKey().equals("")){
                return;
            }

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
            String key=databaseReference.push().getKey();
            databaseReference.child("Carts").child(SplashScreen.trimmedMail)
                    .child(key).setValue(new CartModel(list.get(position).getPictureLink(),list.get(position).getServiceName(),list.get(position).getCompanyName(),list.get(position).getContactNumberOfSeller(),list.get(position).getServicePrice(),key));
                Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView serviceName,servicePrice;
        private ImageView add;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceName=itemView.findViewById(R.id.serviceListServiceName);
            servicePrice=itemView.findViewById(R.id.serviceListServicePrice);
            add=itemView.findViewById(R.id.serviceListServiceAdd);
        }
    }
}
