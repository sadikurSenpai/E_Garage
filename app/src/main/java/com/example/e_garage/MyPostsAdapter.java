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

import java.sql.ClientInfoStatus;
import java.util.List;

public class MyPostsAdapter extends RecyclerView.Adapter<MyPostsAdapter.ViewHolder> {
    private List<MyPostsModel> list;
    private Context context;

    public MyPostsAdapter(List<MyPostsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyPostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_posts_model,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.productName.setText(list.get(position).getProductName());
        holder.productPrice.setText(list.get(position).getProductPrice());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getRandomKey().equals("")){
                    return;
                }

                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
                databaseReference.child("MyPosts").child(list.get(position).getType())
                        .child(SplashScreen.trimmedMail).child(list.get(position).getRandomKey()).removeValue();
                databaseReference.child(list.get(position).getType())
                        .child(list.get(position).getRandomKey()).removeValue();
                Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName,productPrice;
        private ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName=itemView.findViewById(R.id.myPostsModelName);
            productPrice=itemView.findViewById(R.id.myPostsModelPrice);
            delete=itemView.findViewById(R.id.myPostsModelDelete);;
        }
    }
}
