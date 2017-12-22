package com.wcs.germain.winstatehack;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateCards extends AppCompatActivity {

    private RelativeLayout mCard;
    private ImageView mCardImage;
    private TextView mTextCard;
    private String mColor = "";
    private String mImage = "";

    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;

    private Context context;

    private String API_URL = "https://api-face.sightcorp.com/api/detect/";
    private String API_KEY = "d6b95f4eaac74193837bacbfbc194021";

    int happiness;

    public CreateCards(Context context) {
        this.context = context;
    }
    public CreateCards() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_cards);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

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

    // Retour sur la page Connection si pression du bouton retour Android
    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(CreateCards.this, MenuCards.class);
        startActivity(intent);
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

                String text = mTextCard.getText().toString();
                // TODO trouver le moyen de set le type
                CardModel card = new CardModel(id, gravity, width, height, mColor, mImage, text, null, null,null );

                ref.child("Cards").child(id).setValue(card);

                progressBar.setVisibility(View.GONE);
                Toast.makeText(CreateCards.this, "Votre carte a bien été créee !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CreateCards.this, CreateCards.class));

                getImageResponse(new ImageResponseListener() {
                    @Override
                    public void onResult(String response) {
                        getImageResponseFromJson(response);
                        Toast.makeText(getBaseContext(),String.valueOf(happiness), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        });
    }

    //Méthode pour stocker la photo
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // Méthode pour prendre la photo
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.wcs.germain.winstatehack.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void getImageResponse(final ImageResponseListener imageResponseListener) {

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = API_URL;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        imageResponseListener.onResult(response);
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("app_key", API_KEY);
                params.put("url", "https://media.istockphoto.com/photos/young-blond-woman-laughing-picture-id488312318");

                return params;
            }
        };
        queue.add(postRequest);

    }

    private int getImageResponseFromJson(String response) {

        JSONObject jObject = null;
        try {
            jObject = new JSONObject(response);

            JSONArray jArray = jObject.getJSONArray("people");
            for (int i=0; i < jArray.length(); i++) {
                JSONObject emotionsObject = ((JSONObject) jArray.get(i)).getJSONObject("emotions");
                happiness = Integer.parseInt(emotionsObject.getString("happiness"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return happiness;
    }

    public interface ImageResponseListener {
        void onResult(String response);
        void onError(String error);
    }

}
