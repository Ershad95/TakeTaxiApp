package test.com.test;

/**
 * Created by Ershad on 6/10/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
//Dedicate Token For Device and Store With SharedPrefrences...
public class Token extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Token", refreshedToken);//put token in Token Key
        editor.putBoolean("IsNew", true); // when the new token generated...
        Log.d("Refres", refreshedToken); // log Token.
        editor.apply(); // save Key And Value
    }

}
