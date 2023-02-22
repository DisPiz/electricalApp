package org.lukashchuk.appll.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.lukashchuk.appll.models.electricalAppliances.BatteryAppliance;
import org.lukashchuk.appll.models.electricalAppliances.DefaultAppliance;
import org.lukashchuk.appll.models.electricalAppliances.LitreageAppliance;
import org.lukashchuk.appll.services.ApplianceService;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateApplianceController implements Initializable {
    private final ApplianceService applianceService = new ApplianceService();
    @FXML
    private Button updBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private TextField nameText;
    @FXML
    private TextField manuText;
    @FXML
    private TextField powerText;
    @FXML
    private TextField apText;
    @FXML
    private TextField optText;
    @FXML
    private Button okBtn;
    @FXML
    private ComboBox typeCombo;
    @FXML
    private Label optionalLabel;
    private Long appId;
    private String appType;
    private Boolean appPlug;

    private Boolean appBattery;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> types = FXCollections.observableArrayList("default", "litreage", "battery");
        typeCombo.setItems(types);
    }
    @FXML
    private void addApplianceBtn(){
        if (typeCombo.getSelectionModel().getSelectedItem().equals("litreage")) {
            applianceService.updateAllFields(appId, new LitreageAppliance(nameText.getText(), manuText.getText(), Integer.parseInt(powerText.getText()), Integer.parseInt(apText.getText()), false, Integer.parseInt(optText.getText())));
        } else if(typeCombo.getSelectionModel().getSelectedItem().equals("battery")) {
            applianceService.updateAllFields(appId, new BatteryAppliance(nameText.getText(), manuText.getText(), Integer.parseInt(powerText.getText()), Integer.parseInt(apText.getText()), false, false));
        } else {
            applianceService.updateAllFields(appId, new DefaultAppliance(nameText.getText(), manuText.getText(), Integer.parseInt(powerText.getText()), Integer.parseInt(apText.getText()), false));
        }
        Stage stage = (Stage) updBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clearAllFields(){
        nameText.setText(null);
        manuText.setText(null);
        powerText.setText(null);
        apText.setText(null);
        optText.setText(null);
    }

    void setApplianceUpd(Long id, String name, String manufacturer, Integer power, Integer apartment, Boolean plug, String type, Integer litreage, Boolean battery) {
        appId = id;
        if(type.equals("litreage")){
            typeCombo.getSelectionModel().select(1);
        } else if (type.equals("battery")) {
            typeCombo.getSelectionModel().select(2);
        } else {
            typeCombo.getSelectionModel().select(0);
        }
        nameText.setText(name);
        manuText.setText(manufacturer);
        powerText.setText(power.toString());
        apText.setText(apartment.toString());
        if(type.equals("litreage")){
            optionalLabel.visibleProperty().set(true);
            optText.visibleProperty().set(true);
            optText.setText(litreage.toString());
        }
        if(type.equals("battery")){
            appBattery = battery;
        }
        appType = type;
        appPlug = plug;
    }
}
