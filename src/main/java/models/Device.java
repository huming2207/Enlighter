package models;

import javafx.beans.property.StringProperty;

public class Device
{
    public String getDeviceAddr()
    {
        return deviceAddr;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public Device(String deviceAddr, String deviceName)
    {
        this.deviceAddr = deviceAddr;
        this.deviceName = deviceName;
        this.deviceAddr = deviceAddr.replace("/", ""); // Always remove the "/" generated by mDNS lib
    }

    @Override
    public String toString()
    {
        return String.format("%s - %s", this.deviceName, this.deviceAddr);
    }

    @Override
    public boolean equals(Object object)
    {
        if(object == null) return false;

        Device comparingDevice = (Device)object;

        return comparingDevice.deviceName.equals(this.deviceName)
                && comparingDevice.deviceAddr.equals(this.deviceAddr);
    }

    private String deviceAddr;
    private String deviceName;
}
