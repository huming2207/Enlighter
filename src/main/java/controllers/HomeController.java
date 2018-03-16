package controllers;

import helpers.EnlightLedInfoFetcher;
import helpers.EnlightMDnsListener;
import helpers.EnlightSysInfoFetcher;
import helpers.interfaces.EnlightInfoFetcher;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
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
    private Device selectedDevice = null;
    private EnlightInfoFetcher infoFetcher = new EnlightLedInfoFetcher(ledInfo);

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

    public static void showError(String error)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, error);
        alert.show();
    }

    @FXML
    private ComboBox<Device> deviceComboBox;

    @FXML
    private Slider brightnessSlider;

    @FXML
    private Slider colorTempSlider;

    @FXML
    private ColorPicker freeColorPicker;

    @FXML
    private Button aboutButton;

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

    @FXML
    private void handleAboutButton()
    {
        FXMLLoader aboutFxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/About.fxml"));
        aboutFxmlLoader.setController(new AboutController(this.selectedDevice.getDeviceAddr()));
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(aboutFxmlLoader.load()));
            stage.setTitle("About");
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Message: " + e.getMessage());
            alert.setTitle("Failed to load About page");
            e.printStackTrace();
            alert.show();
        }
    }
}
