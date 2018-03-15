package controllers;

import helpers.EnlightLedInfoFetcher;
import helpers.EnlightMDnsListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import models.Device;
import models.LedInfo;
import models.SysInfo;

import javax.jmdns.JmDNS;
import java.io.IOException;
import java.net.InetAddress;

public class HomeController
{
    private ObjectProperty<ObservableList<Device>> deviceList;
    private LedInfo ledInfo = new LedInfo();
    private SysInfo sysInfo = new SysInfo();
    private Device selectedDevice = null;
    private EnlightLedInfoFetcher infoFetcher = new EnlightLedInfoFetcher(ledInfo);

    @FXML
    private ComboBox<Device> deviceComboBox;

    @FXML
    private Slider brightnessSlider;

    @FXML
    private Slider colorTempSlider;

    @FXML
    private ColorPicker freeColorPicker;

    public HomeController()
    {
        // Initialize device list and JmDNS instance.
        deviceList = new SimpleObjectProperty<>();
        deviceList.set(FXCollections.observableArrayList());

        try {
            System.out.println("Localhost: " + InetAddress.getLocalHost().toString());
            JmDNS jmDNS = JmDNS.create(InetAddress.getLocalHost());

            // Here we should set type to "_http._tcp.local." but not "_http.tcp."
            jmDNS.addServiceListener("_http._tcp.local.", new EnlightMDnsListener(deviceList.getValue()));

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
        // Bind device list to device combo box
        deviceComboBox.itemsProperty().bind(deviceList);

        // Bind color to color picker
        freeColorPicker.valueProperty().bindBidirectional(ledInfo.colorProperty());

        // Bind brightness to brightness slider
        brightnessSlider.valueProperty().bindBidirectional(ledInfo.brightnessProperty());
    }

    @FXML
    private void handleDeviceSelection()
    {
        this.selectedDevice = deviceComboBox.getValue();
        System.out.println(String.format("Selected device: %s, IP: %s",
                selectedDevice.getDeviceName(), selectedDevice.getDeviceAddr()));

        infoFetcher.fetchInfo(this.selectedDevice.getDeviceAddr());
    }

    public static void showError(String error)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, error);
        alert.show();
    }
}
