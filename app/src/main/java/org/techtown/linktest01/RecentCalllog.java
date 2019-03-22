package org.techtown.linktest01;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class RecentCalllog extends AppCompatActivity {
    int i;
   // private ListView lstNames;
    private AlertDialog dialog;
    private ListView lstNames02;
    private List<String> contacts;
    private TextView t1;
    String findname="";
    String findcallnumber="";
    String finddate ="";
    String nName ="";

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    ArrayList<String> calltype = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> callnumber = new ArrayList<>();
    ArrayList<String> duration = new ArrayList<>();
    ArrayList<String> calltime= new ArrayList<>();
    ImageView view03;
    StringBuffer sb;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        this.lstNames02 = (ListView) findViewById(R.id.lstNames02);
        this.view03 = (ImageView) findViewById(R.id.imageView3);
        showContacts();

        view03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main4Activity.class);
                startActivityForResult(intent, 101);


            }
        });

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  this.lstNames = (ListView) findViewById(R.id.lstNames);
        this.lstNames02 = (ListView) findViewById(R.id.lstNames02);
        this.view03 = (ImageView) findViewById(R.id.imageView3);


        showContacts();
        view03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Main4Activity.class);
                startActivityForResult(intent, 101);


            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==101){
            nName=   data.getStringExtra("name");//문자열 받기
            try{
                Searchname(nName);
            }
            catch(Exception e){

            }


        }
    }
    public void Searchname(String nName) throws  Exception{
        int  number = 0;
        String temp[];
        String temp2[];


        String resultDate;
        int count = 0;
        /*
        temp2 = temp[2].split(":");
        findcallnumber =temp2[1].trim();
        */
        for (number=0; number<contacts.size() ;number++){
            String conn =contacts.get(number).toString();
            temp=conn.split("\n");



            temp2 = temp[1].split(":");

            findname =temp2[1].trim();


            if(nName.equals(findname)){
                if(count ==0) {
                    count++;
                    temp2 = temp[3].split(":");
                    finddate = temp2[1].trim();
                    temp2 = temp[2].split(":");

                    findcallnumber = temp2[1].trim();


                }
                else{
                    String tempSplit2[] = temp[3].split(":");
                    resultDate = tempSplit2[1].trim();
                    if(DateFormat(finddate)<DateFormat(resultDate)  ){
                        finddate = resultDate;


                    }

                }
                /*
                for( j = 0; j<contacts.size(); j++){
                    String tempContacts = contacts.get(j).toString();
                    String tempSplit[] = tempContacts.split("\n");

                    String tempSplit2[] = tempSplit[3].split(":");
                    String resultDate = tempSplit2[1].trim();

                    if(DateFormat(finddate)<DateFormat(resultDate)  ){


                    }
                }
                */

            }


        }
        new Alert();



    }

    public long DateFormat(String d) throws  Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = df.parse(d);

        return date.getTime();

    }



        public void Test03() {
                String[] str = sb.toString().split("/");

                String type = str[0];
                String name = str[1];
                String pnumber = str[2];
                String calltime = str[3];
                String duration = str[4];

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            Toast.makeText(getApplicationContext(), "갱신에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "갱신에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            };

                CallRequest callRequest = new CallRequest(type, name, pnumber, calltime, duration, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RecentCalllog.this);
                queue.add(callRequest);

        }

    class Alert{
        private AlertDialog.Builder alertDialog;
        Intent inputIntent;

        public Alert() throws Exception{
           startAlarm();
        }
        public void startAlarm() throws Exception{

            Intent intent = new Intent(RecentCalllog.this, MyReceiver.class);
                    intent.putExtra("phoneNumber",findcallnumber);
            intent.putExtra("name",nName);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(RecentCalllog.this.getApplicationContext(), 234324243, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(DateFormat(finddate));

            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+
                    (5*1000), pendingIntent);
        }
    }
        // 연락처 목록 가져오기

        /*
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        startActivityForResult(intent, 101);
        */


    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or notT.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //권한 부여하는 과정
        } else {
            // 권한이 있거나 혹은 얻었을 시 시작하는 부분
            // List<String> contacts = getContactNames();
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            //lstNames.setAdapter(adapter);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //권한 부여하는 과정
        } else {
            // 권한이 있거나 혹은 얻었을 시 시작하는 부분
            contacts = getContactNames02();



           ArrayAdapter<String> adapter = new MylistAdapter(this, contacts);
           lstNames02.setAdapter(adapter);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // Permission is granted
                        showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }



    }

    /**
     * Read the name of all the contacts.
     *
     * @return a list of names.
     */
    /*
    private List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
       // Cursor cursor02 =cr.query(CallLog.Calls.CONTENT_URI,null,null,null,CallLog, CallLog.Calls.DEFAULT_SORT_ORDER);


        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
              // String callnumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));

                contacts.add(name);
              //  contacts.add(callnumber);

            } while (cursor.moveToNext());
        }


        return contacts;
    }
    */
    private List<String> getContactNames02() {

        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        //String[] selectionArgs = { CallLog.Calls.OUTGOING_TYPE + "CallLog.Calls.OUTGOING_TYPE"}; //수신 발신 모두  CallLog.Calls.INCOMING_TYPE, CallLog.Calls.OUTGOING_TYPE
        Cursor cursor[] = new Cursor[2];
        cursor[0] = managedQuery(CallLog.Calls.CONTENT_URI, null, CallLog.Calls.TYPE + "= ?",
                new String[]{ String.valueOf(CallLog.Calls.OUTGOING_TYPE ) },                CallLog.Calls.DEFAULT_SORT_ORDER );
       // ContactsContract.Contacts.DISPLAY_NAME
        cursor[1] = managedQuery(CallLog.Calls.CONTENT_URI, null, CallLog.Calls.TYPE + "= ?",
                new String[]{ String.valueOf(CallLog.Calls.INCOMING_TYPE   ) },     CallLog.Calls.DEFAULT_SORT_ORDER);

        //.equals(MESSAGE_TYPE_INBOX)
        //,MESSAGE_TYPE_SENT
        //equals(MESSAGE_TYPE_CONVERSATIONS))
                /*
                public static final String MESSAGE_TYPE_INBOX = "1";
                public static final String MESSAGE_TYPE_SENT = "2";
                public static final String MESSAGE_TYPE_CONVERSATIONS = "3";
                public static final String MESSAGE_TYPE_NEW = "new";
                */
        for(i=0 ; i <2 ;i++) {


            if (cursor[i].moveToFirst()) {
                // Iterate through the cursor
                do {
                   sb = new StringBuffer();
                    int count = 0;
                /*
                calltype.add( cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)));
                name.add( cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME)));
                duration.add( cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)));
                callnumber.add( cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION)));
                calltime.add( timeToString(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE))));
                */
                    if (count % 2 == 0) {

                    }
                    //타입
                    String ttype = cursor[i].getString(cursor[i].getColumnIndex(CallLog.Calls.TYPE));
                    if(ttype.equals("2")){
                        sb.append("발신 통화내역\n");
                    }
                    else{
                        sb.append("수신 통화내역\n");
                    }

                    if(cursor[i].getString(cursor[i].getColumnIndex(CallLog.Calls.CACHED_NAME))==null){
                        sb.append("이름 : ").append("이름없음").append("\n");
                    }
                    else{
                        sb.append("이름 : ").append(cursor[i].getString(cursor[i].getColumnIndex(CallLog.Calls.CACHED_NAME))).append("\n");

                    }
                    //이름

                    sb.append("전화번호 : ").append(cursor[i].getString(cursor[i].getColumnIndex(CallLog.Calls.NUMBER))).append("\n");
                    //번호

                    String callDate02 = timeToString(cursor[i].getLong(cursor[i].getColumnIndex(CallLog.Calls.DATE)));
                    sb.append("전화 시작날짜 : ").append(callDate02).append("\n");
                    CalDate d = new CalDate();
                    d.doDiffOfDate(callDate02);
                    String Test = String.valueOf(d.SendDate());
                    //날짜

                    String duration = cursor[i].getString(cursor[i].getColumnIndex(CallLog.Calls.DURATION));
                    int sec = Integer.parseInt(duration);
                    int min = 0;
                    int ti = 0;
                    while (true) {
                        if (sec >= 60) {
                            min++;
                            sec = sec - 60;
                            if (min >= 60) {
                                ti++;
                                min = min - 60;
                            }
                        } else {
                            break;
                        }
                    }
                    duration = ti + "시" + min + "분" + sec + "초";
                    sb.append("총 전화시간 : ").append(duration).append("\n");
                    //시간

                    sb.append("!" + Test);

                    contacts.add(sb.toString());

                    //Test03();

                } while (cursor[i].moveToNext());
            }
        }


        return contacts;
    }
    private String timeToString(Long time) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleFormat.format(new Date(time));
        return date;
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}