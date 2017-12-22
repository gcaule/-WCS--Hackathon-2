package com.wcs.germain.winstatehack;

/**
 * Created by wilder on 21/12/17.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DeckAdapter extends ArrayAdapter<Pair<Pair<CardModel, String>, Integer>> {


    private final int mResource;
    private String mUserId;

    DeckAdapter(@NonNull Context context, @NonNull List<Pair<Pair<CardModel, String>, Integer>> objects) {
        super(context, R.layout.item, objects);
        mResource = R.layout.item;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull final ViewGroup parent) {

        // Shared pref
        SharedPreferences user = parent.getContext().getSharedPreferences("Login", 0);
        mUserId = user.getString("userID","");

        final RelativeLayout layout;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (RelativeLayout) inflater.inflate(mResource, parent, false);
        } else
            layout = (RelativeLayout) convertView;

        Pair item = getItem(position);
        if (item != null) {
            //layout.setBackgroundColor((Integer) item.second);
            layout.setBackgroundColor(Color.TRANSPARENT);
            Pair pair = (Pair) item.first;
            final CardModel model = (CardModel) pair.first;
            final String userToSend = (String) pair.second;
            // le Text
            ((TextView) layout.findViewById(R.id.item_textview)).setText((String) model.getText());
            // la couleur
            RelativeLayout card = layout.findViewById(R.id.item_card);
            Resources resources = parent.getResources();
            int resourceId = resources.getIdentifier(model.getColor(), "drawable", parent.getContext().getPackageName());
            card.setBackground(resources.getDrawable(resourceId));
            // le personnage
            ImageView personnage = layout.findViewById(R.id.item_card_image);
            resourceId = resources.getIdentifier(model.getImage(), "drawable", parent.getContext().getPackageName());
            personnage.setBackground(resources.getDrawable(resourceId));


            // Pour envoyer la carte
            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                    String id = ref.push().getKey();

                    ref.child("SentCards").child(id).child("cardId").setValue(model.getId());
                    ref.child("SentCards").child(id).child("userSenderId").setValue(mUserId);
                    ref.child("SentCards").child(id).child("userReceiverId").setValue(userToSend);
                    ref.child("SentCards").child(id).child("readStatus").setValue(false);

                    ref.child("Cards").child(model.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            CardModel cardModel = dataSnapshot.getValue(CardModel.class);
                            List<String> authorizedList = cardModel.getAuthorizedId();
                            authorizedList.add(userToSend);
                            ref.child("Cards").child(model.getId()).child("authorizedId").setValue(authorizedList);

                            Toast.makeText(view.getContext(), "Votre carte a bien été envoyée!", Toast.LENGTH_SHORT).show();
                            view.getContext().startActivity(new Intent(view.getContext(), HomeActivity.class));
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                    return false;
                }
            });

        }
            return layout;
    }

}