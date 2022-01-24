package test.com.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements AsyncSendToServer, AsyncUpdateStatus {
    String token;
    private FirebaseAnalytics mFirebaseAnalytics;
    String name, tel, addr, des, time, price;
    public TextView txtName, txtTel, txtAddr, txtDes;
    public ImageView imgAccept, imgCancel, imgFinish;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        //set Font Fot Text
        Typeface tf = Typeface.createFromAsset(getAssets(), "font/BK.ttf");


        imgAccept = (ImageView) findViewById(R.id.imgAccept);
        imgCancel = (ImageView) findViewById(R.id.imgCancel);
        imgFinish = (ImageView) findViewById(R.id.imgFinish);
        sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        token = sharedPreferences.getString("Token", "");

        int service = sharedPreferences.getInt("service", 0);


        if (service == 1) {
            imgFinish.setAlpha(1f);
            imgFinish.setEnabled(true);
            imgAccept.setAlpha(0.5f);
            imgAccept.setEnabled(false);
        } else {
            imgAccept.setAlpha(1f);
            imgAccept.setEnabled(true);
            imgFinish.setEnabled(false);
            imgFinish.setAlpha(0.5f);
        }

        //Used this Code For Analytics Fcm ....For Better Monitoring App

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        txtName = (TextView) findViewById(R.id.txtName);
        txtTel = (TextView) findViewById(R.id.txtTel);
        txtAddr = (TextView) findViewById(R.id.txtAddr);
        txtDes = (TextView) findViewById(R.id.txtDes);

        txtTel.setTypeface(tf);


        //Exit FRom MAinActivty
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putBoolean("state", false).apply();
                finishAffinity();
            }
        });


        name = sharedPreferences.getString("Name", "");
        tel = sharedPreferences.getString("Tel", "");
        addr = sharedPreferences.getString("Addr", "");
        des = sharedPreferences.getString("Des", "");
        time = sharedPreferences.getString("Time", "");
        price = sharedPreferences.getString("Price", "");


        AlertDialog.Builder d = new AlertDialog.Builder(this);
        final LayoutInflater inflater = (LayoutInflater) getLayoutInflater();

        View view = inflater.inflate(R.layout.price, null);
        TextView txtPrice = (TextView) view.findViewById(R.id.txtPrice);
        txtPrice.setTypeface(tf);
        DecimalFormat df = new DecimalFormat("###,###,###");
        price = df.format(Integer.parseInt(price));
        d.setView(view);
        final AlertDialog dialog = d.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.Save_Alert;
        dialog.show();

        //Put data In MainActivty
        txtPrice.setText(price);
        txtName.setText(name);
        txtTel.setText(tel);
        txtAddr.setText(addr);
        txtDes.setText(des);


        imgFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putInt("service", 0).apply();
                UpdateStatus updateStatus = new UpdateStatus(MainActivity.this, Flag.Driver);
                updateStatus.Responce_del = MainActivity.this;

                updateStatus.execute(
                        token,
                        "waiting"
                        , DatabaseLink.UpdateStatus);
                imgAccept.setAlpha(0.5f);
                imgAccept.setEnabled(false);
                imgFinish.setEnabled(false);
                imgFinish.setAlpha(0.5f);

            }

        });

        imgAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences.edit().putInt("service", 1).apply();

                imgAccept.setAlpha(0.5f);
                imgAccept.setEnabled(false);
                Gson gson = new Gson();
                MessagesInfo messagesInfo = new MessagesInfo();
                messagesInfo.Time = time;
                messagesInfo.number = token;
                String json = gson.toJson(messagesInfo);
                Messages_HTTP messages_http = new Messages_HTTP(MainActivity.this, Flag.Driver);
                messages_http.Responce_del = MainActivity.this;
                messages_http.execute(json, DatabaseLink.msg_tokrn);

            }
        });



    }

    @Override
    public void Response(Response response) throws IOException, JSONException {
        try {
            String src = response.body().string();

            JSONObject jsonObject = new JSONObject(src);
            String MSG = jsonObject.getString("Message");
            String RES = jsonObject.getString("Result");
            if (RES.equals("2") || RES.equals("0")) {
                Toast t = new Toast(getApplicationContext());
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.error, (ViewGroup) findViewById(R.id.Group));
                TextView textView = (TextView) view.findViewById(R.id.editText);
                textView.setText("راننده دیگری پیام را تایید کرده");
                t.setView(view);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.setDuration(Toast.LENGTH_SHORT);
                t.show();
            } else if (RES.equals("1")) {
                UpdateStatus updateStatus = new UpdateStatus(MainActivity.this, Flag.Driver);
                updateStatus.Responce_del = MainActivity.this;
                updateStatus.execute(
                        token,
                        "busy"
                        , DatabaseLink.UpdateStatus);
                imgAccept.setAlpha(0.5f);
                imgAccept.setEnabled(false);
                imgFinish.setAlpha(1f);
                imgFinish.setEnabled(true);

                Alerts.ShowAccept(MainActivity.this);
            }
        } catch (Exception ex) {
            Alerts.NoResponse(MainActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        sharedPreferences.edit().putBoolean("state", false).apply();
        super.onBackPressed();
    }

    @Override
    public void UpdateStatus_Response(Response response) throws IOException, JSONException {
        String src = response.body().string();
    }
}
