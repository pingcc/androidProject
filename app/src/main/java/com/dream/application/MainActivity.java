package com.dream.application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.user.fun.library.util.ImageUtils;

public class MainActivity extends AppCompatActivity {
    public String url="http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView=findViewById(R.id.image);
//        ImageUtils.loadImage(R.mipmap.ic_launcher,imageView);
        ImageUtils.loadImage(url,imageView);


    }
}
