package controllers;

import helpers.EnlightLedInfoFetcher;
import helpers.EnlightMDnsListener;
import helpers.EnlightSettingPusher;
import helpers.interfaces.EnlightInfoFetcher;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Device;
import models.LedInfo;

import javax.jmdns.JmDNS;
import java.io.IOException;
import java.net.InetAddress;

public class HomePageController
{
    private ObjectProperty<ObservableList<Device>> deviceList;
    private LedInfo ledInfo = new LedInfo();
    private Device selectedDevice = null;
    private EnlightInfoFetcher infoFetcher = new EnlightLedInfoFetcher(ledInfo);
    private EnlightSettingPusher settingPusher = null;

    public HomePageController()
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
    private ToggleButton powerToggleButton;

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

        // Fetch current brightness and color of the target device
        infoFetcher.fetchInfo(this.selectedDevice.getDeviceAddr());

        // Register IP address to setting pusher
        settingPusher = new EnlightSettingPusher(this.selectedDevice.getDeviceAddr());
    }

    @FXML
    private void handleAboutButton()
    {
        FXMLLoader aboutFxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/Advanced.fxml"));
        aboutFxmlLoader.setController(new AdvancedPageController(this.selectedDevice.getDeviceAddr()));
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(aboutFxmlLoader.load()));
            stage.setTitle("Advanced information");
            stage.setMaxHeight(400);
            stage.setMaxWidth(700);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Message: " + e.getMessage());
            alert.setTitle("Failed to load About page");
            e.printStackTrace();
            alert.show();
        }
    }

    @FXML
    private void handleBrightness()
    {
        settingPusher.commitSetting("value", Integer.toString((int)brightnessSlider.getValue()), "bright");
    }

    @FXML
    private void handleColorTemp()
    {
        settingPusher.commitSetting("value", Integer.toString((int)colorTempSlider.getValue()), "temp");
    }

    @FXML
    private void handleColorPicker()
    {
        Color value = freeColorPicker.getValue();
        settingPusher.commitSetting("value",
                String.format("#%02x%02x%02x",
                        (int)(value.getRed() * 255),
                        (int)(value.getGreen() * 255),
                        (int)(value.getBlue() * 255)),
                "color");

        // Only perform when user modified opacity value
        if(value.getOpacity() < 1) {
            settingPusher.commitSetting("value",
                    Integer.toString((int)value.getOpacity() * 255), "bright");
        }
    }

    @FXML
    private void handleSave()
    {
        settingPusher.commitSetting("cmd", "save", "save");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Status setting saved");
        alert.setTitle("Setting saved.");
        alert.showAndWait();
    }

    @FXML
    private void handlePower()
    {
        if(powerToggleButton.isSelected()) {
            settingPusher.commitSetting("switch", "on", "power");
        } else {
            settingPusher.commitSetting("switch", "off", "power");
        }
    }


}
