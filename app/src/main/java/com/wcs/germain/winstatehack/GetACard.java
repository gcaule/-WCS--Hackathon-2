package com.wcs.germain.winstatehack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GetACard extends AppCompatActivity {

    private CardModel mCard;
    private String mIdUserSent;
    private String mSenderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_acard);

        // Shared pref
        SharedPreferences user = getSharedPreferences("Login", 0);
        final String userId = user.getString("userID", "");

        mCard = getIntent().getParcelableExtra("object");
        mIdUserSent = getIntent().getStringExtra("idUserSent");

        // TODO ce que je recupere pour l'instant est un exemple
        // il faudra recuperer soit un objet soit autre chose jsé pas
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        // ReadStatus passe a true
        ref.child("SentCards").orderByChild("cardId").equalTo(mCard.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    String id = dsp.getKey();
                    ref.child("SentCards").child(id).child("readStatus").setValue(true);
                    mSenderId = dsp.child("userSenderId").getValue(String.class);

                    if(mSenderId!=null){
                        // Username
                        ref.child("user").child(mSenderId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
                                String name = user.getFirstName();
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


        // Bouton répondre
        TextView repondre = findViewById(R.id.getacard_btn_répondre);
        repondre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iResponse = new Intent(GetACard.this, MenuCards.class);
                iResponse.putExtra("reponse", mSenderId);
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
