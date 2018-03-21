package com.example.dndgenerator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by soren on 21-03-2018.
 */

public class Race extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView textViewRace, textViewSub, textViewSpeed;
    EditText editTextName, editTextSpeed;
    Spinner spinnerRace, spinnerSub;

    DatabaseReference dndRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference raceRef = dndRootRef.child("Race");


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        //Get UI elements
        textViewRace = (TextView)findViewById(R.id.textViewRace);
        textViewSub = (TextView)findViewById(R.id.textViewSub);
        textViewSpeed = (TextView)findViewById(R.id.textViewSpeed);
        editTextName = (EditText)findViewById(R.id.editTextName);

        spinnerRace = (Spinner)findViewById(R.id.spinnerRace);
        spinnerSub = (Spinner)findViewById(R.id.spinnerSub);

        spinnerRace.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.main_race, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerRace.setAdapter(adapter);
    }

    @Override
    protected void onStart(){
        super.onStart();



        raceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                textViewRace.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
