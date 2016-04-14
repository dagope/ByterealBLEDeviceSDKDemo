package com.bytereal.byterealblesdk.service;


public class IBeacon {
    private int major;
    private int minor;
    private String name;
    private String uuid;
    private String mac;
    private int transmittingPower;
    private int rssi;


    private Long lastUpdated;


    public int getMajor() {
        return this.major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return this.minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getTransmittingPower() {
        return this.transmittingPower;
    }

    public void setTransmittingPower(int transmittingPower) {
        this.transmittingPower = transmittingPower;
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public Long getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String toString() {
        return "IBeacon [major=" + this.major + ", minor=" + this.minor + ", name=" + this.name + ", uuid=" + this.uuid + ", mac=" + this.mac + ", transmittingPower=" + this.transmittingPower + ", rssi=" + this.rssi + ", lastUpdated=" + this.lastUpdated + "]";
    }
}


