package com.wcs.germain.winstatehack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "proutprout";
    private String statusText ;
    private int totalNbHackteurs = 0;
    private int totalNbwins =0;
    private CardModel mCard;
    private String mIdUserSent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        // Shared pref
        SharedPreferences user = getSharedPreferences("Login", 0);
        final String userId = user.getString("userID","");
        Log.e(TAG, userId);

        ImageView btnSendCard = findViewById(R.id.send_card);
        ImageView btnSendSmile = findViewById(R.id.send_smile);
        ImageView btnDeconnection = findViewById(R.id.deconnection);
        final TextView nbHackteurs = findViewById(R.id.nb_hackteurs);
        final TextView nbTotalWins = findViewById(R.id.nb_total_wins);
        final TextView nbpersonalWins = findViewById(R.id.nb_personal_wins);
        final TextView tvUserName = findViewById(R.id.user_name);
        TextView hackteurs = findViewById(R.id.hackteurs);
        TextView personalWins = findViewById(R.id.personal_wins);
        TextView totalWins = findViewById(R.id.total_wins);
        final TextView status = findViewById(R.id.status);

        // Assign font
        Typeface regularFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Regular.otf");
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Bold.otf");

        nbHackteurs.setTypeface(boldFont);
        nbTotalWins.setTypeface(boldFont);
        nbpersonalWins.setTypeface(boldFont);
        tvUserName.setTypeface(boldFont);
        hackteurs.setTypeface(regularFont);
        personalWins.setTypeface(regularFont);
        totalWins.setTypeface(regularFont);
        status.setTypeface(regularFont);

        // Assign values from database to textviews
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference UserRef = database.getReference().child("user");
        UserRef.orderByKey().equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot sp : dataSnapshot.getChildren()) {
                    final User myUser = sp.getValue(User.class);
                    tvUserName.setText(myUser.getFirstName());
                    Log.e(TAG, myUser.getFirstName());
                    nbpersonalWins.setText(String.valueOf(myUser.getNbWin()));
                    if(myUser.getNbWin()<100){
                        statusText = "Ami du bonheur";
                    }
                    if(myUser.getNbWin()<50){
                        statusText = "Stagiaire";
                    }
                    if(myUser.getNbWin()<10){
                        statusText = "Noob";
                    }else{
                        statusText = "Donneur de love";
                    }

                    status.setText(statusText);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        UserRef.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalNbHackteurs =0;
                totalNbwins=0;
                for (DataSnapshot sp : dataSnapshot.getChildren()) {
                    final User randomUser = sp.getValue(User.class);

                    totalNbHackteurs ++;
                    totalNbwins += randomUser.getNbWin() ;
                }
                nbTotalWins.setText(String.valueOf(totalNbwins));
                nbHackteurs.setText(String.valueOf(totalNbHackteurs));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Go to send cards
        btnSendCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCard = new Intent(HomeActivity.this, ContactActivity.class);
                startActivity(intentCard);
                finish();
            }
        });

        // Déconnection
        btnDeconnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setMessage(getString(R.string.deconnection))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.deconnection_yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences userPref = getSharedPreferences("Login", 0);
                                userPref.edit().clear().apply();
                                Intent intentConnection = new Intent(HomeActivity.this, ConnectionActivity.class);
                                startActivity(intentConnection);
                                finish();
                            }
                        })
                        .setNegativeButton(getString(R.string.deconnection_no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        //Set your icon here
                        .setTitle(getString(R.string.deconnection_title));
                AlertDialog alert = builder.create();
                alert.show();
            }

        });

        //Go to smile
        // TODO mettre le vrai link
        final List<CardModel> listCard = new ArrayList<>();
        btnSendSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCard != null) {
                    Intent intentSmile = new Intent(HomeActivity.this, GetACard.class);
                    intentSmile.putExtra("object", listCard.get(0));
                    intentSmile.putExtra("idUserSent", mIdUserSent);
                    startActivity(intentSmile);
                } else{
                    Toast.makeText(HomeActivity.this, "Vous n'avez pas recu de sourire :(", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // On recupere les Cards recus !
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("SentCards").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (listCard.size() > 0) {
                    listCard.clear();
                }

                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    String userReceveirId = dsp.child("userReceiverId").getValue(String.class);
                    boolean status = dsp.child("readStatus").getValue(boolean.class);
                    if (userReceveirId.equals(userId) && status == false){
                        String id = dsp.child("cardId").getValue(String.class);
                        mIdUserSent = dsp.child("userSenderId").getValue(String.class);
                        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference();
                        ref2.child("Cards").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dsp2) {

                                CardModel cardModel = dsp2.getValue(CardModel.class);
                                mCard = cardModel;
                                listCard.add(cardModel);

                                TextView nbreSmile = findViewById(R.id.nbre_smile);
                                if (listCard.size() != 0){
                                    nbreSmile.setVisibility(View.VISIBLE);
                                    nbreSmile.setText(getApplicationContext().getResources().getString(R.string.sourires, String.valueOf(listCard.size())));
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
