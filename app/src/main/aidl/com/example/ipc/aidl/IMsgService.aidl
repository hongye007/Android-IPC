// IMsgService.aidl
package com.example.ipc.aidl;
import com.example.ipc.aidl.Msg;
import com.example.ipc.aidl.IMsgListener;
// Declare any non-default types here with import statements

interface IMsgService {
    void sendMsg(in Msg msg);
    Msg pollMsg();
    void registerListener(IMsgListener listener);
    void unRegisterListener(IMsgListener listener);
}