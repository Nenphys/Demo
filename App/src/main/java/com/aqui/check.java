package com.aqui;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

/**
 * Created by Chapo on 12/11/13.
 */
public class check extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_check);

    }
    /**
     * This is where we get the intent once we've tapped the tag.
     * Then we can use 'getParceableExtra()' to get the tag data and build an NDEF message array.
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        Log.i("NFC Intent", "Llegamos");
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getDataString())) {
            NdefMessage[] messages = null;
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                messages = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    messages[i] = (NdefMessage) rawMsgs[i];
                }
            }
            if (messages != null) {
                if (messages[0] != null) {
                    String result = "";
                    byte[] payload = messages[0].getRecords()[0].getPayload();
                    // this assumes that we get back am SOH followed by host/code
                    for (int b = 1; b < payload.length; b++) { // skip SOH
                        result += (char) payload[b];
                    }
                    TextView test = (TextView)findViewById(R.id.textView2);
                    test.setText(result);
                }
            } else {
                Toast.makeText(this, "The NFC tag appears to be empty", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
