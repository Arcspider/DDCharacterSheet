package com.example.dndgenerator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soren on 21-03-2018.
 */

public class Race extends AppCompatActivity {

    TextView    textViewSub,    txtSpeed, txtAbilityScore, txtRacial;
    EditText    editTextName;
    Spinner     spinnerRace,    spinnerSub;
    ListView    RTList;
    String      race;

    //Definere database
    DatabaseReference dndRaceRef    = FirebaseDatabase.getInstance().getReference("Races");
    DatabaseReference MHRef         = FirebaseDatabase.getInstance().getReferenceFromUrl("https://dnd-genrea.firebaseio.com/Races/Dwarf/Hill Dwarf/Traits");


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        //Get UI elements
        textViewSub     = findViewById(R.id.textViewSub);
        txtSpeed        = findViewById(R.id.txtSpeed);
        txtAbilityScore = findViewById(R.id.txtAbilityScore);
        txtRacial       = findViewById(R.id.txtRacial);
        RTList          = findViewById(R.id.RTList);

        spinnerRace     = findViewById(R.id.spinnerRace);
        spinnerSub      = findViewById(R.id.spinnerSub);

    }

    @Override
    protected void onStart(){
        super.onStart();
        dndRaceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final List<String> raceList = new ArrayList<String>(); //Opretter et array til "races"
                for (final DataSnapshot racesSnapshot : dataSnapshot.getChildren()) {
                    final String raceName = racesSnapshot.child("raceName").getValue(String.class); //Finder "raceName" fra childs i databasen
                    raceList.add(raceName);  //Tilføjer "raceName" til arraylisten
                    Log.d("Races", "The race is " + raceName);

                    // Opretter en spinner-liste
                    final Spinner raceSpinner = findViewById(R.id.spinnerRace);
                    ArrayAdapter<String> raceAdapter = new ArrayAdapter<String>(Race.this, android.R.layout.simple_spinner_item, raceList);
                    raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    raceSpinner.setAdapter(raceAdapter);
                    raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String nameRace = raceSpinner.getSelectedItem().toString();
                            if (adapterView == spinnerRace) {
                                race = raceList.get(i);
                                expandRace(race);
                                }
                                final Integer raceSpeed = racesSnapshot.child("Speed").getValue(Integer.class);
                                Log.d("Speed", "onItemSelected: " + raceSpeed);
                                if(nameRace.equals("Dragonborn")){
                                    if (raceSpeed != null){
                                        txtSpeed.setText(raceSpeed.toString());}
                                } else if(nameRace.equals("Half-Elf)")) {
                                    if (raceSpeed != null) {
                                        txtSpeed.setText(raceSpeed.toString());
                                    }
                                }
                            }


                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }
        );
    }
    void expandRace(final String raceName){
        final Spinner subSpinner = findViewById(R.id.spinnerSub);

        //Opretter en spinnerliste for subraces
        dndRaceRef.child(raceName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final List<String> subList = new ArrayList<>();
                for (final DataSnapshot subSnapshot : dataSnapshot.getChildren()){
                    final String subName = subSnapshot.child("SubName").getValue(String.class);
                    if (subName != null) subList.add(subName);
                    Log.d("Sub","The subrace is: " + subName);
                }

                //Gemmer spinnerlisten afvejen hvis der ikke er nogen subraces
                if (subList.isEmpty()){
                    spinnerSub.setVisibility(View.GONE);
                    textViewSub.setVisibility(View.GONE);
                }

                else {
                    spinnerSub.setVisibility(View.VISIBLE);
                    textViewSub.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> subAdapter = new ArrayAdapter<String>(Race.this, android.R.layout.simple_spinner_item, subList);
                    subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subSpinner.setAdapter(subAdapter);

                    // Vælger "Speed", "Ability Score" og "Racial Traits" for alle individuelle subraces
                    subSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                            String subName = spinnerSub.getSelectedItem().toString();

                            for (final DataSnapshot subSnapshot : dataSnapshot.getChildren()) {
                                final Integer subSpeed = subSnapshot.child("Speed").getValue(Integer.class);
                                final Integer asStr = subSnapshot.child("Strength").getValue(Integer.class);
                                final Integer asDex = subSnapshot.child("Dexterity").getValue(Integer.class);
                                final Integer asInt = subSnapshot.child("Intelligence").getValue(Integer.class);
                                final Integer asCon = subSnapshot.child("Constitution").getValue(Integer.class);
                                final Integer asWis = subSnapshot.child("Wisdom").getValue(Integer.class);
                                final Integer asChar = subSnapshot.child("Charisma").getValue(Integer.class);
                                //final String DTTrait = subSnapshot.child("Traits").child("Dwarven Toughness").getValue(String.class);
                               /* final List<String> subRT = new ArrayList<>();
                                final String subTrait = subSnapshot.child("Traits").child("traitName").getValue(String.class);
                                if (subTrait != null) subRT.add(subTrait);
                                Log.d("traits", "onItemSelected: "+subTrait);
                                ArrayAdapter<String> subRTList = new ArrayAdapter<String>(Race.this, android.R.layout.simple_list_item_1, subRT);
                                RTList.setAdapter(subRTList);*/

                                final List<String> subRT = new ArrayList<>();
                                final String subTrait = subSnapshot.child("Traits").child("traitName").getValue(String.class);
                                if (subTrait != null) subRT.add(subTrait);
                                Log.d("traits", "onItemSelected: "+subTrait);
                                Log.d("traits", "onItemSelected: " + subSnapshot.child("Traits"));



                                if (subName.equals("Hill Dwarf")) {
                                    if ((subSpeed != null) && (subSpeed == 45) && (asCon != null) && (asWis != null)){
                                        Log.d("trait", "onItemSelected: " );
                                        Log.d("asbonus", "onItemSelected: " + asCon);
                                        txtSpeed.setText(subSpeed.toString());
                                        txtAbilityScore.setText("Constitution: " + asCon.toString() + "Wisdom: " + asWis);
                                        //txtRacial.setText(DTTrait);
                                        ArrayAdapter<String> subRTList = new ArrayAdapter<String>(Race.this, android.R.layout.simple_list_item_1, subRT);
                                        RTList.setAdapter(subRTList);
                                    }
                                 /*   txtAbilityScore.setText("Constituition " + asCon.toString());
                                } else if (subName.equals("Dark Elf (Drow)")) {
                                    if (subSpeed != null) {
                                        txtSpeed.setText(subSpeed.toString());
                                    }
                                } else if (subName.equals("High Elf")) {
                                    if (subSpeed != null) {
                                        txtSpeed.setText(subSpeed.toString());
                                    }
                                /*} else if (subName.equals("Wood Elf")) {
                                    if (subSpeed != null) {
                                        txtSpeed.setText(subSpeed.toString());
                                    }
                                } else if (subName.equals("Forest Gnome")) {
                                    if (subSpeed != null) {
                                        txtSpeed.setText(subSpeed.toString());
                                    }
                                } else if (subName.equals("Rock Gnome")) {
                                    if (subSpeed != null) {
                                        txtSpeed.setText(subSpeed.toString());
                                    }
                                } else if (subName.equals("Light Foot")) {
                                    if (subSpeed != null) {
                                        txtSpeed.setText(subSpeed.toString());
                                    }
                                } else if (subName.equals("Stout")) {
                                    if (subSpeed != null) {
                                        txtSpeed.setText(subSpeed.toString());
                                    }*/
                                } else if (subName.equals("Mountain Dwarf")) {
                                    if (subSpeed != null) {
                                        txtSpeed.setText(subSpeed.toString());
                                    }
                                }
                            }
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

