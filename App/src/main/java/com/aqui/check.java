package com.aqui;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.widget.ViewAnimator;

/**
 * Created by Chapo on 12/11/13.
 */
public class check extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_check);

        ViewAnimator i = (ViewAnimator)findViewById(R.id.viewAnimator);
        i.setAnimateFirstView(true);




    }
}
