package com.aqui;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by Chapo on 1/21/14.
 */
public class Registro {

    Context tempContext;
    EditText email;
    EditText contra;
    EditText nombre;
    public Registro(Context context){

        tempContext = context;

        final Dialog Registro = new Dialog(context);
        Registro.setContentView(R.layout.registro);
        Registro.setTitle("Registrate");
        Registro.setCancelable(false);



       email    = (EditText)Registro.findViewById(R.id.r_email);
       contra   = (EditText)Registro.findViewById(R.id.r_pass);
       nombre   = (EditText)Registro.findViewById(R.id.r_nombre);

        email.setHint("Correo Electronico");
        contra.setHint("Contraseña");
        nombre.setHint("Nombre y Apellido");

        Button   registro = (Button)Registro.findViewById(R.id.registarBtn);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackendlessUser user = new BackendlessUser();

                user.setProperty("email", email.getText().toString());
                user.setPassword(contra.getText().toString());
                user.setProperty("name", nombre.getText().toString());


                //Registro
                Backendless.UserService.register( user, new AsyncCallback<BackendlessUser>(){
                    public void handleResponse( BackendlessUser registeredUser ){
                        Toast toast = Toast.makeText(tempContext, "Registro correcto!!!!!!!",Toast.LENGTH_SHORT);
                        toast.show();
                        Log.i("Registro"," Registro ok");
                        Registro.dismiss();
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        Toast toast = Toast.makeText(tempContext, "Registro correcto!!!!!!!",Toast.LENGTH_SHORT);
                        toast.show();
                        Log.i("Registro"," Registro"+backendlessFault.toString());
                    }

                });
            }
        });


        Registro.show();

    }
}
