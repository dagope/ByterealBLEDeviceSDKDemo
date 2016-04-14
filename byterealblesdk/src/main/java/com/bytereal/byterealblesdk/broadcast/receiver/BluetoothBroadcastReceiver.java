package com.bytereal.byterealblesdk.broadcast.receiver;


import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bytereal.byterealblesdk.service.IBeaconScanService;


public class BluetoothBroadcastReceiver extends BroadcastReceiver {
    private static Context context;
    private boolean isBluetoothEnabled = BluetoothAdapter.getDefaultAdapter().isEnabled();

    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Intent bindIntent = new Intent(context, IBeaconScanService.class);
        if ((this.isBluetoothEnabled) && (!IBeaconScanService.isRunning)) {
            context.startService(bindIntent);
        } else if ((!this.isBluetoothEnabled) &&
                (IBeaconScanService.isRunning)) {
            Intent stopIntent = new Intent(context, IBeaconScanService.class);
            context.stopService(stopIntent);
        }
    }

    public static void unBindService() {
        Intent stopIntent = new Intent(context, IBeaconScanService.class);
        context.stopService(stopIntent);
    }
}
