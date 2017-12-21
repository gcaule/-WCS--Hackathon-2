package com.wcs.germain.winstatehack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateCards extends AppCompatActivity {

    private RelativeLayout mCard;
    private ImageView mCardImage;
    private TextView mTextCard;
    private String mColor = "";
    private String mImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cards);

        Typeface regularFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Regular.otf");
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Bold.otf");

        TextView titleTop = findViewById(R.id.menucards_tv_top);
        titleTop.setTypeface(boldFont);
        TextView title2 = findViewById(R.id.createcards_tv_title2);
        title2.setTypeface(regularFont);

        showLinearColor();
        showLinearCharacters();
        showMessage();
        createCard();
    }

    private void showLinearColor() {
        mCard = findViewById(R.id.createcards_card);
        Typeface regularFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Regular.otf");
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Bold.otf");

        TextView changeColor = findViewById(R.id.createcards_changecolor);
        changeColor.setTypeface(regularFont);
        changeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearColor = findViewById(R.id.createcards_linear_color);

                if (linearColor.getVisibility() == View.GONE) {
                    linearColor.setVisibility(View.VISIBLE);
                } else {
                    linearColor.setVisibility(View.GONE);
                }
            }
        });
        Button grey = findViewById(R.id.createcards_card_grey);
        grey.setTypeface(regularFont);
        Button blue = findViewById(R.id.createcards_card_blue);
        blue.setTypeface(regularFont);
        Button yellow = findViewById(R.id.createcards_card_yellow);
        yellow.setTypeface(regularFont);
        Button white = findViewById(R.id.createcards_card_white);
        white.setTypeface(regularFont);

        grey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCard.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.card_grey));
                mColor = "card_grey";
            }
        });blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCard.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.card_blue));
                mColor = "card_blue";
            }
        });yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCard.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.card_yellow));
                mColor = "card_yellow";
            }
        });white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCard.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.card_white));
                mColor = "card_white";
            }
        });



    }
    private void showLinearCharacters() {
        mCardImage = findViewById(R.id.createcards_card_image);
        Typeface regularFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Regular.otf");
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Bold.otf");

        TextView changeImage = findViewById(R.id.createcards_changeimage);
        changeImage.setTypeface(regularFont);

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout linearCharacter = findViewById(R.id.createcards_linear_character);

                if (linearCharacter.getVisibility() == View.GONE) {
                    linearCharacter.setVisibility(View.VISIBLE);
                } else {
                    linearCharacter.setVisibility(View.GONE);
                }
            }
        });
        TextView oracle = findViewById(R.id.createcards_card_oracle);
        oracle.setTypeface(regularFont);
        TextView alchemist = findViewById(R.id.createcards_card_alchemist);
        alchemist.setTypeface(regularFont);
        TextView magicien = findViewById(R.id.createcards_card_magicien);
        magicien.setTypeface(regularFont);
        TextView compteuse = findViewById(R.id.createcards_card_compteuse);
        compteuse.setTypeface(regularFont);
        final TextView philosophe = findViewById(R.id.createcards_card_philosophe);
        philosophe.setTypeface(regularFont);

        oracle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardImage.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.oracle));
                mImage = "oracle";
            }
        });alchemist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardImage.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.alchemiste));
                mImage = "alchemiste";
            }
        });magicien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardImage.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.magicien));
                mImage = "magicien";
            }
        });compteuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardImage.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.compteuse));
                mImage = "compteuse";
            }
        });philosophe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardImage.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.philosophe));
                mImage = "philosophe";
            }
        });
    }
    private void showMessage(){
        mTextCard = findViewById(R.id.createcards_card_textview);

        Typeface regularFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Regular.otf");
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Bold.otf");
        mTextCard.setTypeface(boldFont);

        final EditText message = findViewById(R.id.createcards_settext);
        message.setTypeface(regularFont);
        final Button send = findViewById(R.id.createcards_send);
        send.setTypeface(regularFont);
        send.setVisibility(View.GONE);

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)){
                    send.setVisibility(View.GONE);
                }else {
                    send.setVisibility(View.VISIBLE);
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextCard.setText(message.getText());
                InputMethodManager in = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

    }
    private void createCard() {
        Button validate = findViewById(R.id.createcards_validate);

        Typeface regularFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Regular.otf");
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Bold.otf");

        validate.setTypeface(boldFont);
        
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressBar progressBar = findViewById(R.id.createcards_progressbar);
                progressBar.setVisibility(View.VISIBLE);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                String id = ref.push().getKey();
                int gravity = mCard.getGravity();
                int width = mCard.getWidth();
                int height = mCard.getHeight();
                List<String> authorizedId = new ArrayList<>();
                //authorizedId.add(userid);

                String text = mTextCard.getText().toString();
                // TODO trouver le moyen de set le type
                CardModel card = new CardModel(id, gravity, width, height, mColor, mImage, text, null, null,null );

                ref.child("Cards").child(id).setValue(card);

                progressBar.setVisibility(View.GONE);
                Toast.makeText(CreateCards.this, "Votre carte a bien été créee !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CreateCards.this, CreateCards.class));
            }
        });
    }
}
