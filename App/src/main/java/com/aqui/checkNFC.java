package com.aqui;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;




/**
 * Created by Chapo on 12/11/13.
 */
public class CheckNFC extends Activity{
    //NFC
    private NfcAdapter mNfcAdapter;
    private IntentFilter[] mNdefExchangeFilters;
    private PendingIntent mNfcPendingIntent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_check);


          /* NFC */
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mNfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                CheckNFC.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TOP), 0);



        IntentFilter discovery = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mNdefExchangeFilters = new IntentFilter[] { discovery };

    }//OnCreate

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
                i = new Intent(this,Balance.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
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

    /**
     * This is where we get the intent once we've tapped the tag.
     * Then we can use 'getParceableExtra()' to get the tag data and build an NDEF message array.
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        Log.i("NFC Intent", "Llegamos");
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
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

                    //Location
                    LocationManager mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    LocationListener mLocationListener = new Posicion();
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30, 30, mLocationListener);
                    //mLocationManager.removeUpdates(mLocationListener);
                }
            } else {
                Toast.makeText(this, "The NFC tag appears to be empty", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //NFC
    private void nfcCheck() {
        Log.i("NFC Intent","nfcCheck");
        if(mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent,
                    mNdefExchangeFilters, null);
        } else {
            Toast.makeText(this, "Sorry, No NFC Adapter found.", Toast.LENGTH_SHORT).show();
        }
    }
  //Location
    public class Posicion implements LocationListener{
        public void onLocationChanged(Location loc){

            TextView latLong =(TextView)findViewById(R.id.latlog);

            latLong.setText("Latitud = "+loc.getLatitude()+" "+"Longitud = "+loc.getLongitude());
        }
        public void onProviderDisabled(String provider){
            Toast.makeText( getApplicationContext(),"Gps Desactivado",Toast.LENGTH_SHORT ).show();
        }
        public void onProviderEnabled(String provider){
            Toast.makeText( getApplicationContext(),"Gps Activo",Toast.LENGTH_SHORT ).show();
        }
        public void onStatusChanged(String provider, int status, Bundle extras){
        }
    }
  }//Activity


