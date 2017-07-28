package com.shi.androidstudy.myserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind被执行 " );
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
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
