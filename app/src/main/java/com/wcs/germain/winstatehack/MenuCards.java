package com.wcs.germain.winstatehack;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MenuCards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu_cards);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        TextView topTitle = findViewById(R.id.menucards_tv_top);
        TextView seeDeck = findViewById(R.id.menucards_tv_fromcollection);
        TextView createCards = findViewById(R.id.menucards_tv_addcard);
        TextView rules = findViewById(R.id.menucards_tv_help);


        Typeface regularFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Regular.otf");
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Bold.otf");

        topTitle.setTypeface(boldFont);
        seeDeck.setTypeface(regularFont);
        createCards.setTypeface(regularFont);
        rules.setTypeface(regularFont);
      
        seeDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuCards.this, CardsCollection.class));
            }
        });

        createCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuCards.this, CreateCards.class));
            }
        });
    }

    // Retour sur la page Connection si pression du bouton retour Android
    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(MenuCards.this, ContactActivity.class);
        startActivity(intent);
    }
}