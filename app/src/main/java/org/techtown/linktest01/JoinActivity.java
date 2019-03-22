package org.techtown.linktest01;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class JoinActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joinlayout);

        final EditText nameedit = (EditText)findViewById(R.id.editText1);
        final EditText pnumberedit = (EditText)findViewById(R.id.editText2);
        final EditText idedit = (EditText)findViewById(R.id.editText3);
        final EditText pw1edit = (EditText)findViewById(R.id.editText4);
        final EditText pw2edit = (EditText)findViewById(R.id.editText5);

        Button cancel = (Button)findViewById(R.id.Button01);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button checkid = (Button)findViewById(R.id.button);
        checkid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idedit.getText().toString();

                if (id.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다.").setPositiveButton("확인", null).create();
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                validate = true;
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                };

                ValidateRequest validateRequest = new ValidateRequest(id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(validateRequest);
            }
        });

        Button apply = (Button)findViewById(R.id.button1);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pnumber = pnumberedit.getText().toString();
                String id = idedit.getText().toString();
                String pw1 = pw1edit.getText().toString();
                String name = nameedit.getText().toString();
                String pw2 = pw2edit.getText().toString();

                if(!validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("먼저 중복 체크를 해주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                if(id.equals("") || pnumber.equals("") || pw1.equals("") || pw2.equals("") || name.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("빈칸없이 입력해 주세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                if(!pw1.equals(pw2)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                    dialog = builder.setMessage("비밀번호를 확인해 주세요.").setPositiveButton("확인", null).create();
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("회원 가입에 성공하였습니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("회원 가입에 실패하였습니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(pnumber, id, pw1, name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(registerRequest);
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
