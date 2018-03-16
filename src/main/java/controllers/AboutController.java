package controllers;

import helpers.EnlightSysInfoFetcher;
import helpers.interfaces.EnlightInfoFetcher;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.SysInfo;

public class AboutController
{
    private SysInfo sysInfo = new SysInfo();
    private EnlightInfoFetcher infoFetcher = new EnlightSysInfoFetcher(this.sysInfo);

    public AboutController(String deviceIp)
    {
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
}
