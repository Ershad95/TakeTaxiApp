package test.com.test;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import okhttp3.Response;

public class Send_list extends AppCompatActivity implements AsyncResponse,AsyncSendToServer {

    RecyclerView send_list;
    List<String> ReadyDriver;
    AlertDialog alertDialog;
    RecyclerManagerForSend adpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_list);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        ReadyDriver = new ArrayList<>();
        send_list= (RecyclerView)findViewById(R.id.send_list);
        Transiver_HTTP http = new Transiver_HTTP(Send_list.this,Flag.Operator);
        http.Responce_del = Send_list.this;
        http.execute(
                "",
                "",
                "",
                "",
                "",
                 DatabaseLink.WaitingDriver);

        Intent intent = getIntent();
        final String name =  intent.getStringExtra("Name");
        final String tel =  intent.getStringExtra("Tel");
        final String address =  intent.getStringExtra("Addr");
        final String destination =  intent.getStringExtra("Des");
        final String price = intent.getStringExtra("Price");
        final String date =  formattedDate;





        ImageView sendToDriver = (ImageView)findViewById(R.id.sendToDriver);
        sendToDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater view = getLayoutInflater();
                View alertView =  view.inflate(R.layout.accept_send_msg,null);

               ImageView img_send =  (ImageView) alertView.findViewById(R.id.btnSentToDriver);
               ImageView img_cancel = (ImageView) alertView.findViewById(R.id.btnCancel);

                AlertDialog.Builder builder = new AlertDialog.Builder(Send_list.this);
                builder.setCancelable(false);
                builder.setView(alertView);
                alertDialog=  builder.create();
                alertDialog.getWindow().getAttributes().windowAnimations=R.style.Alert;
                alertDialog.show();
                img_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        adpter = (RecyclerManagerForSend) send_list.getAdapter();
                        ReadyDriver.clear();
                        for (int i=0;i<adpter.getItemCount();i++) {
                            if (adpter.list2.get(i).check) {
                                ReadyDriver.add(adpter.list2.get(i).token);
                            }
                        }

                            Template_send_data data = new Template_send_data();
                            data.msg.Time=date;
                            data.msg.PassengerName = name;
                            data.msg.PassengerTel = tel;
                            data.msg.Address=address;
                            data.token=  ReadyDriver;
                            data.msg.Destination=destination;
                            data.msg.Price = price;
                            Gson gson = new Gson();
                            String jsonString =  gson.toJson(data);


                        Messages_HTTP http = new Messages_HTTP(Send_list.this,Flag.Operator);
                        http.Responce_del = Send_list.this;
                        http.execute(jsonString, DatabaseLink.SendNotification);
                    }
                });

                img_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.hide();
                        alertDialog.dismiss();
                    }
                });

            }
        });
    }

    @Override
    public void ReturnResponse(Response response) throws IOException, JSONException {
        String src = response.body().string();
        if(src==null)
        {
            Alerts.NoResponse(Send_list.this);
        }else
        {
            Gson driveJson = new Gson();
            DriverInfo[] listDriver = driveJson.fromJson(src,DriverInfo[].class);

            final ArrayList<DriverInfo> WaitingList = new ArrayList<>();
            for (int i = 0; i < listDriver.length; i++) {
                WaitingList.add(listDriver[i]);
            }


            send_list.setLayoutManager(new LinearLayoutManager(this));
            send_list.setAdapter(new RecyclerManagerForSend(WaitingList,send_list.getRootView()));
            send_list.setHasFixedSize(true);
        }

    }

    @Override
    public void Response(Response response) throws IOException, JSONException {


        try {
            String src =  response.body().string();
            finish();
        }catch (Exception ex)
        {
           Alerts.NoResponse(Send_list.this);
        }


        alertDialog.hide();
        alertDialog.dismiss();
    }


}
