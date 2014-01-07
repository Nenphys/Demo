package com.aqui;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.GpsStatus;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Parcelable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private NfcAdapter mNfcAdapter;
    private IntentFilter[] mNdefExchangeFilters;
    private PendingIntent mNfcPendingIntent;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
        ListView PlacesList = (ListView)findViewById(R.id.listView);
        List<item> items = new ArrayList<item>();
        for(int i = 0;i<15;i++){
            items.add(new item(R.drawable.apl, "AppleBees" + i));
        }
        PlacesList.setAdapter(new itemAdapter(this,items));
        PlacesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivity.this,check.class);
                startActivity(i);
            }
        });
        }catch(IndexOutOfBoundsException e){

        }
         /* NFC */
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                check.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TOP), 0);

        IntentFilter discovery = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mNdefExchangeFilters = new IntentFilter[] { discovery };

    }

    @Override
    protected void onResume(){
        super.onResume();
        nfcCheck();
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(mNfcAdapter != null) mNfcAdapter.disableForegroundDispatch(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.balance:
                i = new Intent(this,balance.class);
                startActivity(i);

                break;

//            case R.id.check:
//                i = new Intent(this,check.class);
//                startActivity(i);
//                break;
        }

            return super.onOptionsItemSelected(item);
    }

//    /**
//     * This is where we get the intent once we've tapped the tag.
//     * Then we can use 'getParceableExtra()' to get the tag data and build an NDEF message array.
//     * @param intent
//     */
//    @Override
//    protected void onNewIntent(Intent intent) {
//        Log.i("NFC Intent", "Llegamos");
//        super.onNewIntent(intent);
//        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
//            NdefMessage[] messages = null;
//            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
//            if (rawMsgs != null) {
//                messages = new NdefMessage[rawMsgs.length];
//                for (int i = 0; i < rawMsgs.length; i++) {
//                    messages[i] = (NdefMessage) rawMsgs[i];
//                }
//            }
//            if (messages != null) {
//                if (messages[0] != null) {
//                    String result = "";
//                    byte[] payload = messages[0].getRecords()[0].getPayload();
//                    // this assumes that we get back am SOH followed by host/code
//                    for (int b = 1; b < payload.length; b++) { // skip SOH
//                        result += (char) payload[b];
//                    }
//                    TextView test = (TextView)findViewById(R.id.textView);
//                    test.setText(result);
//                }
//            } else {
//                Toast.makeText(this, "The NFC tag appears to be empty", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    /**
     * we check to see if the NFC adapter is enabled and we execute enableForegroundDispatch(),
     * passing in our pending intent and filters.
     */
    private void nfcCheck() {
        Log.i("NFC Intent","nfcCheck");
        if(mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent,
                    mNdefExchangeFilters, null);
        } else {
            Toast.makeText(this, "Sorry, No NFC Adapter found.", Toast.LENGTH_SHORT).show();
        }
    }


}
