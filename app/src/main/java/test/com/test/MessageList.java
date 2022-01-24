package test.com.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

public class MessageList extends AppCompatActivity implements AsyncResponse {

    RecyclerView recylerviewMsg;
    TextView txtEmpty;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        recylerviewMsg = (RecyclerView) findViewById(R.id.recylerviewMsg);
        //Retrive Token
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");

        txtEmpty = (TextView) findViewById(R.id.txtEmpty);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        //Send Token To Messaages Link For Show All Message ...
        Transiver_HTTP http = new Transiver_HTTP(MessageList.this,Flag.Operator);
        http.Responce_del = MessageList.this;
        http.execute(
                "",
                "",
                "",
                "",
                token
                , DatabaseLink.Message);


    }

    //Response From Host
    @Override
    public void ReturnResponse(Response response) throws IOException, JSONException {
        //Fetch Json String From Server
        try {
            String src = response.body().string();

            Gson driveJson = new Gson();
            //Used Gson For Convert Json String To MessageInfo Object
            MessagesInfo[] msg = driveJson.fromJson(src, MessagesInfo[].class);
            ArrayList<MessagesInfo> msglist = new ArrayList<>();
            //add Data To msglist
            if (msg.length > 0) {
                txtEmpty.setVisibility(View.GONE);
                frameLayout.setVisibility(View.GONE);
                for (MessagesInfo instance : msg) {

                    msglist.add(instance);
                }
            } else {
                txtEmpty.setVisibility(View.VISIBLE);
                frameLayout.setVisibility(View.VISIBLE);
            }
            //Pass Data To RecyclerView
            recylerviewMsg.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
            recylerviewMsg.setLayoutManager(mLayoutManager);
            recylerviewMsg.setAdapter(new RecyclerManagerForMessage(msglist, recylerviewMsg.getRootView(),Flag.Operator));
        } catch (Exception ex) {
           Alerts.NoResponse(MessageList.this);
        }

    }

}
