package test.com.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Response;

//this Class Manage Operator Action
public class Operator extends AppCompatActivity implements AsyncResponse {

    TabHost tabHost;
    Flag flag;
    ImageView imgOperator, imgInfo, imgExit, imgUpdate, imgMoney, imgMessageBox;
    ImageView btnCancel, btnSubmit;
    EditText edtPass, edtUser, edtName, edtLname, edtPassengerName, edtPassengerTel, edtPassengerAddress, edtDestinatin;
    EditText edtPrice, edtLocation;
    AlertDialog alertDialog;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);


        edtPassengerAddress = (EditText) findViewById(R.id.edtPassengerAddress);
        edtPassengerName = (EditText) findViewById(R.id.edtPassengerName);
        edtPassengerTel = (EditText) findViewById(R.id.edtPassengerTel);
        edtDestinatin = (EditText) findViewById(R.id.edtDestination);


        Intent get_reSend_Data = getIntent();
        boolean ReSend = get_reSend_Data.getBooleanExtra("ReSend", false);
        if (ReSend) {
            get_reSend_Data.putExtra("ReSend",false);
            edtPassengerName.setText(get_reSend_Data.getStringExtra("name"));
            edtPassengerTel.setText(get_reSend_Data.getStringExtra("tel"));
            edtPassengerAddress.setText(get_reSend_Data.getStringExtra("addr"));
            edtDestinatin.setText(get_reSend_Data.getStringExtra("destination"));
        }

        flag = Flag.ShowAllDriver;
        Transiver_HTTP http1 = new Transiver_HTTP(Operator.this, Flag.Operator);
        http1.Responce_del = Operator.this;
        http1.execute(
                "",
                "",
                "",
                "",
                ""
                , DatabaseLink.DriverList);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        imgOperator = (ImageView) findViewById(R.id.imgOperator);
        imgExit = (ImageView) findViewById(R.id.imgExit);
        imgUpdate = (ImageView) findViewById(R.id.imgUpdate);
        imgMoney = (ImageView) findViewById(R.id.imgMoney);
        imgMessageBox = (ImageView) findViewById(R.id.imgMessageBox);

        imgMessageBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Operator_Message.class));
            }
        });

