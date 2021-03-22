package com.example.firetorecycle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.midi.MidiOutputPort;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadaptor extends FirebaseRecyclerAdapter<Model,myadaptor.myviewholder> {
Context context;
//FirebaseRecyclerOptions<Model> options;
    public myadaptor(@NonNull FirebaseRecyclerOptions<Model> options, Context context)
    {
        super(options);
  ///      this.options=options;
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final Model model)
    { //Data is feeded from model class

       //final Model temp;
        holder.name.setText(model.getName());
        holder.course.setText(model.getCourse());
        holder.email.setText(model.getEmail());

        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);

        holder.img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(context,MainActivity3.class);
                 i.putExtra("name",model.getName());
               i.putExtra("course",model.getCourse());
               i.putExtra("email",model.getEmail());
               i.putExtra("img",model.getPurl());

               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(i);

           }
       });

 ////////////////////////////////////////////////            UPDATE                   /////////////////////////////////////////////////////////////////////////////

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dp = DialogPlus.newDialog(holder.img.getContext()) //img-> to get contenxt of which to see data it can be any varible out o f 4
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,850)
                        .create();
                          dp.show();

                          //To create Runtime view of dialong content
                      View myview = dp.getHolderView();
                 final EditText name1 = myview.findViewById(R.id.updName);
                 final EditText email1 = myview.findViewById(R.id.updEmail);
                 final EditText course1 = myview.findViewById(R.id.updCourse);
                 final EditText url1 = myview.findViewById(R.id.updURL);
                 Button submit = myview.findViewById(R.id.btnUpdateID);

                 name1.setText(model.getName());;
                 email1.setText(model.getEmail());
                course1.setText(model.getCourse());
                url1.setText(model.getPurl());

                dp.show();
              submit.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) { //Map object for each view is ready
                      Map<String,Object> map = new HashMap<>();
                      map.put("purl",url1.getText().toString());
                      map.put("email",email1.getText().toString());
                      map.put("name",name1.getText().toString());
                      map.put("course",course1.getText().toString());

                      FirebaseDatabase.getInstance().getReference().child("student")
                              .child(getRef(position).getKey()).updateChildren(map)
                              .addOnSuccessListener(new OnSuccessListener<Void>() {
                                  @Override
                                  public void onSuccess(Void aVoid) {
                                      dp.dismiss();
                                  }
                              })
                              .addOnFailureListener(new OnFailureListener() {
                                  @Override
                                  public void onFailure(@NonNull Exception e) {
                                      dp.dismiss();
                                  }
                              });
                  }
              });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            }
        });

        //////
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete...?");


                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("student")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });


                builder.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }

        });

    }



    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends  RecyclerView.ViewHolder
    {
        ImageView del,update;
        CircleImageView img;
        TextView course,name,email;
        public myviewholder(@NonNull View itemView)
        {

            super(itemView);
            del=(ImageView) itemView.findViewById(R.id.delbtnID);
            update=(ImageView) itemView.findViewById(R.id.updatebtnID1);

            name=(TextView)itemView.findViewById(R.id.nametext);
            email=(TextView)itemView.findViewById(R.id.emailtext);
            course=(TextView)itemView.findViewById(R.id.coursetext);
          img=(CircleImageView)itemView.findViewById(R.id.img1);



        }
    }
}
