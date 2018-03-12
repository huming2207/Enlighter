package models;

import javafx.beans.property.StringProperty;

public class Device
{
    public String getDeviceAddr()
    {
        return deviceAddr;
    }

    public void setDeviceAddr(String deviceAddr)
    {
        this.deviceAddr = deviceAddr;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public void setDeviceName(String deviceName)
    {
        this.deviceName = deviceName;
    }

    public Device(String deviceAddr, String deviceName)
    {
        this.deviceAddr = deviceAddr;
        this.deviceName = deviceName;
    }

    @Override
    public String toString()
    {
        return String.format("%s - %s", this.deviceName, this.deviceAddr);
    }

    @Override
    public boolean equals(Object object)
    {
        Device comparingDevice = (Device)object;

        return comparingDevice.deviceName.equals(this.deviceName)
                && comparingDevice.deviceAddr.equals(this.deviceAddr);
    }

    private String deviceAddr;
    private String deviceName;
}
