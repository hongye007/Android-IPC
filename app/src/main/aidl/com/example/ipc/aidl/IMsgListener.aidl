// IMsgListener.aidl
package com.example.ipc.aidl;
import com.example.ipc.aidl.Msg;
// Declare any non-default types here with import statements

interface IMsgListener {
    void onMsgReceived(inout Msg msg);
}