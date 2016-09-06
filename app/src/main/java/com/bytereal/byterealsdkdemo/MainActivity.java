package com.bytereal.byterealsdkdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bytereal.byterealblesdk.broadcast.receiver.BluetoothBroadcastReceiver;
import com.bytereal.byterealblesdk.broadcast.register.RegisteredBroadcast;
import com.bytereal.byterealblesdk.service.IBeacon;
import com.bytereal.byterealblesdk.service.IBeaconScanConfig;
import com.bytereal.byterealblesdk.service.IBeaconScanService;
import com.bytereal.byterealblesdk.service.IBeaconScanTask;


public class MainActivity extends AppCompatActivity implements IBeaconScanTask.NewIBeaconCallback {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private static final int CODE_PERMISSIONS_GROUP = 1;
    TextView tvLog;
    ScrollView scrollView;

    Context context = null;
    Intent serviceBL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        tvLog = (TextView) findViewById(R.id.tvLog);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        serviceBL = new Intent(this.context, IBeaconScanService.class);



        if(AppPermissions.checkAllPermissions(getApplicationContext())){
            InitializeApp();
        }
        else{
            //grantPermissions();
            if(AppPermissions.shouldShowAnyRequestPermisions(this, AppPermissions.permissionsApp)){
                //mostramos mensaje de permisos
                AppPermissions.showDialogAllRequestPermissions(this, AppPermissions.permissionsApp, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //solicitamos los permisos
                        AppPermissions.requirePermissions(MainActivity.this, AppPermissions.permissionsApp, CODE_PERMISSIONS_GROUP);
                    }
                });
            }
            else{
                //solicitamos los permisos
                AppPermissions.requirePermissions(MainActivity.this, AppPermissions.permissionsApp, CODE_PERMISSIONS_GROUP);
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    InitializeApp();

                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }


    private void InitializeApp() {
        // Set callback interface
        IBeaconScanConfig.newIBeaconCallback = this;
// Set signal loss longer than the length of the device is not found again, determined to be signal loss
// Default is 15 seconds
// Recommendation (scanning + interval) * 3. That is, three times did not find the device that the signal was lost
        IBeaconScanConfig.scanCacheDurationTime = 15000;
// Scan length, the default is 3 seconds. Please consider the actual demand and consumption of electricity
        IBeaconScanConfig.scanDurationTime = 3000;
// Scan interval, the default is 2 seconds. Please consider the actual demand and consumption of electricity
        IBeaconScanConfig.scanSpacingTime = 2000;
        // After Register here Bluetooth status listen to the broadcast, please note the order, be sure to turn on Bluetooth Radio Open
// Here please turn on Bluetooth, then SDK will open BLE Scan Service
        RegisteredBroadcast.registeredBluetoothBroadcast(this.context);
        this.context.startService(this.serviceBL);
    }

    @Override
    public void findNewIBeacon(IBeacon paramIBeacon) {
        writeLogView(paramIBeacon);
        Log.i(TAG, paramIBeacon.toString());
    }

    private void CalculateDistance(IBeacon beacon){

    }

    @Override
    public void endOfTheScan() {
        writeLogView("scan stopped!!take time to do what u want to");
        writeLogView("devices are " + IBeaconScanConfig.IBeaconListCache.toString());
        Log.w(TAG, "scan stopped!!take time to do what u want to");
        Log.w(TAG, "devices are " + IBeaconScanConfig.IBeaconListCache.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.serviceBL != null)
        {
            RegisteredBroadcast.unRegisterBluetoothBroadcast(this);
            context.stopService(this.serviceBL);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(this.serviceBL != null){
            BluetoothBroadcastReceiver.unBindService();
            context.stopService(this.serviceBL);
        }
    }

    private void writeLogView(final IBeacon beacon){

        String msg = "";
        msg += "UIID: " + beacon.getUuid();
        msg += " | : " + beacon.getMajor();
        msg += "-: " + beacon.getMinor();
        msg += " | RssI: " + beacon.getRssi();
        msg += " | TX: " + beacon.getTransmittingPower();



        writeLogView(msg);
    }

    private void writeLogView(final String msg){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvLog.setText(tvLog.getText()+ "\n\n- "+ msg);
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });


    }
}

