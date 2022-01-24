package test.com.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;


public class Auth extends AppCompatActivity implements AsyncResponse {
    EditText username;
    EditText password;
    Context ctx = this; //cache this... Class Context for use in Othe Block Of Code
    String usr, pass;
    AlertDialog alertDialog;
    RadioButton radioDriver, radioOperator;
    boolean isDriver, isOprator;
    SharedPreferences sharedPreferences;
    Boolean message_accepted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        radioDriver = (RadioButton) findViewById(R.id.radioDriver);
        radioOperator = (RadioButton) findViewById(R.id.radioOperator);
        sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE);
        message_accepted = sharedPreferences.getBoolean("state", false);

        //determine App User (Operator Or Driver)
        radioDriver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDriver = isChecked;
            }
        });


        radioOperator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isOprator = isChecked;
            }
        });


        //Set Font For Text
        Typeface tf = Typeface.createFromAsset(getAssets(), "font/BK.ttf");
        radioDriver.setTypeface(tf);
        radioOperator.setTypeface(tf);

        final TextView txtUser = (TextView) findViewById(R.id.txtUser);
        final TextView txtPass = (TextView) findViewById(R.id.txtPass);
        username = (EditText) findViewById(R.id.edtUser);
        password = (EditText) findViewById(R.id.edtPass);

        Button btn_Send = (Button) findViewById(R.id.btnSend);
        Button btn_SignUp = (Button) findViewById(R.id.btnCancel);

        txtPass.setTypeface(tf);
        txtUser.setTypeface(tf);
        btn_SignUp.setTypeface(tf);
        btn_Send.setTypeface(tf);

        //Cache Data From SignUp Activity...this Code is Active when new User Signing Up in App;;;
        Intent intent = getIntent();
        username.setText(intent.getStringExtra("username"));
        password.setText(intent.getStringExtra("password"));
        String userType = intent.getStringExtra("userType");
        if (userType == "Driver") {
            radioDriver.setChecked(true);
        } else if (userType == "Operator") {
            radioOperator.setChecked(true);
        } else if (userType == "NOT_SET") {
            radioOperator.setChecked(false);
            radioDriver.setChecked(false);
        }

        //Check Internet Connection....
        if (!checkInternetConenction()) {
            Alerts.ShowWarning(Auth.this);
        }

        //Go To SighUp Activity
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean state = checkInternetConenction();
                if (state) {
                    Intent intent = new Intent(ctx, SignUp.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Alerts.ShowWarning(Auth.this);

                }

            }
        });

        //Send And Check Data (Pass,User)
        btn_Send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final boolean state = checkInternetConenction();
                if (state) {
                    usr = username.getText().toString();
                    pass = password.getText().toString();
                    if (!TextUtils.isEmpty(usr) && !TextUtils.isEmpty(pass)) {
                        // SendToken();

                        String token = sharedPreferences.getString("Token", "");
                        SingIn(usr, pass, token);
                    } else {
                        username.setHint("فیلد پر نشده");
                        password.setHint("فیلد پر نشده");
                        username.setHintTextColor(getResources().getColor(R.color.Errror));
                        password.setHintTextColor(getResources().getColor(R.color.Errror));
                        Alerts.ShowError(Auth.this);
                    }
                } else {
                    Alerts.ShowWarning(Auth.this);
                }
            }
        });
    }

    //Check pass and username for Auth
    private void SingIn(String mail, String pass, String token) {

        if (isDriver == false && isOprator == false) {
            Alerts.ShowError(Auth.this);
        } else if (isDriver) {
            Transiver_HTTP http = new Transiver_HTTP(this, Flag.Driver);
            http.Responce_del = this;
            http.execute(mail, pass, "", "", token, DatabaseLink.SignIn);
        } else if (isOprator) {
            Transiver_HTTP http = new Transiver_HTTP(this, Flag.Driver);
            http.Responce_del = Auth.this;
            http.execute(mail, pass, "", "", token, DatabaseLink.Operator);
        }


    }

    // Check Internet Connection
    private boolean checkInternetConenction() {
        // get Connectivity Manager object to check connection
        ConnectivityManager connec
                = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() ==
                android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else if (
                connec.getNetworkInfo(0).getState() ==
                        android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;
    }

    //Recive Response from Host (Php Code)
    @Override
    public void ReturnResponse(final Response response) throws IOException, JSONException {


        try {
            String src = response.body().string();

            JSONObject jsonObject = new JSONObject(src);
            String MSG = jsonObject.getString("Message");
            String RES = jsonObject.getString("Result");
            if (RES.equals("0")) {
                LoginStatus.SetStatus(Flag.LogOut);
                Alerts.ShowError(Auth.this);
            } else {
                LoginStatus.SetStatus(Flag.Login);
                SharedPreferences sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE);
                int show = sharedPreferences.getInt("Login_Rep", 1);
                if (show == 1) {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    View view = getLayoutInflater().inflate(R.layout.custome_dialog, null);
                    Button btnNever = (Button) view.findViewById(R.id.btnNever);
                    Button btnYes = (Button) view.findViewById(R.id.btnYes);
                    Button btnNotNow = (Button) view.findViewById(R.id.btnNotNow);

                    btnNever.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putInt("Login_state", 1);
                            editor.putInt("Login_Rep", 0);
                            editor.apply();
                            alertDialog.hide();
                            alertDialog.dismiss();

                            if (message_accepted) {
                                sharedPreferences.edit().putBoolean("state", false).apply();
                                startActivity(new Intent(ctx, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();
                            } else {
                                Alerts.ShowLogin(Auth.this);
                                if (isDriver) {
                                    startActivity(new Intent(ctx, Driver.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                } else if (isOprator) {
                                    startActivity(new Intent(ctx, Operator.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                }
                            }
                        }
                    });
                    btnNotNow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putInt("Login_state", 1);
                            editor.putInt("Login_Rep", 1);
                            editor.apply();
                            alertDialog.hide();
                            alertDialog.dismiss();
                            if (message_accepted) {
                                sharedPreferences.edit().putBoolean("state", false).apply();
                                startActivity(new Intent(ctx, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();
                            } else {
                                if (isDriver) {
                                    Alerts.ShowLogin(Auth.this);
                                    startActivity(new Intent(ctx, Driver.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                } else if (isOprator) {
                                    startActivity(new Intent(ctx, Operator.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                }
                            }
                        }
                    });
                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("Login_user", usr);
                            editor.putString("Login_pass", pass);
                            editor.putString("login_type", isDriver == true ? "Driver" : "Operator");
                            editor.putInt("Login_state", 0);
                            editor.putInt("Login_Rep", 0);
                            editor.apply();

                            alertDialog.hide();
                            alertDialog.dismiss();
                            if (message_accepted) {
                                sharedPreferences.edit().putBoolean("state", false).apply();
                                startActivity(new Intent(ctx, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();
                            } else {
                                Alerts.ShowLogin(Auth.this);
                                if (isDriver) {
                                    startActivity(new Intent(ctx, Driver.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                } else if (isOprator) {
                                    startActivity(new Intent(ctx, Operator.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                }
                            }
                        }
                    });


                    alertDialogBuilder.setView(view);
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                }
                if (show == 0) {

                    if (message_accepted) {
                        sharedPreferences.edit().putBoolean("state", false).apply();
                        startActivity(new Intent(ctx, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    } else {
                        Alerts.ShowLogin(Auth.this);
                        if (isDriver) {
                            startActivity(new Intent(ctx, Driver.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                        } else if (isOprator) {
                            startActivity(new Intent(ctx, Operator.class));
                            finish();
                        }
                    }
                }

            }
        } catch (Exception ex) {
            Alerts.NoResponse(Auth.this);
        }

    }


}
