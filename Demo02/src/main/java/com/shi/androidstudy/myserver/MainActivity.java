package com.shi.androidstudy.myserver;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    private MyService.DownloadBinder downloadBinder;


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;
            downloadBinder.startDownLoad();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartServer(View view){
        Intent bindIntent = new Intent(getApplicationContext(),MyService.class);
        bindService(bindIntent,connection,BIND_AUTO_CREATE);
    }

    public void onTestBind(View view){
        downloadBinder.startDownLoad();
        downloadBinder.getProgress();
    }

    public void onStopServer(View view){
        unbindService(connection);
    }

}
