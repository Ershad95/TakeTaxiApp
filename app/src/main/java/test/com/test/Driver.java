package test.com.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

public class Driver extends AppCompatActivity implements AsyncResponse,AsyncUpdateStatus {

    TabHost tabHost;
    ImageView LogOut;
    ImageView status;
    RadioButton ready, busy;
    String token;
    Flag flag;
    TextView edtName,edtLname,edtEmail,edtPass;
    RecyclerView recylerview;
    TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        token = sharedPreferences.getString("Token", "");
        TabHost.TabSpec spec1 = tabHost.newTabSpec("TAB 1");
        TabHost.TabSpec spec2 = tabHost.newTabSpec("TAB 2");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("اطلاعات راننده", getResources().getDrawable(R.drawable.user));
        spec1.setContent(R.id.tab1);
        spec2.setIndicator("سرویس ها", getResources().getDrawable(R.drawable.message_box));
        spec2.setContent(R.id.tab2);
        tabHost.addTab(spec1);
        tabHost.addTab(spec2);

        LogOut = (ImageView) findViewById(R.id.imgLogOut);
        status = (ImageView) findViewById(R.id.imgStatus);

        ready = (RadioButton) findViewById(R.id.radioReady);
        busy = (RadioButton) findViewById(R.id.radioBusy);

        edtName = (TextView)findViewById(R.id.edtDriverName);
        edtLname = (TextView)findViewById(R.id.edtDriverLname);
        edtEmail = (TextView)findViewById(R.id.edtDriverEmail);
        edtPass = (TextView)findViewById(R.id.edtDriverPass);

        txtWelcome = (TextView)findViewById(R.id.txtWelcome);

        recylerview = (RecyclerView)findViewById(R.id.recylerview);

        Transiver_HTTP http = new Transiver_HTTP(Driver.this,Flag.Driver);
        http.Responce_del = Driver.this;
        http.execute(
                "",
                "",
                "",
                "",
                token
                , DatabaseLink.DRIVER);


        ready.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flag = Flag.Ready;
                    UpdateStatus updateStatus = new UpdateStatus(Driver.this, Flag.Driver);
                    updateStatus.Responce_del = Driver.this;
                    updateStatus.execute(
                            token,
                            "waiting"
                            , DatabaseLink.UpdateStatus);
                }
            }
        });

        busy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flag = Flag.Busy;
                    UpdateStatus updateStatus = new UpdateStatus(Driver.this, Flag.Driver);
                    updateStatus.Responce_del = Driver.this;
                    updateStatus.execute(
                            token,
                            "busy"
                            , DatabaseLink.UpdateStatus);
                }
            }
        });

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginStatus.SetStatus(Flag.LogOut);
                finish();
            }
        });


    }

    @Override
    public void ReturnResponse(Response response) throws IOException, JSONException {

        //Fetch Json String From Server

            String src = response.body().string();

            Gson json = new Gson();
            Template_Recive_data recive_data = json.fromJson(src,Template_Recive_data.class);



              DriverInfo info = recive_data.info;

              edtPass.setText(info.password);
              edtEmail.setText(info.username);
              edtLname.setText(info.lname);
              edtName.setText(info.name);

                txtWelcome.setText("آقای "+info.lname+" خوش آمدید");

              if(info.status.equals("waiting"))
              {
                  status.setImageResource(R.drawable.ready_status);
                  ready.setChecked(true);
              }
                if(info.status.equals("busy"))
              {
                  status.setImageResource(R.drawable.busy_status);
                  busy.setChecked(true);
              }

              ArrayList<MessagesInfo> messages = (ArrayList<MessagesInfo>) recive_data.messages;

            //Pass Data To RecyclerView
            recylerview.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
            recylerview.setLayoutManager(mLayoutManager);
            recylerview.setAdapter(new RecyclerManagerForMessage(messages, recylerview.getRootView(),Flag.Driver));




    }

    @Override
    public void UpdateStatus_Response(Response response) throws IOException, JSONException {
        String src = response.body().string();
        JSONObject jsonObject = new JSONObject(src);
        String MSG = jsonObject.getString("Message");
        String RES = jsonObject.getString("Result");
        if (RES.equals("0"))
            Alerts.ShowError(Driver.this);
        else {
            if (flag == Flag.Busy)
                status.setImageResource(R.drawable.busy_status);
            if (flag == Flag.Ready)
                status.setImageResource(R.drawable.ready_status);
        }
    }
}
