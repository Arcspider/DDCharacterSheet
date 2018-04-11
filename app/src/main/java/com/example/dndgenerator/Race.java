package com.example.dndgenerator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    String race;

    DatabaseReference dndRootRef = FirebaseDatabase.getInstance().getReference();


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


    }

    @Override
    protected void onStart(){
        super.onStart();

        dndRootRef.child("Races").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> races = new ArrayList<String>();
                for (DataSnapshot racesSnapshot: dataSnapshot.getChildren()) {
                    String raceName = racesSnapshot.child("raceName").getValue(String.class);
                    if (raceName != null) races.add(raceName);
                    Log.d("Race", "The race is " + raceName);
                }
                Spinner raceSpinner = findViewById(R.id.spinnerRace);
                ArrayAdapter<String> raceAdapter = new ArrayAdapter<String>(Race.this, android.R.layout.simple_spinner_item, races);
                raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerRace.setAdapter(raceAdapter);
                spinnerRace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        race = races.get(i);
                        Log.d("Race", "You have selected " + race);
                    }
                });




              /*  Spinner subSpinner = (Spinner) findViewById(R.id.spinnerSub);
                //TODO: Husk subrace array
                ArrayAdapter<String> subAdapter = new ArrayAdapter<String>(Race.this, android.R.layout.simple_spinner_item, );
                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSub.setAdapter(subAdapter);*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

}
