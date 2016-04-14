package com.bytereal.byterealblesdk.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.bytereal.byterealblesdk.threadpoll.ThreadPoolHelper;


public class IBeaconScanService extends Service {
    /* 21 */   public static boolean isRunning = false;
    private static final String TAG = "IBeaconScanService";

    private void ILOG(String message) {
        Log.i(TAG, message);
    }

    public void onCreate() {

        super.onCreate();
        ILOG("service created ");
    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning = true;
        ThreadPoolHelper.execute(new IBeaconScanRunnable());
        return super.onStartCommand(intent, flags, startId);
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);

    }


    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        ILOG("service destroyed");
    }


    class IBeaconScanRunnable implements Runnable {
        IBeaconScanRunnable() {
        }

        public void run() {
            ILOG("scan runnable is executed!");
            IBeaconScanTask iBeaconUtil = new IBeaconScanTask();
            iBeaconUtil.startIBeaconScan(IBeaconScanService.this.getApplicationContext());
            try {
                Thread.sleep(IBeaconScanConfig.scanDurationTime);
                iBeaconUtil.stopIBeaconScan(IBeaconScanService.this.getApplicationContext());
                Thread.sleep(IBeaconScanConfig.scanSpacingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ThreadPoolHelper.execute(this);
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}

