package org.techtown.linktest01;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 안동규 on 2018-01-10.
 */

public class CallRequest extends StringRequest {
    final static private String URL = "http://jinhui0131.cafe24.com/CallList.php";
    private Map<String, String> parameters;

    public CallRequest(String type, String name, String pnumber, String calltime, String duration, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("type", type);
        parameters.put("name", name);
        parameters.put("pnumber", pnumber);
        parameters.put("calltime", calltime);
        parameters.put("duration", duration);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
