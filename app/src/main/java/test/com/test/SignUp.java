package test.com.test;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import okhttp3.Response;


public class SignUp extends AppCompatActivity implements AsyncResponse {


    EditText edtEmail, edtpass, edtname, edtlname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button btn_Reg = (Button) findViewById(R.id.btnReg);
        Button btn_Ret = (Button) findViewById(R.id.btnRet);

        Typeface tf = Typeface.createFromAsset(getAssets(), "font/BK.ttf");

        edtEmail = (EditText) findViewById(R.id.mail);
        edtpass = (EditText) findViewById(R.id.pass);
        edtname = (EditText) findViewById(R.id.edtname);
        edtlname = (EditText) findViewById(R.id.edtlname);


        btn_Reg.setTypeface(tf);
        btn_Ret.setTypeface(tf);


        btn_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = "";
                String pass = "";
                String name = "";
                String lname = "";
                try {
                    mail = edtEmail.getText().toString().trim();
                    pass = edtpass.getText().toString().trim();
                    name = edtname.getText().toString().trim();
                    lname = edtlname.getText().toString().trim();

                } catch (Exception ex) {
                    Log.e("Error", ex.getMessage());
                }


                if (!TextUtils.isEmpty(mail) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(lname)) {
                    try {
                        SignUpUSer(mail, pass, name, lname);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (TextUtils.isEmpty(mail)) {
                        edtEmail.setHint("این فیلد پر نشده");
                        edtEmail.setHintTextColor(getResources().getColor(R.color.Errror));
                    }
                    if (TextUtils.isEmpty(pass)) {
                        edtpass.setHint("این فیلد پر نشده");
                        edtpass.setHintTextColor(getResources().getColor(R.color.Errror));
                    }
                    if (TextUtils.isEmpty(name)) {
                        edtname.setHint("این فیلد پر نشده");
                        edtname.setHintTextColor(getResources().getColor(R.color.Errror));
                    }
                    if (TextUtils.isEmpty(lname)) {
                        edtlname.setHint("این فیلد پر نشده");
                        edtlname.setHintTextColor(getResources().getColor(R.color.Errror));
                    }

                    Alerts.ShowError(SignUp.this);
                }


            }
        });

    }

    private void SignUpUSer(String email, String password, String name, String lname) throws ExecutionException, InterruptedException, IOException {

        SharedPreferences sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", "");
        Transiver_HTTP http = new Transiver_HTTP(this,Flag.Driver);
        http.Responce_del = this;
        http.execute(email, password, name, lname, token, DatabaseLink.SignUp);
    }

    @Override
    public void ReturnResponse(Response response) throws IOException, JSONException {

        try {
            String src = response.body().string();

            JSONObject jsonObject = new JSONObject(src);
            String MSG = jsonObject.getString("Message");
            String RES = jsonObject.getString("Result");
            if (RES.equals("0")) {
               Alerts.ShowError(SignUp.this);
            } else {
                Toast t = new Toast(getApplicationContext());
                LayoutInflater inflater = getLayoutInflater();
                View v2 = inflater.inflate(R.layout.adduser, (ViewGroup) findViewById(R.id.Group));
                t.setView(v2);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.setDuration(Toast.LENGTH_SHORT);
                t.show();
                startActivity(new Intent(this, Auth.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .putExtra("username", edtEmail.getText().toString())
                        .putExtra("password", edtpass.getText().toString()));
            }
        } catch (Exception ex) {
            Alerts.NoResponse(SignUp.this);
        }

    }

    public void OnReturn(View view) {
        startActivity(new Intent(this, Auth.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }


}
