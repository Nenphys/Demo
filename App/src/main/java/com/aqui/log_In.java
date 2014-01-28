package com.aqui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by Chapo on 1/13/14.
 */
public class Log_In {

    private String usuario;
    private String contra;
    private Context tempContext;

    public BackendlessUser userLogin;

    public Log_In(Context context){


        Log.i("Current user",""+Backendless.UserService.CurrentUser());


       if(Backendless.UserService.CurrentUser()== null){

            final Dialog Login = new Dialog(context);
            Login.setContentView(R.layout.login);
            Login.setTitle("Login");
            Login.setCancelable(false);

            tempContext =context;
            ImageView face = (ImageView) Login.findViewById(R.id.facebook);
            ImageView twitter = (ImageView) Login.findViewById(R.id.twitter);
            Button loginBtn = (Button)Login.findViewById(R.id.login);
            TextView registro = (TextView)Login.findViewById(R.id.registroTv);


            //login
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText email = (EditText) Login.findViewById(R.id.username);
                    EditText pass = (EditText) Login.findViewById(R.id.pass);

                    //Datos Login
                    usuario = email.getText().toString();
                    contra = pass.getText().toString();

                    Backendless.UserService.login( usuario, contra, new AsyncCallback<BackendlessUser>(){
                        public void handleResponse( BackendlessUser user ){

                            userLogin = user;
                            Toast toast = Toast.makeText(tempContext, "Login correcto!!!!!!!",Toast.LENGTH_SHORT);
                            toast.show();
                            Log.i("Login"," Login ok");
                            Login.dismiss();
                        }

                        public void handleFault( BackendlessFault fault ){
                            Toast toast = Toast.makeText(tempContext, "Login Incorrecto!!!!!!!",Toast.LENGTH_SHORT);
                            toast.show();
                            Log.i("Login", "" + fault.toString());
                        }
                    });

                }
            });


            //Login Facebook
            face.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Backendless.UserService.loginWithFacebook((Activity)tempContext,
                            new AsyncCallback<BackendlessUser>() {
                                @Override
                                public void handleResponse(BackendlessUser backendlessUser) {
                                    Log.i("Facebook Registration:", backendlessUser.getEmail() + "Success");

                                }

                                @Override
                                public void handleFault(BackendlessFault backendlessFault) {
                                    Log.i("Facebook Registration:", backendlessFault.getMessage());
                                }
                            });
                }
            });

            //Login Twitter
            twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Backendless.UserService.loginWithTwitter((Activity)tempContext,
                            new AsyncCallback<BackendlessUser>() {
                                @Override
                                public void handleResponse(BackendlessUser backendlessUser) {
                                    Log.i("Twitter Registration:", backendlessUser.getEmail() + "Success");

                                }

                                @Override
                                public void handleFault(BackendlessFault backendlessFault) {
                                    Log.i("Twitter Registration:", backendlessFault.getMessage());
                                }
                            });
                }
            });

            //Registro Usuario sin facebook o twitter
            registro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Registro nuevoRegistro = new Registro(tempContext);
                }
            });

            Login.show();
        }
    }

}
