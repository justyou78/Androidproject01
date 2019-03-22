package org.techtown.linktest01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by 안동규 on 2018-01-09.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException e){
                e.printStackTrace();

        }
        startActivity(new Intent(this, Login.class));
        finish();
    }
}
