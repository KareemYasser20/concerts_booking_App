package com.example.concerts_booking_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OperaConcerts extends AppCompatActivity {

    ArrayList<PartyModel> Operaconcerts = new ArrayList<>();
    DatabaseReference realtimeRef = FirebaseDatabase.getInstance().getReference();
    GridView Opera_gridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opera_concerts);
        Opera_gridview=findViewById(R.id.Opera_gridview);

        Opera_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent bookSests = new Intent(OperaConcerts.this , BookandSeats.class);
                startActivity(bookSests);
            }
        });

        realtimeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ShowOperaconcerts(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ShowOperaconcerts(DataSnapshot dataSnapshot) {
        for (DataSnapshot shot: dataSnapshot.child("Opera").getChildren()
        ) {
            PartyModel OperaParty = shot.getValue(PartyModel.class);
            Operaconcerts.add(OperaParty);


        }

        GridAdapter adapter = new GridAdapter( OperaConcerts.this, R.layout.concertsrow , Operaconcerts);
        Opera_gridview.setAdapter(adapter);
    }
}
