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

    DatabaseReference dndRaceRef    = FirebaseDatabase.getInstance().getReference("Races");
    DatabaseReference dndRaceDwarf  = dndRaceRef.child("Dwarf");
    DatabaseReference dndRaceElf    = dndRaceRef.child("Elf");
    DatabaseReference dndRaceGnome  = dndRaceRef.child("Gnome");


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
        editTextName    = findViewById(R.id.editTextName);

        spinnerRace     = findViewById(R.id.spinnerRace);
        spinnerSub      = findViewById(R.id.spinnerSub);


    }

    @Override
    protected void onStart(){
        super.onStart();

        dndRaceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> races = new ArrayList<String>();
                for (DataSnapshot racesSnapshot : dataSnapshot.getChildren()) {
                    String raceName = racesSnapshot.child("raceName").getValue(String.class);
                    if (raceName != null) races.add(raceName);
                    Log.d("Races", "The race is " + raceName);


                }
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
    void expandRace(String raceName){
        dndRaceDwarf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> subDwarf = new ArrayList<>();
                for (DataSnapshot dwarfSnapshot : dataSnapshot.getChildren()){
                    String dwarfSub = dwarfSnapshot.child("SubName").getValue(String.class);
                    if (dwarfSub != null) subDwarf.add(dwarfSub);
                    Log.d("Sub","The subrace is: " + dwarfSub);

                    if (dwarfSub != null && dwarfSub.equals("Mountain Dwarf")){
                        dndRaceDwarf.child("Mountain Dwarf").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                txtSpeed.setText("Speed");
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                }
                Spinner subSpinner = findViewById(R.id.spinnerSub);
                ArrayAdapter<String> subAdapter = new ArrayAdapter<String>(Race.this, android.R.layout.simple_spinner_item, subDwarf);
                subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subSpinner.setAdapter(subAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}


//    }

//}
