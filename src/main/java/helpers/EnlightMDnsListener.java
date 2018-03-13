package helpers;

import javafx.collections.ObservableList;
import models.Device;
import models.SysInfo;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

public class EnlightMDnsListener implements ServiceListener
{
    private ObservableList<Device> devices;

    public EnlightMDnsListener(ObservableList<Device> devices)
    {
        this.devices = devices;
        System.out.println("EnlightMDnsListener: initialized.");
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

        if(!event.getName().startsWith("enlight-")) {
            System.out.println(String.format("mDNS device named \"%s\" is not an Enlight lamp.", event.getName()));
        } else {
            devices.add(new Device(deviceAddr, event.getName()));
        }
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
