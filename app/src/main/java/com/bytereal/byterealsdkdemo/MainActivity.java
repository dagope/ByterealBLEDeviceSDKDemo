package com.bytereal.byterealsdkdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bytereal.byterealblesdk.broadcast.receiver.BluetoothBroadcastReceiver;
import com.bytereal.byterealblesdk.broadcast.register.RegisteredBroadcast;
import com.bytereal.byterealblesdk.service.IBeacon;
import com.bytereal.byterealblesdk.service.IBeaconScanConfig;
import com.bytereal.byterealblesdk.service.IBeaconScanTask;


public class MainActivity extends AppCompatActivity implements IBeaconScanTask.NewIBeaconCallback {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        // 设置回调接口
        IBeaconScanConfig.newIBeaconCallback = this;
// 设置信号丢失时长,超过该时长没有再次发现设备,判定为信号丢失
// 默认为15秒
// 建议(扫描+间隔)*3.即,连续三次没有发现设备信号才认为丢失
        IBeaconScanConfig.scanCacheDurationTime = 15000;
// 扫描时长,默认为3秒.请根据实际需求和电量消耗考虑
        IBeaconScanConfig.scanDurationTime = 3000;
// 扫描间隔,默认为2秒.请根据实际需求和电量消耗考虑
        IBeaconScanConfig.scanSpacingTime = 2000;
// 请在这里注册蓝牙状态监听广播,请注意顺序,一定先开启广播后开启蓝牙
// 请在这里开启蓝牙,然后SDK会开启BLE扫描服务
        RegisteredBroadcast.registeredBluetoothBroadcast(this);
    }

    @Override
    public void findNewIBeacon(IBeacon paramIBeacon) {
        Log.i(TAG, paramIBeacon.toString());
    }

    @Override
    public void endOfTheScan() {
        Log.w(TAG, "scan stopped!!take time to do what u want to");
        Log.w(TAG, "devices are " + IBeaconScanConfig.IBeaconListCache.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        RegisteredBroadcast.unRegisterBluetoothBroadcast(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothBroadcastReceiver.unBindService();
    }
}
