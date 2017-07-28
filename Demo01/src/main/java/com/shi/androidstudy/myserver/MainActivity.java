package com.shi.androidstudy.myserver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartServer(View view){
        startService(new Intent(getApplicationContext(),MyService.class));
    }

    public void onStopServer(View view){
        stopService(new Intent(getApplicationContext(),MyService.class));
    }
}
