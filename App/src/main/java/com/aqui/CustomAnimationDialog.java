package com.aqui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Chapo on 1/24/14.
 */
public class CustomAnimationDialog {
    public ProgressDialog progressDialog;
    ImageView imgView;

    public CustomAnimationDialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progressbar);
        imgView = (ImageView)progressDialog.findViewById(R.id.flecha);
        imgView.setVisibility(ImageView.VISIBLE);
        imgView.setBackgroundResource(R.drawable.frame_animation);

        AnimationDrawable frame = (AnimationDrawable) imgView.getBackground();
        if(frame.isRunning()){
            Log.i("Animacion","IF animacion");
            frame.stop();
        }else{
            Log.i("Animacion","Else animacion");
            frame.stop();
            frame.start();
        }

    }
}
