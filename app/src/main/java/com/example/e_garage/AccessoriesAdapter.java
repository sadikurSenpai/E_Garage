package com.example.e_garage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AccessoriesAdapter extends RecyclerView.Adapter<AccessoriesAdapter.ViewHolder> {
    private List<ProductModel> list;
    private Context context;

    public AccessoriesAdapter(List<ProductModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AccessoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.accesssories_model,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccessoriesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        if(list.get(position).getProductName().equals("No product Yet !")){
//            holder.productName.setText("No Product Yet !");
//            return;
//        }
        Glide.with(context).load(list.get(position).getPictureLink()).into(holder.imageView);
        holder.productName.setText(list.get(position).getProductName());
        holder.price.setText(list.get(position).getProductSellingPrice());

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getProductName().equals("No product Yet !")){
                    return;
                }
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                String key=databaseReference.push().getKey();
                databaseReference.child("Carts").child(SplashScreen.trimmedMail)
                        .child(key).setValue(new CartModel(list.get(position).getPictureLink(),list.get(position).getProductName(),list.get(position).getProductCompanyName(),list.get(position).getContactNumber(),list.get(position).getProductSellingPrice(),key));
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getProductName().equals("No product Yet !")){
                    return;
                }
                Intent intent=new Intent(context,ProductDetails.class);
                intent.putExtra("productName",list.get(position).getProductName());
                intent.putExtra("companyName",list.get(position).getProductCompanyName());
                intent.putExtra("sellingPrice",list.get(position).getProductSellingPrice());
                intent.putExtra("MRP",list.get(position).getProductMRP());
                intent.putExtra("description",list.get(position).getProductDescription());
                intent.putExtra("pictureLink",list.get(position).getPictureLink());
                intent.putExtra("contactNumber",list.get(position).getContactNumber());
                intent.putExtra("type",list.get(position).getType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView productName,price,details,add;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.categoryProductImage);
            productName=itemView.findViewById(R.id.categoryProductName);
            price=itemView.findViewById(R.id.categoryProductPrice);
            details=itemView.findViewById(R.id.categoryProductDetails);
            add=itemView.findViewById(R.id.categoryProductAdd);
        }
    }
}
