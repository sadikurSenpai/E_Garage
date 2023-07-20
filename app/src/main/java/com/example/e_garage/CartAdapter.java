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

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartModel> list;
    private Context context;

    public CartAdapter(List<CartModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_model,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getProductImage()).into(holder.imageView);
        holder.productName.setText(list.get(position).getProductName());
        holder.productCompanyName.setText(list.get(position).getProductCompanyName());
        holder.productPrice.setText(list.get(position).getProductPrice());
        holder.contactNumber.setText(list.get(position).getContactNumber());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getProductCompanyName().equals("") ){
                    return;
                }
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Carts").child(SplashScreen.trimmedMail)
                        .child(list.get(position).getRandomKey()).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView,remove;
        private TextView productName,productCompanyName,productPrice,contactNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.individualCartModelImage);
            remove=itemView.findViewById(R.id.individualCartModelRemove);
            productName=itemView.findViewById(R.id.individualCartModelProductName);
            productCompanyName=itemView.findViewById(R.id.individualCartModelCompanyName);
            productPrice=itemView.findViewById(R.id.individualCartModelPrice);
            contactNumber=itemView.findViewById(R.id.individualCartModelPhoneNumber);
        }
    }
}
