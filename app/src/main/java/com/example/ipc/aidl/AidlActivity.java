package com.example.ipc.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ipc.APP;
import com.example.ipc.R;
import com.example.ipc.utils.Constants;

public class AidlActivity extends AppCompatActivity {

    private static String TAG = "IPC-AidlActivity-" + APP.processName();
    private IMsgService msgService;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case Constants.MSG_FROM_SERVE:
                    Log.d(TAG, "handleMessage: new book arrived " + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        Intent intent = new Intent(this, MsgService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    public void sendMsg(View v) {
        try {
            msgService.sendMsg(new Msg(0, "from client"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void pollMsg(View v) {
        try {
            Msg msg = msgService.pollMsg();
            ((TextView) findViewById(R.id.tv)).setText("Client: receive msg [" + msg.toString() + "]");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        unbindService(serviceConnection);
        super.onDestroy();
    }

    private IMsgListener listener = new IMsgListener.Stub() {
        @Override
        public void onMsgReceived(Msg msg) throws RemoteException {
            handler.obtainMessage(Constants.MSG_FROM_SERVE, msg).sendToTarget();
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            msgService = IMsgService.Stub.asInterface(service);
            try {
                msgService.registerListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}