package com.wcs.germain.winstatehack;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuCards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cards);

        final String idToSend = getIntent().getExtras().getString("idToSend");

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

        final Intent intent = new Intent(MenuCards.this, CardsCollection.class);
        intent.putExtra("idToSend", idToSend);
        seeDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(MenuCards.this, CardsCollection.class);
                intent.putExtra("idToSend", idToSend);
                startActivity(intent);
            }
        });

        createCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(MenuCards.this, CreateCards.class);
                intent.putExtra("idToSend", idToSend);
                startActivity(intent);
            }
        });
    }
}
