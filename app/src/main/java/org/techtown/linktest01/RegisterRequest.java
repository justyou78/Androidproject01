package org.techtown.linktest01;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 안동규 on 2018-01-11.
 */

public class RegisterRequest extends StringRequest {
    final static private String URL = "http://jinhui0131.cafe24.com/Register.php";
    private Map<String, String> parameters;

    public RegisterRequest(String pnumber, String id, String pw, String name, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("pnumber", pnumber);
        parameters.put("id", id);
        parameters.put("pw", pw);
        parameters.put("name", name);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
