package com.bytereal.byterealblesdk.broadcast.register;


import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import com.bytereal.byterealblesdk.broadcast.receiver.BluetoothBroadcastReceiver;


public class RegisteredBroadcast {
    private static final String TAG = "RegisteredBroadcast";
    private static BluetoothBroadcastReceiver bluetoothBroadcastReceiver;

    private static void ILOG(String log) {
        Log.i(TAG, log);
    }

    public static void registeredBluetoothBroadcast(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver();
        context.registerReceiver(bluetoothBroadcastReceiver, intentFilter);
        ILOG("broadcastreceiver registered!");
    }

    public static void unRegisterBluetoothBroadcast(Context context) {
        context.unregisterReceiver(bluetoothBroadcastReceiver);
        ILOG("broadcastreceiver unregistered!");

    }
}
