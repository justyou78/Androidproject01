package org.techtown.linktest01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by 안동규 on 2018-01-11.
 */

public class AlertActivity extends Activity{


    Intent outputIntent;
    String phoneNum;

    String name;


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main4);
        super.onCreate(savedInstanceState);

        outputIntent = getIntent();
        phoneNum =outputIntent.getStringExtra("callNumber");

        name = outputIntent.getStringExtra("name");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("메세지 왔다리!");

        alertDialog.setMessage(name+"!!! 이대로 가면 어사각 인정??                                                              어~~~ 인정");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("통화",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("MissingPermission")
                    public void onClick(DialogInterface dialog, int which) {
                       Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: "+phoneNum));
                        call.setData(Uri.parse("tel: " + phoneNum));
//                        call.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(call);
                        /*
                        String text= editText.getText().toString();
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + text));
                        startActivity(intent);
                        */
                    }
                });
        alertDialog.setNegativeButton("거절",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        name=   data.getStringExtra("name");//문자열 받기
//        phoneNum=   data.getStringExtra("callNumber");//문자열 받기
//        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
//
//
//
//
//    }


}
