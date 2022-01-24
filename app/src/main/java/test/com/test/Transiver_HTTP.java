package test.com.test;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

// this class Used For Connect Between  App And Host

public class Transiver_HTTP extends AsyncTask<String, Void, Response> {
    final int TIMEOUT=30;
    private final AlertDialog.Builder builder;
    private final Flag flag;
    private final AlertDialog progressDialog;
    public AsyncResponse Responce_del = null;
    Context context;

    Transiver_HTTP(Context ctx,Flag flag) {
        context = ctx;
        builder=new AlertDialog.Builder(ctx);
        this.flag = flag;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.progress_dialog,null);

        ImageView imgLoading = (ImageView) view.findViewById(R.id.imgProgressDilaog);
        TextView txtPrgs = (TextView) view.findViewById(R.id.txtProgressDialog);
        TextView txtPrgs2 = (TextView) view.findViewById(R.id.txt2ProgressDialog);

        if(flag == Flag.Operator)
        {
            imgLoading.setImageResource(R.drawable.loading_red);
            txtPrgs.setTextColor(ctx.getResources().getColor(R.color.frag2));
            txtPrgs2.setTextColor(ctx.getResources().getColor(R.color.frag2));
        }
        if(flag==Flag.Driver)
        {
            imgLoading.setImageResource(R.drawable.loading_blue);
            txtPrgs.setTextColor(ctx.getResources().getColor(R.color.frag4));
            txtPrgs2.setTextColor(ctx.getResources().getColor(R.color.frag4));
        }

        builder.setView(view);
        builder.setCancelable(false);
        progressDialog =   builder.create();
        progressDialog.getWindow().getAttributes().windowAnimations = R.style.ProgressDialog;
    }

    @Override
    protected Response doInBackground(String... params) {
        final String URL = params[5];
        try {

            OkHttpClient.Builder b = new OkHttpClient.Builder();
            b.connectTimeout(TIMEOUT,TimeUnit.SECONDS);
            OkHttpClient client = b.build();
//            b.writeTimeout(30, TimeUnit.SECONDS);
//            b.readTimeout(30, TimeUnit.SECONDS);

            RequestBody body = new FormBody.Builder()
                    .add("email", params[0].toString())
                    .add("pass",  params[1].toString())
                    .add("name",  params[2].toString())
                    .add("lname", params[3].toString())
                    .add("token", params[4].toString())
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .post(body)
                    .build();
            Call call = client.newCall(request);
            Response response = call.execute();
            return response;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Log.d(ex.getMessage(), "msg");
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
        protected void onPostExecute(Response response) {
            progressDialog.dismiss();
            progressDialog.hide();
            try {
                Responce_del.ReturnResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }
}