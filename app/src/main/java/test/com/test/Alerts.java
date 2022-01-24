package test.com.test;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class Alerts {
    public static void ShowError(Context ctx){
        Toast t = new Toast(ctx);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v2 = inflater.inflate(R.layout.error,null);
        t.setView(v2);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.setDuration(Toast.LENGTH_SHORT);
        t.show();
    }
    public static void ShowWarning(Context ctx){
        Toast t = new Toast(ctx);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v2 = inflater.inflate(R.layout.warnning, null);
        t.setView(v2);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.setDuration(Toast.LENGTH_SHORT);
        t.show();
    }
    public static void ShowLogin(Context ctx){
        Toast t = new Toast(ctx);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v2 = inflater.inflate(R.layout.login, null);
        t.setView(v2);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.setDuration(Toast.LENGTH_SHORT);
        t.show();
    }
    public static void NoResponse(Context ctx){
        Toast t = new Toast(ctx);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v2 = inflater.inflate(R.layout.no_response, null);
        t.setView(v2);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.setDuration(Toast.LENGTH_LONG);
        t.show();
    }
    public static void ShowAccept(Context ctx){
        Toast t = new Toast(ctx);
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.accept,null);
        t.setView(view);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.setDuration(Toast.LENGTH_LONG);
        t.show();
    }
}
