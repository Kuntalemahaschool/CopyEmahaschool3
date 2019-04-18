package com.example.emahaschool.AddToCart;

import android.view.View;

public class CartItems
{

    public String titleCart,priceCart,productIMG,prod_id,qty;


    public void settitlecart(String titleCart)
    {
        this.titleCart = titleCart;
    }

    public String gettitlecart()
    {
        return titleCart;
    }


    public void setpriceCart(String priceCart)
    {
        this.priceCart = priceCart;
    }

    public String getpriceCart()
    {
        return priceCart;
    }

    public void setproductIMG(String productIMG)
    {
        this.productIMG = productIMG;
    }

    public String getproductIMG()
    {
        return productIMG;
    }

    public void setproductqty(String qty){
        this.qty = qty;
    }

    public  String getproductqty(){
        return qty;
    }

    public void setproduct_ID(String prod_id) { this.prod_id =  prod_id;}

    public String getproduct_ID(){ return prod_id; }
}
