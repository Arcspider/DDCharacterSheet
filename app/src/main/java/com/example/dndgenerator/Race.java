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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soren on 21-03-2018.
 */

public class Race extends AppCompatActivity {

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
        textViewRace = findViewById(R.id.textViewRace);
        textViewSub = findViewById(R.id.textViewSub);
        textViewSpeed = findViewById(R.id.textViewSpeed);
        editTextName = findViewById(R.id.editTextName);

        spinnerRace = findViewById(R.id.spinnerRace);
        spinnerSub = findViewById(R.id.spinnerSub);



      /*  spinnerRace.setOnItemSelectedListener(this); implements AdapterView.OnItemSelectedListener

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.main_race, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerRace.setAdapter(adapter); */
    }

    @Override
    protected void onStart(){
        super.onStart();



        raceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
           //     String text = dataSnapshot.getValue(String.class);
             //   textViewRace.setText(text);
                final List<String> races = new ArrayList<String>();

                for (DataSnapshot racesSnapshot: dataSnapshot.getChildren()) {
                    String raceName = racesSnapshot.child("Races").getValue(String.class);
                    races.add(raceName);
                }

                Spinner raceSpinner = findViewById(R.id.spinnerRace);
                ArrayAdapter<String> raceAdapter = new ArrayAdapter<String>(Race.this, android.R.layout.simple_spinner_item, races);
                raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerRace.setAdapter(raceAdapter);

        }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

 /*   @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/
}
