package com.bytereal.byterealblesdk.service;

import android.bluetooth.BluetoothDevice;

public class IBeaconDecodeTask {
    public static IBeacon fromScanData(BluetoothDevice device, int rssi, byte[] scanData) {
        int startByte = 2;
        boolean flag = false;
        while (startByte <= 5) {
            if (((scanData[(startByte + 2)] & 0xFF) == 2) && ((scanData[(startByte + 3)] & 0xFF) == 21)) {
                flag = true;
                break;
            }
            if (((scanData[startByte] & 0xFF) == 45) &&
                    ((scanData[(startByte + 1)] & 0xFF) == 36) &&
                    ((scanData[(startByte + 2)] & 0xFF) == 191) &&
                    ((scanData[(startByte + 3)] & 0xFF) == 22)) {
                IBeacon iBeacon = new IBeacon();
                iBeacon.setMajor(0);
                iBeacon.setMinor(0);
                iBeacon.setUuid("00000000-0000-0000-0000-000000000000");
                iBeacon.setTransmittingPower(-55);
                iBeacon.setLastUpdated(Long.valueOf(System.currentTimeMillis()));
                return iBeacon;
            }
            if (((scanData[startByte] & 0xFF) == 173) &&
                    ((scanData[(startByte + 1)] & 0xFF) == 119) &&
                    ((scanData[(startByte + 2)] & 0xFF) == 0) &&
                    ((scanData[(startByte + 3)] & 0xFF) == 198)) {
                IBeacon iBeacon = new IBeacon();
                iBeacon.setMajor(0);
                iBeacon.setMinor(0);
                iBeacon.setUuid("00000000-0000-0000-0000-000000000000");
                iBeacon.setTransmittingPower(-55);
                iBeacon.setLastUpdated(Long.valueOf(System.currentTimeMillis()));
                return iBeacon;
            }
            startByte++;
        }
        if (!flag) {
            return null;
        }
        IBeacon iBeacon = new IBeacon();
        iBeacon.setMajor((scanData[(startByte + 20)] & 0xFF) * 256 + (scanData[(startByte + 21)] & 0xFF));

        iBeacon.setMinor((scanData[(startByte + 22)] & 0xFF) * 256 + (scanData[(startByte + 23)] & 0xFF));
        iBeacon.setTransmittingPower(scanData[(startByte + 24)]);
        iBeacon.setRssi(rssi);
        byte[] proximityUuidBytes = new byte[16];
        System.arraycopy(scanData, startByte + 4, proximityUuidBytes, 0, 16);
        String hexString = bytesToHexString(proximityUuidBytes);
        iBeacon.setUuid(AssemblyUUID(hexString));
        iBeacon.setLastUpdated(Long.valueOf(System.currentTimeMillis()));
        if (device != null) {
            iBeacon.setMac(device.getAddress());
            iBeacon.setName(device.getName());
        }
        return iBeacon;
    }


    private static String AssemblyUUID(String hexString) {
        StringBuilder sb = new StringBuilder();
        sb.append(hexString.substring(0, 8));
        sb.append("-");
        sb.append(hexString.substring(8, 12));
        sb.append("-");
        sb.append(hexString.substring(12, 16));
        sb.append("-");
        sb.append(hexString.substring(16, 20));
        sb.append("-");
        sb.append(hexString.substring(20, 32));
        return sb.toString();
    }


    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if ((src == null) || (src.length <= 0)) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);

            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}

