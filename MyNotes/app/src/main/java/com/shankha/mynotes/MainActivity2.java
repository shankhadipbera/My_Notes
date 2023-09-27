package com.shankha.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shankha.mynotes.Models.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    EditText title,note;
    ImageView save;
    Notes notes;
    boolean isOldNote=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        title=findViewById(R.id.edT_title);
        note=findViewById(R.id.edT_notes);
        save=findViewById(R.id.imgV_save);

        notes=new Notes();
        try{
            notes= (Notes) getIntent().getSerializableExtra("old_note");
            title.setText(notes.getTitle());
            note.setText(notes.getNotes());
            isOldNote=true;
        }catch (Exception e){
            e.printStackTrace();
        }



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titlE=title.getText().toString();
                String notE=note.getText().toString();
                if(notE.isEmpty()){
                    Toast.makeText(MainActivity2.this, "Please Enter Notes!....", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    SimpleDateFormat formater= new SimpleDateFormat("EEE dd MMM yyyy HH:mm a");
                    Date date=new Date();
                    if(!isOldNote){
                        notes=new Notes();
                    }
                    notes.setTitle(titlE);
                    notes.setNotes(notE);
                    notes.setDate(formater.format(date));
                    Intent intent=new Intent();
                    intent.putExtra("Note",notes);
                    setResult(Activity.RESULT_OK,intent);
                    finish();

                }
            }
        });
    }
}