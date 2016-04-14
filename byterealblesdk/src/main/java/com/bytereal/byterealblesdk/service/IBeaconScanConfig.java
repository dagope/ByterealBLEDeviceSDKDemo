package com.bytereal.byterealblesdk.service;

import java.util.HashSet;


public class IBeaconScanConfig {
    /* 19 */   public static int scanDurationTime = 3000;


    /* 24 */   public static int scanSpacingTime = 2000;


    /* 30 */   public static int scanCacheDurationTime = 15000;


    public static HashSet<IBeacon> IBeaconListCache = new HashSet();
    public static IBeaconScanTask.NewIBeaconCallback newIBeaconCallback;
}
