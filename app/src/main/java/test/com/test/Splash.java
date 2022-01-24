package test.com.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView img = (ImageView) findViewById(R.id.imgSplash);
        ImageView img2 = (ImageView) findViewById(R.id.imgSplash2);
        final ImageView loading = (ImageView) findViewById(R.id.imgLoading);


        TextView txt = (TextView) findViewById(R.id.txtSplash);
        Typeface tf = Typeface.createFromAsset(getAssets(), "font/BK.ttf");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int x = displayMetrics.widthPixels;


        img2.animate().setDuration(3000).translationX(x).start();
        img.animate().setDuration(3500).translationX(-x).start();
        final RotateAnimation rotateAnimation = new RotateAnimation(0, 360, 50f, 50f);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(600);
        loading.startAnimation(rotateAnimation);
        txt.setTypeface(tf);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rotateAnimation.cancel();
                SharedPreferences sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE);
                int showAuth = sharedPreferences.getInt("Login_state", 1);


                switch (showAuth) {
                    case 0: {
                        String tmpUser = sharedPreferences.getString("Login_user", "");
                        String tmpPass = sharedPreferences.getString("Login_pass", "");
                        String userType= sharedPreferences.getString("login_type","NOT_SET");

                        Intent auth = new Intent(getApplicationContext(), Auth.class);

                        auth.putExtra("username",tmpUser);
                        auth.putExtra("password",tmpPass);
                        auth.putExtra("userType",userType);

                        auth.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(auth);
                        finish();
                        break;
                    }

                    case 1: {
                        Intent auth = new Intent(getApplicationContext(), Auth.class);
                        startActivity(auth);
                        finish();
                        break;
                    }

                }

            }
        }, 3000);
    }


}
