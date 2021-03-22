package com.example.firetorecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
EditText name,course,mail,url;
Button btn;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        name=(EditText)findViewById(R.id.nameID1);
        course=(EditText)findViewById(R.id.courseID2);
        mail=(EditText)findViewById(R.id.emailID2);
        url=(EditText)findViewById(R.id.urlID2);
     btn = (Button)findViewById(R.id.btnADDID);


     final String a = name.getText().toString();
      final String b = course.getText().toString();
        final String c = mail.getText().toString();
        final String d = url.getText().toString();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }
        });

    }

    private void  insert()
    { int i=0;

        String a = name.getText().toString().trim();
        String b = course.getText().toString().trim();
        String c = mail.getText().toString().trim();
        String d = url.getText().toString().trim();


        if(a.isEmpty() )
        { i=0;
            name.setError("Field Required! ");
        }
        if(b.isEmpty() )
        {
            course.setError("Field Required! ");
        }
        if(c.isEmpty() )
        {
            mail.setError("Field Required! ");
        }
        if(d.isEmpty() )
        {
            url.setError("Field Required! ");

        }
       int l1=a.length();
        int l2=b.length();
        int l3=c.length();
        int l4=d.length();

        if( l1>0 && l2>0 && l3>0 &&l4>0)
        {
            dataInsert();
        }


    }
    public void dataInsert()
    {
        Map<String,Object> map =new  HashMap<>();
        map.put("name",name.getText().toString());
        map.put("course",course.getText().toString());
        map.put("email",mail.getText().toString());
        map.put("purl",url.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("student").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        name.setText("");
                        course.setText("");
                        mail.setText("");
                        url.setText("");
                        Toast.makeText(MainActivity2.this,"Data added",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        name.setText("");
                        course.setText("");
                        mail.setText("");
                        url.setText("");
                        Toast.makeText(MainActivity2.this,"Failed to add",Toast.LENGTH_LONG).show();
                    }
                });


    }

}