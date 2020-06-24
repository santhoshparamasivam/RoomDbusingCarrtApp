package com.example.mytask;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewListAdapter extends RecyclerView.Adapter<ViewListAdapter.ViewHolder>{
    private List<Productfile>productlist = new ArrayList<>();
    final List templist = new ArrayList<>();
    private Context context;
    int lastPosition = 0;
    SessionManager sessionManager;



    public ViewListAdapter(Context context, List<Productfile> productList) {
        this.context = context;
        this.productlist = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_item_view, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Productfile productfile=productlist.get(position);
        sessionManager=new SessionManager();
//         holder.Message.setVisibility(View.VISIBLE);
        if (productfile.getCount()!=null)
        if (productfile.getCount()==0){
            holder.itemname.setText(productfile.getName());
            holder.plist_text.setText(productfile.getDescription());
            holder.tv_quantity.setText(String.valueOf(productfile.getCount()));
            holder.price.setText("Rs "+String.valueOf(productfile.getPrice()));
            holder.tv_quantity.setVisibility(View.GONE);
            holder.Add.setVisibility(View.VISIBLE);
            holder.cart_minus_img.setVisibility(View.GONE);
            holder.cart_plus_img.setVisibility(View.GONE);
        }else{
            holder.itemname.setText(productfile.getName());
            holder.plist_text.setText(productfile.getDescription());
            holder.tv_quantity.setText(String.valueOf(productfile.getCount()));
            holder.price.setText("Rs "+String.valueOf(productfile.getPrice()));
            holder.tv_quantity.setVisibility(View.VISIBLE);
            holder.Add.setVisibility(View.GONE);
            holder.cart_minus_img.setVisibility(View.VISIBLE);
            holder.cart_plus_img.setVisibility(View.VISIBLE);
        }
        holder.Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_quantity.setVisibility(View.VISIBLE);
                holder.Add.setVisibility(View.GONE);
                holder.cart_minus_img.setVisibility(View.VISIBLE);
                holder.cart_plus_img.setVisibility(View.VISIBLE);
                holder.tv_quantity.setText(String.valueOf(1));
                ((ViewCart) context).AddcartValue("1","Add",productfile);
            }
        });
        holder.cart_plus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           String total=  holder.tv_quantity.getText().toString();
                int quanitiy = Integer.parseInt(total);
            if (!(Integer.parseInt(total) ==20)){
               quanitiy=Integer.parseInt(total)+1;
                 holder.tv_quantity.setText(String.valueOf(quanitiy));

                ((ViewCart) context).AddcartValue(String.valueOf(quanitiy),"Add", productfile);
            }


            }
        });
        holder.cart_minus_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String total=     holder.tv_quantity.getText().toString();
                int quanitiy = Integer.parseInt(total);
                if (Integer.parseInt(total)==1){
                    quanitiy =0;
                    holder.tv_quantity.setVisibility(View.GONE);
                    holder.Add.setVisibility(View.VISIBLE);
                    holder.cart_minus_img.setVisibility(View.GONE);
                    holder.cart_plus_img.setVisibility(View.GONE);
                    ((ViewCart) context).AddcartValue(String.valueOf(quanitiy),"minus", productfile);
                }else{
                   quanitiy=Integer.parseInt(total)-1;
                    holder.tv_quantity.setText(String.valueOf(quanitiy));
                    ((ViewCart) context).AddcartValue(String.valueOf(quanitiy),"minus", productfile);
                }

            }
        });


    }


    @Override
    public int getItemCount() {
        if (productlist != null) {
            return productlist.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView plist_text, itemname, Add, tv_quantity,price;
        ImageView cart_minus_img, cart_plus_img, Message;


        public ViewHolder(View itemView) {
            super(itemView);
            cart_minus_img = itemView.findViewById(R.id.cart_minus_img);
            cart_plus_img = itemView.findViewById(R.id.cart_plus_img);
            itemname = itemView.findViewById(R.id.from_name);
            plist_text = itemView.findViewById(R.id.plist_text);
            Add = itemView.findViewById(R.id.Add);
            tv_quantity = itemView.findViewById(R.id.cart_quantity_tv);
            Message = itemView.findViewById(R.id.Message);
            price = itemView.findViewById(R.id.price);

        }
    }
}




