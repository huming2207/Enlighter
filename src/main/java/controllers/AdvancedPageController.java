package controllers;

import helpers.EnlightFirmwareUpdater;
import helpers.EnlightSettingPusher;
import helpers.EnlightSysInfoFetcher;
import helpers.interfaces.EnlightInfoFetcher;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.SysInfo;

import javax.xml.soap.Text;
import java.io.File;
import java.util.HashMap;

public class AdvancedPageController
{
    private SysInfo sysInfo = new SysInfo();
    private EnlightInfoFetcher infoFetcher = new EnlightSysInfoFetcher(this.sysInfo);
    private EnlightSettingPusher settingPusher;
    private String deviceIp = null;

    public AdvancedPageController(String deviceIp)
    {
        this.deviceIp = deviceIp;
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick an image...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Enlight firmware binary (*.bin/*.elf)", "*.bin", "*.elf"));
        File file = fileChooser.showOpenDialog(new Stage());

        // When user press cancel, the file will be null
        if(file != null) firmwarePathTextBox.setText(file.getAbsolutePath());
    }

    @FXML
    private void handleFirmwareUpgrade()
    {
        EnlightFirmwareUpdater.updateFirmware(this.deviceIp, firmwarePathTextBox.getText(), this);
        Alert confirmedAlert = new Alert(Alert.AlertType.INFORMATION,
                "Firmware upgrade now started, this app will be automatically closed if successful. Please open it again later.");

        confirmedAlert.showAndWait();
    }
}
