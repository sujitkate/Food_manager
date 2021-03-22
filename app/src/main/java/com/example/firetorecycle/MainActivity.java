package com.example.firetorecycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;
FloatingActionButton fb;
myadaptor myadaptorOb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fb =(FloatingActionButton)findViewById(R.id.floatingActionButton4);

      recyclerView=(RecyclerView)findViewById(R.id.recycleID);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));

       network();
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("student"), Model.class)
                        .build();

        myadaptorOb=new myadaptor(options,getApplicationContext());
        recyclerView.setAdapter(myadaptorOb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(i);
            }
        });


    }

    private void network() {
        ConnectivityManager  manager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null)
        {
            if(info.getType() == ConnectivityManager.TYPE_MOBILE)
            {
                Toast.makeText(MainActivity.this,"Mobile data on",Toast.LENGTH_SHORT).show();
            }
            else if(info.getType() == ConnectivityManager.TYPE_WIFI)
            {
                Toast.makeText(MainActivity.this,"Wifi On",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(MainActivity.this,"Internet Required!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        myadaptorOb.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myadaptorOb.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchmenu,menu);

        MenuItem item=menu.findItem(R.id.searchID);

        SearchView searchView;
        searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processSearch(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    private void processSearch(String s)
    {

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("student").orderByChild("name").startAt(s).endAt(s+"\uf8ff"), Model.class)
                        .build();

        myadaptorOb=new myadaptor(options,getApplicationContext()); //Result received at options varible ,it passed to adaptor
        myadaptorOb.startListening();       //Adaptor start listening A/C to result
        recyclerView.setAdapter(myadaptorOb);  //Hence recycle view will show the result

    }

}