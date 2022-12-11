package com.example.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ipc.APP;

public class MsgService extends Service {
    private static final String TAG = "IPC-AidlService-" + APP.processName();

    private int ids = 100;
    private RemoteCallbackList<IMsgListener> mListener = new RemoteCallbackList<>();

    private Binder serveBinder = new IMsgService.Stub() {

        @Override
        public void sendMsg(Msg msg) throws RemoteException {
            Log.d(TAG, "serve: receive msg [" + msg.toString() + "]");
            // run in main thread
            int count = mListener.beginBroadcast();
            for (int i = 0; i < count; i++) {
                IMsgListener listener = mListener.getBroadcastItem(i);
                if (listener != null) {
                    listener.onMsgReceived(msg);
                }
            }
            mListener.finishBroadcast();
        }

        @Override
        public Msg pollMsg() throws RemoteException {
            return new Msg(ids++, "from serve");
        }

        @Override
        public void registerListener(IMsgListener listener) throws RemoteException {
            mListener.register(listener);
        }

        @Override
        public void unRegisterListener(IMsgListener listener) throws RemoteException {
            mListener.unregister(listener);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serveBinder;
    }
}
