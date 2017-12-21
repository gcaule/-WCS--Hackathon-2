package com.wcs.germain.winstatehack;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView btnSendCard = findViewById(R.id.send_card);
        TextView nbHackteurs = findViewById(R.id.nb_hackteurs);
        TextView nbTotalWins = findViewById(R.id.nb_total_wins);
        TextView nbpersonalWins = findViewById(R.id.nb_personal_wins);
        TextView userName = findViewById(R.id.user_name);
        TextView hackteurs = findViewById(R.id.hackteurs);
        TextView personalWins = findViewById(R.id.personal_wins);
        TextView totalWins = findViewById(R.id.total_wins);
        TextView status = findViewById(R.id.status);


        // Assign font
        Typeface regularFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Regular.otf");
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Bold.otf");

        nbHackteurs.setTypeface(boldFont);
        nbTotalWins.setTypeface(boldFont);
        nbpersonalWins.setTypeface(boldFont);
        userName.setTypeface(boldFont);
        hackteurs.setTypeface(regularFont);
        personalWins.setTypeface(regularFont);
        totalWins.setTypeface(regularFont);
        status.setTypeface(regularFont);

        // TODO Assign values from database to textviews

        // Go to send cards
        btnSendCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCard = new Intent(HomeActivity.this, ContactActivity.class);
                startActivity(intentCard);
            }
        });

    }
}
