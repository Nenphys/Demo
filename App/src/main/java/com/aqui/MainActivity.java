package com.aqui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.UserService;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private  static final String BACKENDLESS_KEY= "1132DA59-E875-914B-FF28-F75B1EB4AF00";
    private  static final String APLICATION_SECRET_KEY= "9D8A811D-A0DC-509B-FF59-A1EE8572C000";

    Log_In login;

    BackendlessUser getCurrentUser;
    UserService user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Backendless key's
        String appVersion = "v1";
        Backendless.initApp(this, BACKENDLESS_KEY, APLICATION_SECRET_KEY, appVersion);

        //Login
        login = new Log_In(MainActivity.this);

        //view de lugares y seleccion de modo de registro.
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

                final Dialog dialog = new Dialog(MainActivity.this );
                dialog.setContentView(R.layout.select_nfc_or_qr);
                dialog.setTitle("Selecciona QR o NFC");

                ImageView imagenQr = (ImageView) dialog.findViewById(R.id.qr);
                ImageView imagenNfc = (ImageView) dialog.findViewById(R.id.nfc);

                imagenNfc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this,CheckNFC.class);
                        startActivity(i);
                        dialog.dismiss();
                    }
                });
                imagenQr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this,CheckQR.class);
                        startActivity(i);
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });
        }catch(IndexOutOfBoundsException e){

        }

    }

    @Override
    protected void onResume(){
        super.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
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
                i = new Intent(this,Balance.class);
                startActivity(i);
                break;
            case R.id.logout:
                Backendless.UserService.logout( new AsyncCallback<Void>()
                {
                    public void handleResponse( Void response )
                    {
                        // user has been logged out.
                        Log.i("kkk","LogOut!!");
                    }

                    public void handleFault( BackendlessFault fault )
                    {
                        // something went wrong and logout failed, to get the error code call fault.getCode()
                        Log.i("kkk","LogOut!!"+fault.toString());
                    }
                });
        }

            return super.onOptionsItemSelected(item);
    }

}
