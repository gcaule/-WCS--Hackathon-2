package com.wcs.germain.winstatehack;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_acard);

        // Shared pref
        SharedPreferences user = getSharedPreferences("Login", 0);
        final String userId = user.getString("userID", "");
        mCard = getIntent().getParcelableExtra("object");

        // TODO ce que je recupere pour l'instant est un exemple
        // il faudra recuperer soit un objet soit autre chose jsé pas
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

// Username
        String idUserSent = mCard.getId();
        ref.child("user").orderByKey().equalTo(idUserSent).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView title = findViewById(R.id.getacard_top_title);
                User user = dataSnapshot.getValue(User.class);
                String nameToSent = user.getFirstName();
                title.setText(getApplicationContext().getResources().getString(R.string.getacard_title, nameToSent));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ImageView image = findViewById(R.id.createcards_card_image);
        TextView message = findViewById(R.id.createcards_card_textview);

        message.setText(mCard.getText());
        // la couleur
        RelativeLayout cardLayout = findViewById(R.id.createcards_card);
        Resources resources = getResources();
        int resourceId = resources.getIdentifier(mCard.getColor(), "drawable", getApplicationContext().getPackageName());
        cardLayout.setBackground(resources.getDrawable(resourceId));
        // le personnage
        ImageView personnage = findViewById(R.id.item_card_image);
        resourceId = resources.getIdentifier(mCard.getImage(), "drawable", getApplicationContext().getPackageName());
        personnage.setBackground(resources.getDrawable(resourceId));


    }
}
