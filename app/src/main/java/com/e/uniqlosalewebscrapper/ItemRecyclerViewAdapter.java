package com.e.uniqlosalewebscrapper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.net.Uri;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "ItemRecyclerViewAdapter";
    private ArrayList<Item> items;
    private Context mContext;
    public ItemRecyclerViewAdapter(Context ctx, ArrayList<Item> items) {
        mContext = ctx;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem2, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Glide.with(mContext)
                .asBitmap()
                .load(items.get(position).getImageLink())
                .into(holder.productImage);
        holder.productName.setText(items.get(position).getName());
        holder.productOrigPrice.setText(items.get(position).getOrigPrice());
        holder.productOrigPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productSalePrice.setText( items.get(position).getSalePrice());
        holder.parentLayout.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                Log.d(TAG, "onLongClick: clicked on " + items.get(position).getName());
                String url = items.get(position).getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                mContext.startActivity(i);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView productName;
        TextView productOrigPrice;
        TextView productSalePrice;
        ConstraintLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productOrigPrice = itemView.findViewById(R.id.productOrigPrice);
            productSalePrice = itemView.findViewById(R.id.productSalePrice);
            parentLayout = itemView.findViewById(R.id.parent_layout2);
        }
    }
}
