package com.bytereal.byterealblesdk.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.content.Context;

import java.util.HashSet;
import java.util.Set;


public class IBeaconScanTask {
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private LeScanCallback scanCallback = new IBeaconScanCallBack();
    private static HashSet<IBeacon> IBeaconList = new HashSet();

    public void startIBeaconScan(Context context) {

        this.bluetoothAdapter.startLeScan(this.scanCallback);
    }

    public void stopIBeaconScan(Context context) {
        this.bluetoothAdapter.stopLeScan(this.scanCallback);
        updataCacheList();
        if(IBeaconScanConfig.newIBeaconCallback != null)
            IBeaconScanConfig.newIBeaconCallback.endOfTheScan();
    }


    public static IBeacon updataIBeacon(IBeacon oldBean, IBeacon newBean) {
        oldBean.setLastUpdated(newBean.getLastUpdated());
        oldBean.setMac(newBean.getMac());
        oldBean.setMajor(newBean.getMajor());
        oldBean.setMinor(newBean.getMinor());
        oldBean.setName(newBean.getName());
        oldBean.setRssi(newBean.getRssi());
        oldBean.setTransmittingPower(newBean.getTransmittingPower());
        oldBean.setUuid(newBean.getUuid());
        return oldBean;
    }


    private void updataCacheList() {
        boolean isExpire = false;
        Set<IBeacon> delSet = new HashSet();
        for (IBeacon cacheIBeacon : IBeaconScanConfig.IBeaconListCache) {
            if (isExpireCache(cacheIBeacon)) {
                isExpire = true;
                delSet.add(cacheIBeacon);
            }
        }
        if (isExpire) {
            IBeaconScanConfig.IBeaconListCache.removeAll(delSet);
        }
    }


    private boolean isExpireCache(IBeacon cacheIBeacon) {
        if (cacheIBeacon == null) {
            return true;
        }

        long nowTime = System.currentTimeMillis();
        long lastUpdated = cacheIBeacon.getLastUpdated().longValue();
        long cacheTime = nowTime - lastUpdated;
        if (cacheTime >= IBeaconScanConfig.scanCacheDurationTime) {
            return true;
        }

        return false;
    }


    class IBeaconScanCallBack
            implements LeScanCallback {
        IBeaconScanCallBack() {
        }


        public void onLeScan(BluetoothDevice d, int r, byte[] s) {
            IBeacon realTimeIBeacon = IBeaconDecodeTask.fromScanData(d, r, s);
            if (realTimeIBeacon == null) {
                return;
            }
            IBeaconScanTask.IBeaconList.add(realTimeIBeacon);
            if (IBeaconScanConfig.IBeaconListCache.size() == 0) {
                IBeaconScanConfig.IBeaconListCache.add(realTimeIBeacon);
            }
            boolean isInCache = false;
            for (IBeacon cacheIBeacon : IBeaconScanConfig.IBeaconListCache) {
                if (cacheIBeacon.getMac().equals(realTimeIBeacon.getMac())) {
                    IBeaconScanTask.updataIBeacon(cacheIBeacon, realTimeIBeacon);
                    isInCache = true;
                }
            }
            if (!isInCache) {
                IBeaconScanConfig.IBeaconListCache.add(realTimeIBeacon);
                IBeaconScanConfig.newIBeaconCallback.findNewIBeacon(realTimeIBeacon);
            }
            realTimeIBeacon = null;
        }
    }

    public static abstract interface NewIBeaconCallback {
        public abstract void findNewIBeacon(IBeacon paramIBeacon);

        public abstract void endOfTheScan();
    }
}

