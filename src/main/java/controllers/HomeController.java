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

import javax.jmdns.JmDNS;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HomeController
{
    private ObjectProperty<ObservableList<Device>> deviceList;

    @FXML
    private ComboBox<Device> deviceComboBox;

    public HomeController()
    {
        // Initialize device list and JmDNS instance.
        deviceList = new SimpleObjectProperty<>();
        deviceList.set(FXCollections.observableArrayList());

        try {
            JmDNS jmDNS = JmDNS.create(InetAddress.getLocalHost());
            jmDNS.addServiceListener("_http._tcp", new EnlightListener(deviceList.get()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize()
    {
        deviceComboBox.itemsProperty().bindBidirectional(deviceList);
    }
}
