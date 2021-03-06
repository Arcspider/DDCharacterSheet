package com.example.dndgenerator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Summary extends AppCompatActivity {

    TextView txtName, txtRace, txtClass, txtSpeed, txtAS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtName = findViewById(R.id.txtName);
        txtRace = findViewById(R.id.txtRace);
        txtClass = findViewById(R.id.txtClass);
        txtSpeed = findViewById(R.id.txtSpeed);
        txtAS = findViewById(R.id.txtAS);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent = getIntent();

        String Name = intent.getStringExtra("CharacterName");
        String Race = intent.getStringExtra("Race");
        String Strength = intent.getStringExtra("Strength");
        String Dexterity = intent.getStringExtra("Dexterity");
        String Intelligence = intent.getStringExtra("Intelligence");
        String Constitution = intent.getStringExtra("Constitution");
        String Wisdom = intent.getStringExtra("Wisdom");
        String Charisma = intent.getStringExtra("Charisma");
        Integer Speed = intent.getIntExtra("Speed",0);
        txtName.setText(Name);
        txtRace.setText(Race);
        txtSpeed.setText(String.valueOf(Speed));
    }

}
