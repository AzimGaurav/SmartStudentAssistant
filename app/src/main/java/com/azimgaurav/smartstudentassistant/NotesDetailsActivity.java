package com.azimgaurav.smartstudentassistant;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotesDetailsActivity extends AppCompatActivity {

    EditText etNote;
    FloatingActionButton fabAttachment,fabTheme,fabFont;
    Button btnSave,btnDelete,btnShare;
    DatabaseReference notesReference,databaseReference;
    FirebaseAuth firebaseAuth;
    private String colors[],fonts[];
    private String colorString="#ff99cc00",fontString="segoe_marker";
    private int counterForBackgroundColor,counterForFont;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);

        counterForBackgroundColor=0;
        counterForFont=0;

        //Inintialize components
        layout=findViewById(R.id.note_desc_layout);
        etNote=findViewById(R.id.et_note);
        btnSave=findViewById(R.id.btn_save_note);
        btnShare=findViewById(R.id.btn_note_share);
        btnDelete=findViewById(R.id.btn_delete_note);
        fabAttachment=findViewById(R.id.fab_attachment);
        fabTheme=findViewById(R.id.fab_theme);
        fabFont=findViewById(R.id.fab_font);

        firebaseAuth=FirebaseAuth.getInstance();
        notesReference=FirebaseDatabase.getInstance().getReference().child("Notes").child(firebaseAuth.getCurrentUser().getUid());

        //Receive data from intent
        final String key=getIntent().getStringExtra("key");
        final String title=getIntent().getStringExtra("title");
        final String desc=getIntent().getStringExtra("desc");
        final String color=getIntent().getStringExtra("color");
        final String font=getIntent().getStringExtra("font");

        //Apply set properties
        etNote.setText(desc);
        Log.e("Font",font+"!!!!!!!");
        try
        {
            etNote.setBackgroundColor(Color.parseColor(color));
            layout.setBackgroundColor(Color.parseColor(color));
            Typeface typeface=Typeface.createFromAsset(NotesDetailsActivity.this.getAssets(),font);
            etNote.setTypeface(typeface);
        }
        catch (Exception e)
        {
            Log.e("Color",e.toString());
        }
        //etNote.setBackgroundColor(Color.parseColor("#FFFFFF"));
        //etNote.setBackgroundColor(Color.parseColor(color));
        //layout.setBackgroundColor(Color.parseColor(color));
        //Typeface typeface=Typeface.createFromAsset(NotesDetailsActivity.this.getAssets(),font);
        //etNote.setTypeface(typeface);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etNote.getText().toString().trim().isEmpty() && getIntent().getBooleanExtra("isNew",false))
                {
                    databaseReference = notesReference.push();
                    databaseReference.child("title").setValue(title);
                    databaseReference.child("desc").setValue(etNote.getText().toString());
                    databaseReference.child("color").setValue(colorString);
                    databaseReference.child("font").setValue(fontString);
                    Toast.makeText(getApplicationContext(),"Note added successully..",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(!etNote.getText().toString().trim().isEmpty())
                {
                    databaseReference = notesReference.child(key);
                    databaseReference.child("title").setValue(title);
                    databaseReference.child("desc").setValue(etNote.getText().toString());
                    databaseReference.child("color").setValue(colorString);
                    databaseReference.child("font").setValue(fontString);
                    Toast.makeText(getApplicationContext(),"Note saved successfully..",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(NotesDetailsActivity.this,"Empty note!",Toast.LENGTH_SHORT).show();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key!=null)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(NotesDetailsActivity.this);
                    builder.setTitle("Delete note?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            databaseReference=notesReference.child(key);
                            databaseReference.removeValue();
                            Toast.makeText(NotesDetailsActivity.this,"Note deleted successfully..",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareNote();
            }
        });
        fabAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NotesDetailsActivity.this,"FAB Clicked",Toast.LENGTH_SHORT).show();
            }
        });
        colors=getResources().getStringArray(R.array.bg_colors);
        fabTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(counterForBackgroundColor==13)
                        counterForBackgroundColor=0;
                    colorString=colors[counterForBackgroundColor];
                    counterForBackgroundColor++;
                    View view=getWindow().getDecorView().getRootView();
                    view.findViewById(R.id.et_note).setBackgroundColor(Color.parseColor(colorString));
                    view.findViewById(R.id.note_desc_layout).setBackgroundColor(Color.parseColor(colorString));
                }
                catch (Throwable e)
                {
                    e.printStackTrace();
                }
            }
        });
        fonts=getResources().getStringArray(R.array.my_fonts);
        fabFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(counterForFont==20)
                        counterForFont=0;
                    fontString=fonts[counterForFont];
                    counterForFont++;
                    //Toast.makeText(AddTextActivity.this,"Font : "+fontString,Toast.LENGTH_SHORT).show();
                    Typeface typeface;
                    typeface=Typeface.createFromAsset(NotesDetailsActivity.this.getAssets(),fontString);
                    etNote.setTypeface(typeface);
                }
                catch(Throwable e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
    void shareNote()
    {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,etNote.getText().toString());
        startActivity(Intent.createChooser(intent,"Share to"));
    }
}