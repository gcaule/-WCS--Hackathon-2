package com.wcs.germain.winstatehack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View;
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

public class GetACard extends AppCompatActivity {

    private static final String TAG = "creator key : ";
    private CardModel mCard;
    private String mIdUserSent;
    private String mSenderId;
    private User mSenderUser;
    private String mSenderKey;
    private int nbWinSender;
    private String mCreatorKey;
    private int nbWinCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_acard);

        // Shared pref
        SharedPreferences user = getSharedPreferences("Login", 0);
        final String userId = user.getString("userID", "");

        mCard = getIntent().getParcelableExtra("object");
        mIdUserSent = getIntent().getStringExtra("idUserSent");

        mCreatorKey = mCard.getCreatorId();
        Log.e(TAG, mCreatorKey);


        // TODO ce que je recupere pour l'instant est un exemple
        // il faudra recuperer soit un objet soit autre chose jsé pas
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child("user").orderByChild("id").equalTo(mCreatorKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    nbWinCreator = dsp.child("nbWin").getValue(int.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // ReadStatus passe a true
        ref.child("SentCards").orderByChild("cardId").equalTo(mCard.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    String id = dsp.getKey();
                    ref.child("SentCards").child(id).child("readStatus").setValue(true);
                    mSenderId = dsp.child("userSenderId").getValue(String.class);

                    if(mSenderId!=null){

                        // +nbWin CreatorId
                        if (mSenderId != mCard.getCreatorId()){
                            ref.child("user").child(mCard.getCreatorId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);
                                    int nbWin = user.getNbWin();
                                    ref.child("user").child(mCard.getCreatorId()).child("nbWin").setValue(nbWin+1);
                                    Toast.makeText(GetACard.this, "Le createur de cette carte a été recompensé !", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }


                        // Username
                        ref.child("user").child(mSenderId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                mSenderUser = dataSnapshot.getValue(User.class);
                                String name = mSenderUser.getFirstName();
                                mSenderKey = dataSnapshot.getKey();
                                nbWinSender = mSenderUser.getNbWin();
                                TextView title = findViewById(R.id.getacard_top_title);
                                title.setText(getApplicationContext().getResources().getString(R.string.getacard_title, name));
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

        // On rajoute la carte dans le Autorized id
        ref.child("Cards").child(mCard.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CardModel card = dataSnapshot.getValue(CardModel.class);
                List<String> list = card.getAuthorizedId();
                list.add(userId);
                ref.child("Cards").child(mCard.getId()).child("authorizedId").setValue(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView message = findViewById(R.id.createcards_card_textview);
        message.setText(mCard.getText());
        // la couleur
        RelativeLayout cardLayout = findViewById(R.id.createcards_card);
        Resources resources = getResources();
        int resourceId = resources.getIdentifier(mCard.getColor(), "drawable", getApplicationContext().getPackageName());
        cardLayout.setBackground(resources.getDrawable(resourceId));
        // le personnage
        ImageView personnage = findViewById(R.id.createcards_card_image);
        resourceId = resources.getIdentifier(mCard.getImage(), "drawable", getApplicationContext().getPackageName());
        personnage.setBackground(resources.getDrawable(resourceId));

        // LOLWIN
        Button btnLol = findViewById(R.id.getacard_btn_lol);
        btnLol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child("user").orderByChild("id").equalTo(mSenderKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        nbWinSender += 1;
                        ref.child("user").child(mSenderKey).child("nbWin").setValue(nbWinSender);
                        nbWinCreator += 1;
                        ref.child("user").child(mCreatorKey).child("nbWin").setValue(nbWinCreator);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        // Bouton répondre
        TextView repondre = findViewById(R.id.getacard_btn_répondre);
        repondre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iResponse = new Intent(GetACard.this, MenuCards.class);
                iResponse.putExtra("response", mSenderId);
                startActivity(iResponse);
            }
        });


    }

    // Retour sur la page Connection si pression du bouton retour Android
    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(GetACard.this, HomeActivity.class);
        startActivity(intent);
    }
}
