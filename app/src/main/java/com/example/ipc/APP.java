package com.example.ipc;

import android.app.Application;
import android.os.Build;
import android.util.Log;

public class APP extends Application {
    private static final String TAG = "IPC-APP-";
    private static String  processName = "";

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            processName = getProcessName();
            Log.d(TAG + processName, "application created : " + processName);
        }
    }

    public static String processName() {
        return processName;
    }
}
