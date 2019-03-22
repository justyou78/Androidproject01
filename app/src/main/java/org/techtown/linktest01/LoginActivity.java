package org.techtown.linktest01;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);

        final EditText idedit = (EditText)findViewById(R.id.editText1);
        final EditText pwedit = (EditText)findViewById(R.id.editText2);

        Button cancel = (Button)findViewById(R.id.button2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button clicklogin = (Button) findViewById(R.id.clicklogin);
        clicklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idedit.getText().toString();
                String pw = pwedit.getText().toString();

                if (id.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    dialog = builder.setMessage("아이디를 입력해 주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                if(pw.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    dialog = builder.setMessage("비밀번호를 입력해 주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인에 성공 하였습니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                Intent rc = new Intent(getApplicationContext(), RecentCalllog.class);
                                startActivity(rc);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인에 실패 하였습니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(id, pw, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

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
