package model.test.hackernews.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import model.test.hackernews.R;
import model.test.hackernews.Utils.DialogUtils;
import model.test.hackernews.Utils.NetworkCheckUtility;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int START_DELAY = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(NetworkCheckUtility.isNetworkAvailable(SplashActivity.this)) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                }else{
                    DialogUtils.showNoNetworkDialog(SplashActivity.this,true);
                }
            }
        }, START_DELAY);
    }
}
