package com.benscholer.textbomber;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    static final int PICK_CONTACT_REQUEST = 1;  // The request code

    Toolbar toolbar;
    EditText phoneNumber;
    EditText message;
    EditText repeat;
    Button send;
    Button contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumber = (EditText) findViewById(R.id.phone_number);
        message = (EditText) findViewById(R.id.message_text);
        repeat = (EditText) findViewById(R.id.repeat);
        send = (Button) findViewById(R.id.send_button);
        contact = (Button) findViewById(R.id.contacts);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Text Bomber");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = phoneNumber.getText().toString();
                String mssg = message.getText().toString();
                int rept = Integer.parseInt(repeat.getText().toString());

                if ((num == null || num.equals("")) || (mssg == null || mssg.equals(""))) {
                    Toast.makeText(getApplicationContext(), "Make sure all fields are filled",
                            Toast.LENGTH_LONG).show();
                } else {
                    for (int i = 0; i < rept; i++) {
                        SmsManager sms = SmsManager.getDefault();
                        sms.sendTextMessage(num, null, mssg, null, null);
                    }
                }
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                phoneNumber.setText(number);

                // Do something with the phone number...
            }
        }
    }
}
