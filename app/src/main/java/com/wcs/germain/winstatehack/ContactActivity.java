package com.wcs.germain.winstatehack;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    private ListView contactListView;
    private DatabaseReference mContactDatabaseReference;
    private ArrayList<User> contactList = new ArrayList<>();
    private FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_contact);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        contactListView = findViewById(R.id.listview_contact);
        TextView contacts = findViewById(R.id.contact_tv);
        TextView addContact = findViewById(R.id.contact_new);

        // Assign font
        Typeface regularFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Regular.otf");
        Typeface boldFont = Typeface.createFromAsset(getAssets(), "fonts/Montserrat_Bold.otf");
        contacts.setTypeface(boldFont);
        addContact.setTypeface(boldFont);

        // on charge les référent et on applique l'adapter
        initFirebase();
        addContactFirebaseListener();
    }

    private void initFirebase() {
        mDatabase = FirebaseDatabase.getInstance();
        mContactDatabaseReference = mDatabase.getReference();
    }

    private void addContactFirebaseListener() {
        mContactDatabaseReference.child("user").orderByChild("firstName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (contactList.size() > 0)
                    contactList.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    User contact = postSnapshot.getValue(User.class);
                    contactList.add(contact);
                }
                ContactAdapter adapter = new ContactAdapter(ContactActivity.this, contactList);
                contactListView.setAdapter(adapter);
                contactListView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
