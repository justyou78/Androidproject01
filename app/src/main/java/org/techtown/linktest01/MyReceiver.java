package org.techtown.linktest01;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    NotificationManager notificationManager;
    String name="";
    String callNumber="";
    Intent intent;
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;


//        trIntent.setClass(context, AlertActivity.class);
//        trIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        name =intent.getStringExtra("name");
        callNumber =intent.getStringExtra("phoneNumber");
        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();



        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle("Message")
                .setContentText(name+"에게 메시지 왔다리!!")
                .setSmallIcon(R.drawable.message);

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1234,builder.build());


        Intent ii = new Intent(context,AlertActivity.class);
        ii.putExtra("name",name);
        ii.putExtra("callNumber",callNumber);
        context.startActivity(ii);



    }


}