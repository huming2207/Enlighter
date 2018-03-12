package helpers;

import javafx.collections.ObservableList;
import models.Device;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

public class EnlightListener implements ServiceListener
{
    private ObservableList<Device> devices;

    public EnlightListener(ObservableList<Device> devices)
    {
        this.devices = devices;
        System.out.println("EnlightListener: initialized.");
    }

    @Override
    public void serviceAdded(ServiceEvent event)
    {
        String deviceAddr = event.getInfo().getInet4Addresses()[0].toString();

        System.out.println(String.format("Service added, IP: %s, name: %s",
                deviceAddr,
                event.getName()));
    }

    @Override
    public void serviceRemoved(ServiceEvent event)
    {
        String deviceAddr = event.getInfo().getInet4Addresses()[0].toString();

        System.out.println(String.format("Service removed, IP: %s, name: %s",
                deviceAddr,
                event.getName()));

        Device device = findDeviceInList(deviceAddr, event.getName());

        if(device != null) {
            devices.remove(device);
        }

    }

    @Override
    public void serviceResolved(ServiceEvent event)
    {
        String deviceAddr = event.getInfo().getInet4Addresses()[0].toString();

        System.out.println(String.format("Service resolved, IP: %s, name: %s",
                deviceAddr,
                event.getName()));

        devices.add(new Device(deviceAddr, event.getName()));
    }

    private Device findDeviceInList(String addr, String name)
    {
        for(Device device : devices) {
            if(device.getDeviceName().equals(name) &&
                    device.getDeviceAddr().equals(addr)) {
                return device;
            }
        }

        return null;
    }
}
