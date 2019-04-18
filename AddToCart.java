package com.example.emahaschool.AddToCart;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.emahaschool.AdapterProduct;
import com.example.emahaschool.AddToWishListPackage.AddToWishList;
import com.example.emahaschool.Product;
import com.example.emahaschool.ProductListActivity;
import com.example.emahaschool.R;
import com.example.emahaschool.Shipping.Shipping;
import com.example.emahaschool.Shipping.VerificationImage;
import com.example.emahaschool.helper.EmsSqlserverDBHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddToCart extends AppCompatActivity {

    private CartAdapter cAdapter;
    EmsSqlserverDBHelper sqlserDB;
    RecyclerView recyclerView;
    Button btn,del;
    public int totalcost = 0;

    Toolbar toolbar;

    TextView productID,titleCart,priceCart,qtychkout;

    String txtproductID,txtIMGUrl,websiteURL;
    public List<CartItems> cartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        sqlserDB = new EmsSqlserverDBHelper();
        recyclerView = findViewById(R.id.cartRecylerView);

        del = findViewById(R.id.del);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar action = getSupportActionBar();

        action.setDisplayHomeAsUpEnabled(true);



        websiteURL = "http://www.emahaschool.com/";
        btn=findViewById(R.id.clkToPro);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent i = new Intent(getApplicationContext(),Shipping.class);
//                i.putExtra("titleCart",titleCart.getText().toString());
                i.putExtra("price",totalcost);
                i.putExtra("Qty",cartList.size());
                startActivity(i);

            }
        });


        cAdapter = new CartAdapter(getApplicationContext(),cartList);
        RecyclerView.LayoutManager cLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(cLayoutManager);
        recyclerView.addItemDecoration(new AddToCart.GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(cAdapter);

        prepareCartList();

    }

    public void prepareCartList() {

        try {

            JSONObject cartlistObject = new JSONObject(sqlserDB.retriveCartData());
            JSONArray cartItemsArrayList = cartlistObject.getJSONArray("cartItemsArrayList");

        if (cartItemsArrayList.length()==0)
        {
            Toast.makeText(getApplicationContext(), " No Items ", Toast.LENGTH_SHORT).show();
        }

          else
            {
            // looping through All items
            for (int i = 0; i < cartItemsArrayList.length(); i++) {
                CartItems crtitems = new CartItems();
                JSONObject c = cartItemsArrayList.getJSONObject(i);

                txtproductID = c.getString("UserID");
                crtitems.setproduct_ID(c.getString("ProductID"));
                crtitems.settitlecart(sqlserDB.getProductname(c.getString("ProductID")));

                txtIMGUrl = websiteURL + sqlserDB.getImgByProductID(c.getString("ProductID"));
                crtitems.setproductIMG(txtIMGUrl);

                crtitems.setpriceCart(c.getString("Amount"));

                totalcost = totalcost + Integer.parseInt(c.getString("Amount"));

                crtitems.setproductqty("1");

                cartList.add(crtitems);

              }
           }
        }
        catch (Exception e)
        {

        }

        cAdapter.notifyDataSetChanged();
    }

    public void delCartRow(View v){

        LinearLayout linearLayout = (LinearLayout) v.getParent().getParent();
        productID = linearLayout.findViewById(R.id.productID);
        titleCart = linearLayout.findViewById(R.id.titleCart);
        priceCart = linearLayout.findViewById(R.id.priceCart);
        String prodID = productID.getText().toString();

        Boolean deleteRow = sqlserDB.deleteCartItems(prodID);

        if (deleteRow)
        {
            Toast.makeText(getApplicationContext(), "Item Removed from Cart", Toast.LENGTH_SHORT).show();
        }

        else
        {
            Toast.makeText(getApplicationContext(), "Unable To Remove", Toast.LENGTH_SHORT).show();
        }

        finish();
        startActivity(getIntent());
    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge)
        {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount)
                { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void getWishList(View v)
    {
//        LinearLayout rlayout = (LinearLayout) v.getParent();
//        likefloating = rlayout.findViewById(R.id.wishlist);

        Intent i = new Intent(getApplicationContext(), AddToWishList.class);
        startActivity(i);

    }

//    public void proceed(View v){
//
//        Intent i = new Intent(getApplicationContext(),Shipping.class);
//        startActivity(i);
//
//    }

}