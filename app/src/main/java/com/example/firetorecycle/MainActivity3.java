package com.example.firetorecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity3 extends AppCompatActivity {
    ImageView img;
    Context context;
    TextView tv1,tv2,tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);



            img=(ImageView)findViewById(R.id.img1);
            tv1=(TextView)findViewById(R.id.desc_header);
            tv2=(TextView)findViewById(R.id.desc_desc);
        tv3=(TextView)findViewById(R.id.desc_desc1);

        String a = getIntent().getStringExtra("img");
        Glide.with(this)
                .load(a)
                .into(img);

        // img.setImageResource(getIntent().getIntExtra("img",0));
            tv1.setText(getIntent().getStringExtra("email"));
        tv2.setText(getIntent().getStringExtra("name"));
        tv3.setText(getIntent().getStringExtra("course"));


    }
}