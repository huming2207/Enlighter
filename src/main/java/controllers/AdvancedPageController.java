package controllers;

import helpers.EnlightSettingPusher;
import helpers.EnlightSysInfoFetcher;
import helpers.interfaces.EnlightInfoFetcher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.SysInfo;

import javax.xml.soap.Text;
import java.util.HashMap;

public class AdvancedPageController
{
    private SysInfo sysInfo = new SysInfo();
    private EnlightInfoFetcher infoFetcher = new EnlightSysInfoFetcher(this.sysInfo);
    private EnlightSettingPusher settingPusher;

    public AdvancedPageController(String deviceIp)
    {
        this.settingPusher = new EnlightSettingPusher(deviceIp);
        infoFetcher.fetchInfo(deviceIp);
    }

    @FXML
    private Label initedDevice;

    @FXML
    private Label sdkVersion;

    @FXML
    private Label firmwareVersion;

    @FXML
    private Label freeRam;

    @FXML
    private Label serialNumber;

    @FXML
    private Label currentWifiSsid;

    @FXML
    private Label currentIpAddr;

    @FXML
    private Label wifiSigStrength;

    @FXML
    private TextField firmwarePathTextBox;

    @FXML
    private TextField wifiSsidTextBox;

    @FXML
    private TextField wifiPasswdTextBox;


    @FXML
    private void initialize()
    {
        // Binding starts here...
        initedDevice.textProperty().bind(this.sysInfo.sysInitedProperty().asString());
        sdkVersion.textProperty().bind(this.sysInfo.sysSdkVerProperty());
        firmwareVersion.textProperty().bind(this.sysInfo.sysVerProperty());
        freeRam.textProperty().bind(this.sysInfo.sysFreeRamProperty().asString());
        serialNumber.textProperty().bind(this.sysInfo.sysIdProperty());
        currentWifiSsid.textProperty().bind(this.sysInfo.netSsidProperty());
        currentIpAddr.textProperty().bind(this.sysInfo.netIpProperty());
        wifiSigStrength.textProperty().bind(this.sysInfo.netSigProperty().asString());
    }

    @FXML
    private void handleWifiSave()
    {
        HashMap<String, String> map = new HashMap<>();

        if(wifiSsidTextBox.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "WiFi name cannot be empty!");
            alert.showAndWait();
            return; // Stops here as this is an error
        } else {
            map.put("wifi_ssid", wifiSsidTextBox.getText());
        }

        if(!wifiPasswdTextBox.getText().isEmpty()) {
            map.put("wifi_passwd", wifiPasswdTextBox.getText());
        }

        settingPusher.commitSetting(map, "setting");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "WiFi settings has been updated, please wait until your device reboot.");
        alert.showAndWait();
    }

    @FXML
    private void handleFirmwareSelection()
    {

    }

    @FXML
    private void handleFirmwareUpgrade()
    {

    }
}
