package com.example.emahaschool.AddToCart;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.example.emahaschool.AddToWishListPackage.AddToWishList;
import com.example.emahaschool.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>

{
    public Context mContext;
    public List<CartItems> cartList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleCart,priceCart,productID;

        public ImageView productIMG;

        public EditText edit_qty;

        Button wishlist,del;

        public MyViewHolder(View v) {
            super(v);
            wishlist = v.findViewById(R.id.wishlist);
            del = v.findViewById(R.id.del);
            productID = v.findViewById(R.id.productID);
            titleCart = v.findViewById(R.id.titleCart);
            priceCart = v.findViewById(R.id.priceCart);
            productIMG =v.findViewById(R.id.productIMG);
            edit_qty  = v.findViewById(R.id.edit_qty);

        }
    }

    public CartAdapter(Context context, List<CartItems> cartList)
    {
        this.mContext = context;
        this.cartList = cartList;
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_to_cart_row, parent, false);
        return new MyViewHolder(itemView);
    }


    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder myViewHolder, int i)
    {
        CartItems cartitems = cartList.get(i);

        myViewHolder.productID.setText(cartitems.getproduct_ID());
        myViewHolder.titleCart.setText(cartitems.gettitlecart());
        myViewHolder.priceCart.setText(String.valueOf(cartitems.getpriceCart()));

        myViewHolder.edit_qty.setText(cartitems.getproductqty());

        Glide.with(mContext).load(cartitems.getproductIMG())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(myViewHolder.productIMG);


        myViewHolder.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return cartList.size();
    }
}