//        imgMoney.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(Operator.this);
//                LayoutInflater inflater = getLayoutInflater();
//                View alertView = inflater.inflate(R.layout.prices, null);
//
//                btnCancel = (ImageView) alertView.findViewById(R.id.imgPriceClose);
//                btnSubmit = (ImageView) alertView.findViewById(R.id.imgPriceSubmit);
//
//
//                btnSubmit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!TextUtils.isEmpty(edtLocation.getText().toString())
//                                && !TextUtils.isEmpty(edtPrice.getText().toString())) {
//                            alertDialog.hide();
//                            alertDialog.dismiss();
//                        }
//                    }
//                });
//                btnCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alertDialog.hide();
//                        alertDialog.dismiss();
//                    }
//                });
//                builder.setView(alertView);
//                builder.setCancelable(false);
//                alertDialog = builder.create();
//                alertDialog.getWindow().getAttributes().windowAnimations = R.style.Save_Alert;
//                alertDialog.show();
//            }
//        });

        imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = Flag.ShowAllDriver;
                Transiver_HTTP http1 = new Transiver_HTTP(Operator.this, Flag.Operator);
                http1.Responce_del = Operator.this;
                http1.execute(
                        "",
                        "",
                        "",
                        "",
                        ""
                        , DatabaseLink.DriverList);
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edtPassengerName.getText().toString();
                String tel =  edtPassengerTel.getText().toString();
                String addr = edtPassengerAddress.getText().toString();
                String des =  edtDestinatin.getText().toString();

                if (name.length() <= 0 || tel.length() <= 0 || addr.length() <= 0 || des.length() <= 0) {
                    Alerts.ShowError(Operator.this);
                } else {
                    final Intent intent = new Intent(v.getContext(), Send_list.class);

                    intent.putExtra("Name", name);
                    intent.putExtra("Tel", tel);
                    intent.putExtra("Addr", addr);
                    intent.putExtra("Des", des);

                    final AlertDialog.Builder d  = new AlertDialog.Builder(Operator.this);
                    final LayoutInflater inflater = (LayoutInflater) getLayoutInflater();

                    View view =  inflater.inflate(R.layout.prices,null);
                    final EditText edtPrice = (EditText) view.findViewById(R.id.edtPrice);

                    ImageView img_submit = (ImageView) view.findViewById(R.id.imgPriceSubmit);
                    ImageView img_close = (ImageView) view.findViewById(R.id.imgPriceClose);

                    d.setView(view);
                    final AlertDialog dialog =  d.create();
                    dialog.getWindow().getAttributes().windowAnimations=R.style.Alert;
                    dialog.show();

                    img_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.hide();
                            dialog.dismiss();
                        }
                    });


                    img_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String p =  edtPrice.getText().toString();
                            intent.putExtra("Price",p);
                            startActivity(intent);
                            dialog.dismiss();
                            dialog.hide();
                        }
                    });




                }

            }
        });

        imgExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Auth.class));
                finish();
            }
        });
        imgInfo = (ImageView) findViewById(R.id.imgInfo);
        tabHost = (TabHost) findViewById(R.id.tabHost);

        recyclerView = (RecyclerView) findViewById(R.id.recylerview);


        imgInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Operator.this);
                LayoutInflater inflater = getLayoutInflater();
                View alertView = inflater.inflate(R.layout.add_operator, null);

                btnCancel = (ImageView) alertView.findViewById(R.id.btnCancel);
                btnSubmit = (ImageView) alertView.findViewById(R.id.btnSubmit);

                edtPass = (EditText) alertView.findViewById(R.id.edtNewPass);
                edtUser = (EditText) alertView.findViewById(R.id.edtnewUser);
                edtName = (EditText) alertView.findViewById(R.id.edtNewName);
                edtLname = (EditText) alertView.findViewById(R.id.edtNewLname);


                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(edtPass.getText().toString())
                                && !TextUtils.isEmpty(edtUser.getText().toString())
                                && !TextUtils.isEmpty(edtName.getText().toString())
                                && !TextUtils.isEmpty(edtLname.getText().toString())) {

                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.hide();
                        alertDialog.dismiss();
                    }
                });

                ImageView imgAlert = (ImageView) alertView.findViewById(R.id.imgAlert);
                TextView txtTitle = (TextView) alertView.findViewById(R.id.AlertTittle);

                imgAlert.setImageDrawable(getResources().getDrawable(R.drawable.edit));
                txtTitle.setText("ویرایش اطلاعات شما");
                builder.setView(alertView);
                builder.setCancelable(false);
                alertDialog = builder.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.Alert;
                alertDialog.show();
            }
        });

        tabHost.setup();
        imgOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Operator.this);
                LayoutInflater inflater = getLayoutInflater();
                View alertView = inflater.inflate(R.layout.add_operator, null);

                btnCancel = (ImageView) alertView.findViewById(R.id.btnCancel);
                btnSubmit = (ImageView) alertView.findViewById(R.id.btnSubmit);

                edtPass = (EditText) alertView.findViewById(R.id.edtNewPass);
                edtUser = (EditText) alertView.findViewById(R.id.edtnewUser);
                edtName = (EditText) alertView.findViewById(R.id.edtNewName);
                edtLname = (EditText) alertView.findViewById(R.id.edtNewLname);


                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(edtPass.getText().toString()) && !TextUtils.isEmpty(edtPass.getText().toString())) {
                            flag = Flag.Add_Operator;
                            Transiver_HTTP http = new Transiver_HTTP(Operator.this, Flag.Operator);
                            http.Responce_del = Operator.this;
                            http.execute(
                                    edtUser.getText().toString(),
                                    edtPass.getText().toString(),
                                    edtName.getText().toString(),
                                    edtLname.getText().toString(),
                                    ""
                                    , DatabaseLink.Add_Operator);
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.hide();
                        alertDialog.dismiss();
                    }
                });
                builder.setView(alertView);
                builder.setCancelable(false);
                alertDialog = builder.create();
                //Set Custome Style For AlertDialog
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.Alert;
                alertDialog.show();

            }
        });

        //Add Tab

        TabHost.TabSpec spec1 = tabHost.newTabSpec("TAB 1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("ارسال پیام");


        TabHost.TabSpec spec2 = tabHost.newTabSpec("TAB 2");
        spec2.setIndicator("لیست رانندگان");
        spec2.setContent(R.id.tab2);


        tabHost.addTab(spec1);
        tabHost.addTab(spec2);

    }

    //Recive Response from Host (Php Code)
    @Override
    public void ReturnResponse(final Response response) throws IOException, JSONException {
        try {
            String src = response.body().string();
//Detect Operation And Call Back Data From Host
            switch (flag) {
                case Add_Operator: {
                    // Retrive Response...
                    // fetch MSG Key in Json String
                    JSONObject jsonObject = new JSONObject(src);
                    String MSG = jsonObject.getString("Message");
                    // fetch Result Key in Json String
                    String RES = jsonObject.getString("Result");
                    //RES = 1----->Task Completed
                    if (RES.equals("1")) {
                        Log.d("Task", "ok");
                    } else {
                        Log.d("Task", "No");
                        Alerts.ShowError(Operator.this);
                    }
                    alertDialog.hide();
                    alertDialog.dismiss();
                    break;
                }
                case ShowAllDriver: {
                    // Retrive Response..
                    //use Gson Lib For Convert Json Data To Object
                    //used This Lib For Esay Use Json Data
                    Gson driveJson = new Gson();
                    //Fetch Data From Src (Json String) and Convet To DriverInfo Object
                    DriverInfo[] listDriver = driveJson.fromJson(src, DriverInfo[].class);

                    //Use ArrayList For RecyclerView in Ui...
                    final ArrayList<DriverInfo> allDriverList = new ArrayList<>();
                    //Fill allDriverList  with listDriver
                    for (int i = 0; i < listDriver.length; i++) {
                        allDriverList.add(listDriver[i]);
                    }

                    //Choose Size And Layout For RecycleView
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));//show Item in One Column
                    //pass Data To Recycler View
                    recyclerView.setAdapter(new RecyclerManagerForShowDriver(allDriverList));
                    break;
                }
            }
        } catch (Exception ex) {
            Alerts.NoResponse(Operator.this);
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
