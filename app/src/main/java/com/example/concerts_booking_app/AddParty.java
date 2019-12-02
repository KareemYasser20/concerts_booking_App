package com.example.concerts_booking_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AddParty extends AppCompatActivity {

    EditText Partyname , Partytime , FirstClassticketprice , SecondClassticketprice , ThirdClassticketprice;

    Button SlecetBandimage_btn , Createnewparty;
    RadioGroup SlectPartyplace;
    RadioButton Sakia , Opera;
    ImageView slectedimage;

    DatabaseReference realtimeRef = FirebaseDatabase.getInstance().getReference();
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    public static String imgeUrl;

    ArrayList<PartyModel> Sakiaconcerts = new ArrayList<>();
    ArrayList<PartyModel> Operaconcerts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_party);
        Partyname=findViewById(R.id.PartName);
        Partytime=findViewById(R.id.PartyTime);
        FirstClassticketprice=findViewById(R.id.F_class_price);
        SecondClassticketprice=findViewById(R.id.s_class_price);
        ThirdClassticketprice=findViewById(R.id.T_class_price);
        SlecetBandimage_btn=findViewById(R.id.Select_image_btn);
        Createnewparty=findViewById(R.id.Create_new_party_btn);
        SlectPartyplace=findViewById(R.id.radio_g_place);
        Sakia=findViewById(R.id.sakia_radio_btn);
        Opera=findViewById(R.id.opera_radio_btn);
        slectedimage=findViewById(R.id.slected_image_IV);

        Createnewparty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioid = SlectPartyplace.getCheckedRadioButtonId();
                Sakia = findViewById(radioid);
                int radiobuttonid=SlectPartyplace.getCheckedRadioButtonId();
                RadioButton PartyPlace=  findViewById(radiobuttonid);
                String party_place = PartyPlace.getText().toString();
                if (party_place.equals("Sakia El Sawy")){

                    AddSakiaParty();
                }else {
                    AddOperaParty();
                }
            }
        });

        SlecetBandimage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectimage();
            }
        });


    }

    private void AddOperaParty() {
        String randamOperaid=realtimeRef.push().getKey();
        PartyModel OperaParty1 = new PartyModel(imgeUrl , Partyname.getText().toString()
                , Partytime.getText().toString() , randamOperaid , FirstClassticketprice.getText().toString()
                , SecondClassticketprice.getText().toString() ,  ThirdClassticketprice.getText().toString()  );

        Operaconcerts.add(OperaParty1);
        realtimeRef.child("Opera").child(randamOperaid).setValue(OperaParty1);
        for (int i=1 ; i<=10 ; i++)
        {
            SeatsModel F_class_Seats = new SeatsModel(String.valueOf(i) , "Available" , FirstClassticketprice.getText().toString() );
            realtimeRef.child("Opera").child(randamOperaid).child("Seats").child(String.valueOf(i)).setValue(F_class_Seats);
        }

        for (int i=11 ; i<=20 ; i++)
        {
            SeatsModel S_class_Seats = new SeatsModel(String.valueOf(i) , "Available" , SecondClassticketprice.getText().toString() );
            realtimeRef.child("Opera").child(randamOperaid).child("Seats").child(String.valueOf(i)).setValue(S_class_Seats);
        }

        for (int i=21 ; i<=30 ; i++)
        {
            SeatsModel T_class_Seats = new SeatsModel(String.valueOf(i) , "Available" , ThirdClassticketprice.getText().toString() );
            realtimeRef.child("Opera").child(randamOperaid).child("Seats").child(String.valueOf(i)).setValue(T_class_Seats);
        }


    }







    void AddSakiaParty()
    {
        String randamid=realtimeRef.push().getKey();
        PartyModel SakiaParty1 = new PartyModel(imgeUrl , Partyname.getText().toString()
                , Partytime.getText().toString() , randamid , FirstClassticketprice.getText().toString()
                , SecondClassticketprice.getText().toString() ,  ThirdClassticketprice.getText().toString()  );

        Sakiaconcerts.add(SakiaParty1);
        realtimeRef.child("Sakia").child(randamid).setValue(SakiaParty1);

        for (int i=1 ; i<=10 ; i++)
        {
            SeatsModel F_class_Seats = new SeatsModel(String.valueOf(i) , "Available" , FirstClassticketprice.getText().toString() );
            realtimeRef.child("Sakia").child(randamid).child("Seats").child(String.valueOf(i)).setValue(F_class_Seats);
        }

        for (int i=11 ; i<=20 ; i++)
        {
            SeatsModel S_class_Seats = new SeatsModel(String.valueOf(i) , "Available" , SecondClassticketprice.getText().toString() );
            realtimeRef.child("Sakia").child(randamid).child("Seats").child(String.valueOf(i)).setValue(S_class_Seats);
        }

        for (int i=21 ; i<=30 ; i++)
        {
            SeatsModel T_class_Seats = new SeatsModel(String.valueOf(i) , "Available" , ThirdClassticketprice.getText().toString() );
            realtimeRef.child("Sakia").child(randamid).child("Seats").child(String.valueOf(i)).setValue(T_class_Seats);
        }




    }

    public void selectimage(){
        Intent pickphoto = new Intent(Intent.ACTION_PICK);
        pickphoto.setType("image/*");
        startActivityForResult(pickphoto , 10);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode==RESULT_OK && data!=null ){
            Uri imageUri = data.getData();
            slectedimage.setImageURI(imageUri);
            uploadphoto(imageUri);
        }

    }

    private void uploadphoto (Uri uri) {

        final String randamname=realtimeRef.push().getKey();


        storageRef.child("photos").child(randamname).putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddParty.this, "upload sucsses", Toast.LENGTH_SHORT).show();
                    storageRef.child("photos").child(randamname).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            imgeUrl= task.getResult().toString();
                        }
                    });
                }
            }
        });

    }


}