package controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.SysInfo;

public class AboutController
{
    private SysInfo sysInfo = new SysInfo();

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

    }
}
