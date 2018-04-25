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

    TextView    textViewRace,   textViewSub,    textViewSpeed, txtSpeed, txtAbilityScore;
    EditText    editTextName,   editTextSpeed;
    Spinner     spinnerRace,    spinnerSub;
    String race;


    //Definere database
    DatabaseReference dndRaceRef    = FirebaseDatabase.getInstance().getReference("Races");


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        //Get UI elements
        textViewRace    = findViewById(R.id.textViewRace);
        textViewSub     = findViewById(R.id.textViewSub);
        textViewSpeed   = findViewById(R.id.textViewSpeed);
        txtSpeed        = findViewById(R.id.txtSpeed);
        txtAbilityScore = findViewById(R.id.txtAbilityScore);

        spinnerRace     = findViewById(R.id.spinnerRace);
        spinnerSub      = findViewById(R.id.spinnerSub);


    }

    @Override
    protected void onStart(){
        super.onStart();

        dndRaceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> races = new ArrayList<String>(); //Opretter et array til "races"
                for (DataSnapshot racesSnapshot : dataSnapshot.getChildren()) {
                    String raceName = racesSnapshot.child("raceName").getValue(String.class); //Finder "raceName" fra childs i databasen
                    races.add(raceName);  //Tilføjer "raceName" til arraylisten
                    Log.d("Races", "The race is " + raceName);


                }
                // Opretter en spinner-liste
                Spinner raceSpinner = findViewById(R.id.spinnerRace);
                ArrayAdapter<String> raceAdapter = new ArrayAdapter<String>(Race.this, android.R.layout.simple_spinner_item, races);
                raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                raceSpinner.setAdapter(raceAdapter);
                raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (adapterView == spinnerRace) {
                            race = races.get(i);
                            expandRace(race);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }


                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*dndRaceElf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> subElf = new ArrayList<>();
                for (DataSnapshot elfSnapshot : dataSnapshot.getChildren()){
                    String elfSub = elfSnapshot.child("SubName").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dndRaceGnome.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> subgnome = new ArrayList<>();
                for (DataSnapshot gnomeSnapshot : dataSnapshot.getChildren()){
                    String gnomeSub = gnomeSnapshot.child("SubName").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }
    void expandRace(final String raceName){
        final Spinner subSpinner = findViewById(R.id.spinnerSub);

        dndRaceRef.child(raceName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final List<String> subList = new ArrayList<>();
                for (final DataSnapshot subSnapshot : dataSnapshot.getChildren()){
                    final String subName = subSnapshot.child("SubName").getValue(String.class);

                    if (subName != null) subList.add(subName);
                    Log.d("Sub","The subrace is: " + subName);
                }

                if (subList.isEmpty()){
                    spinnerSub.setVisibility(View.GONE);
                }

                else {
                    spinnerSub.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> subAdapter = new ArrayAdapter<String>(Race.this, android.R.layout.simple_spinner_item, subList);
                    subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subSpinner.setAdapter(subAdapter);
                    subSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                            String subName = spinnerSub.getSelectedItem().toString();
                            for (final DataSnapshot speedSnapshot : dataSnapshot.getChildren()){
                            final Integer subSpeed =
                                    speedSnapshot.child("Speed").getValue(Integer.class);
                              if(subName.equals("Hill Dwarf")){
                                if (subSpeed != null){
                                    txtSpeed.setText(subSpeed.toString());}
                            } else if(subName.equals("Mountain Dwarf")){
                                if (subSpeed != null){
                                    txtSpeed.setText(subSpeed.toString());}
                            }}
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

