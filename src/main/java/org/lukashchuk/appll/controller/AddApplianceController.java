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

public class AddApplianceController implements Initializable {
    private final ApplianceService applianceService = new ApplianceService();
    @FXML
    private Button createBtn;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> types = FXCollections.observableArrayList("default", "litreage", "battery");
        typeCombo.setItems(types);
        typeCombo.getSelectionModel().select(0);
    }
    @FXML
    private void addApplianceBtn(){
        if (typeCombo.getSelectionModel().getSelectedItem().equals("litreage")) {
            applianceService.add(new LitreageAppliance(nameText.getText(), manuText.getText(), Integer.parseInt(powerText.getText()), Integer.parseInt(apText.getText()), false, Integer.parseInt(optText.getText())));
        } else if(typeCombo.getSelectionModel().getSelectedItem().equals("battery")) {
            applianceService.add(new BatteryAppliance(nameText.getText(), manuText.getText(), Integer.parseInt(powerText.getText()), Integer.parseInt(apText.getText()), false, false));
        } else {
            applianceService.add(new DefaultAppliance(nameText.getText(), manuText.getText(), Integer.parseInt(powerText.getText()), Integer.parseInt(apText.getText()), false));
        }
        Stage stage = (Stage) createBtn.getScene().getWindow();
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
    @FXML
    private void okBtnClick(){
        okBtn.mouseTransparentProperty().set(true);
        typeCombo.mouseTransparentProperty().set(true);
        if (typeCombo.getSelectionModel().getSelectedItem().equals("litreage")) {
            optionalLabel.setText("Літраж");
            optionalLabel.visibleProperty().set(true);
            optText.visibleProperty().set(true);
        }
    }
}
