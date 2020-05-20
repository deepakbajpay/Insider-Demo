package com.vajpayee.paytminsiderdemo.ui.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Utilities {
    public static void setImage(ImageView imageView,String url,int placeholder){
        Picasso.get().load(url).placeholder(placeholder).into(imageView);
    }
}
