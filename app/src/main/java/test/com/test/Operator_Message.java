package test.com.test;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

public class Operator_Message extends AppCompatActivity implements AsyncResponse{
    RecyclerView recyclerView;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator__message);

        recyclerView= (RecyclerView)findViewById(R.id.recyclerMsgOperator);
        imgBack = (ImageView)findViewById(R.id.imgBack);

        Transiver_HTTP http = new Transiver_HTTP(this,Flag.Operator);
        http.Responce_del = this;
        http.execute(
                "",
                "",
                "",
                "",
                "ShowAll",
                DatabaseLink.Message);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Operator.class));
                finish();
            }
        });


    }
    @Override
    public void ReturnResponse(Response response) throws IOException, JSONException {
        //Fetch Json String From Host
        try {
            String src = response.body().string();

            Gson driveJson = new Gson();
            //Used Gson For Convert Json String To MessageInfo Object
            MessagesInfo[] msg = driveJson.fromJson(src, MessagesInfo[].class);
            ArrayList<MessagesInfo> msglist = new ArrayList<>();
            //add Data To msglist
            for (MessagesInfo instance : msg) {

                msglist.add(instance);
            }

            //Pass Data To RecyclerView
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(new RecyclerManagerForMSGopertator(msglist, recyclerView.getRootView()));
        } catch (Exception ex) {
            Alerts.NoResponse(Operator_Message.this);
        }

    }

}
