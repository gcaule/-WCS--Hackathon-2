package com.wcs.germain.winstatehack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wilder on 21/12/17.
 */

public class ContactAdapter  extends BaseAdapter{

    private Activity mActivity;
    private List<User> mListeContact;
    private LayoutInflater mInflater;

    public ContactAdapter(Activity activity, List<User> listeContact) {
        this.mActivity = activity;
        this.mListeContact = listeContact;
    }

    @Override
    public int getCount() {
        return mListeContact.size();
    }

    @Override
    public Object getItem(int i) {
        return mListeContact.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        mInflater = (LayoutInflater) mActivity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = mInflater.inflate(R.layout.contacts_item,null);

        TextView nomContact = itemView.findViewById(R.id.nom_contact);
        String nom = mListeContact.get(i).getFirstName() ;
        nomContact.setText(nom);

        Typeface regularFont = Typeface.createFromAsset(mActivity.getAssets(), "fonts/Montserrat_Regular.otf");
        nomContact.setTypeface(regularFont);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), MenuCards.class);
                intent.putExtra("idToSend", mListeContact.get(i).getId());
                itemView.getContext().startActivity(intent);
            }
        });

        return itemView;
    }
}





