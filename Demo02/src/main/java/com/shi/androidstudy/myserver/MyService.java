package com.shi.androidstudy.myserver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class MyService extends Service {

    DownloadBinder mbinder = new DownloadBinder();
    class DownloadBinder extends Binder{

            public void startDownLoad(){
                Log.e(TAG, "startDownLoad: 被执行");
            }

            public int getProgress(){
                Log.e(TAG, "getProgress: 被执行");
                return 0;
            }
    }

    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind被执行 " );
        return mbinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate被执行 " );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand被执行 " );
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy被执行 " );
        super.onDestroy();
    }
}
