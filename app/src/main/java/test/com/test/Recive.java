package test.com.test;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.text.LoginFilter;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

//When Message Sent...this Class Recived Message
public class Recive extends FirebaseMessagingService {
    private static Intent intent;

//Message Recived...
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //put data to Recived Class

        SharedPreferences sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE);


          //RecivedData.state = true;
//        RecivedData.Name =  remoteMessage.getData().get("Name");
//        RecivedData.Tel = remoteMessage.getData().get("Tel");
//        RecivedData.Addr = remoteMessage.getData().get("Addr");
//        RecivedData.Des = remoteMessage.getData().get("Des");
//        RecivedData.Time = remoteMessage.getData().get("Time");
//        RecivedData.Price = remoteMessage.getData().get("Price");

        sharedPreferences.edit().putBoolean("state",true).apply();
        sharedPreferences.edit().putString("Name",remoteMessage.getData().get("Name")).apply();
        sharedPreferences.edit().putString("Tel",remoteMessage.getData().get("Tel")).apply();
        sharedPreferences.edit().putString("Addr",remoteMessage.getData().get("Addr")).apply();
        sharedPreferences.edit().putString("Des",remoteMessage.getData().get("Des")).apply();
        sharedPreferences.edit().putString("Time",remoteMessage.getData().get("Time")).apply();
        sharedPreferences.edit().putString("Price",remoteMessage.getData().get("Price")).apply();

        //check Login or Logout then Immplement Intent
        if (LoginStatus.GetStatus() == Flag.Login)
        {
            intent = new Intent(Recive.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }else
        {

            String tmpUser = sharedPreferences.getString("Login_user", "");
            String tmpPass = sharedPreferences.getString("Login_pass", "");
            String userType= sharedPreferences.getString("login_type","NOT_SET");

            intent = new Intent(getApplicationContext(), Auth.class);
            intent.putExtra("username",tmpUser);
            intent.putExtra("password",tmpPass);
            intent.putExtra("userType",userType);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        showNotification(getApplicationContext());
    }

    public static void showNotification(Context context)
    {
        PendingIntent pendingIntent = PendingIntent.getActivities(context,0, new Intent[]{intent},PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setVibrate(new long[]{250,250,250,250});
        builder.setColor(Color.GREEN);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);
        builder.setAutoCancel(true);
        builder.setContentText("پیام جدید");
        builder.setContentTitle("پیام از طرف اپراتور");
        builder.setTicker("تاکسی سرویس پارسی");
        builder.setSmallIcon(R.drawable.ic_notifi);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.icn));
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}

