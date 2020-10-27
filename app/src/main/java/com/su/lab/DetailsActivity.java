package com.su.lab;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class DetailsActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    static String CONTACT_ID_EXTRA = "su.lab.CONTACT_ID_EXTRA";
    static String NAME_EXTRA = "su.lab.NAME_EXTRA";

    // Defines a constant that identifies the loader
    static int DETAILS_QUERY_ID = 0;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getLoaderManager().initLoader(DETAILS_QUERY_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Intent intent = getIntent();
        long contactId = intent.getLongExtra(CONTACT_ID_EXTRA, -1);
        name = intent.getStringExtra(NAME_EXTRA);
        return new CursorLoader(
                this,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= " + contactId,
                null,
                null
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToPosition(0);
        final String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        /*
         * TODO #3 Добавить обработчик клика и добавить логику перехода в системное приложение телефона
         */
        TextView phoneNumber = findViewById(R.id.contacts_phone_number);
        phoneNumber.setText(name + " " + number);
        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
