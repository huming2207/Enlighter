package controllers;

import helpers.EnlightListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import models.Device;
import models.LedInfo;
import models.SysInfo;

import javax.jmdns.JmDNS;
import java.io.IOException;
import java.net.InetAddress;

public class HomeController
{
    private ObjectProperty<ObservableList<Device>> deviceList;
    private LedInfo ledInfo;
    private SysInfo sysInfo;
    private Device selectedDevice;

    @FXML
    private ComboBox<Device> deviceComboBox;

    public HomeController()
    {
        // Initialize device list and JmDNS instance.
        deviceList = new SimpleObjectProperty<>();
        deviceList.set(FXCollections.observableArrayList());

        try {
            System.out.println("Localhost: " + InetAddress.getLocalHost().toString());
            JmDNS jmDNS = JmDNS.create(InetAddress.getLocalHost());

            // Here we should set type to "_http._tcp.local." but not "_http.tcp."
            jmDNS.addServiceListener("_http._tcp.local.", new EnlightListener(deviceList.getValue()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SysInfo getSysInfo()
    {
        return this.sysInfo;
    }

    public LedInfo getLedInfo()
    {
        return this.ledInfo;
    }

    @FXML
    private void initialize()
    {
        deviceComboBox.itemsProperty().bind(deviceList);
    }

    @FXML
    private void handleDeviceSelection()
    {
        this.selectedDevice = deviceComboBox.getValue();
        System.out.println(String.format("Selected device: %s, IP: %s",
                selectedDevice.getDeviceName(), selectedDevice.getDeviceAddr()));
    }
}
