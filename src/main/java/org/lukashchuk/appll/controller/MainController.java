package org.lukashchuk.appll.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.lukashchuk.appll.models.electricalAppliances.Appliance;
import org.lukashchuk.appll.models.electricalAppliances.BatteryAppliance;
import org.lukashchuk.appll.models.electricalAppliances.DefaultAppliance;
import org.lukashchuk.appll.models.electricalAppliances.LitreageAppliance;
import org.lukashchuk.appll.services.ApplianceService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {

    private final ApplianceService applianceService = new ApplianceService();
    @FXML
    private TableView<Appliance> tableAppliance;
    @FXML
    private TableColumn<Appliance, Long> idColumn;
    @FXML
    private TableColumn<Appliance, String> nameColumn;
    @FXML
    private TableColumn<Appliance, String> manufacturerColumn;
    @FXML
    private TableColumn<Appliance, Integer> powerColumn;
    @FXML
    private TableColumn<Appliance, Integer> apartmentColumn;
    @FXML
    private TableColumn<Appliance, Boolean> plugColumn;
    @FXML
    private TableColumn<Appliance, String> typeColumn;
    @FXML
    private TableColumn<Appliance, Integer> optionalColumn;
    @FXML
    private TableColumn<Appliance, Boolean> optionalColumnBattery;
    @FXML
    private Button showBtn;
    @FXML
    private Label sumLabel;
    @FXML
    private TextField apText;
    @FXML
    private TextField minText;
    @FXML
    private TextField maxText;
    Appliance appliance = null;
    ObservableList<Appliance> observableList;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDate();
    }

    @FXML
    private void getAddView(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/org/lukashchuk/appll/AddApplianceView.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainController.class.getResource("/org/lukashchuk/appll/css/main.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Додати прилад");
        stage.showAndWait();
        refreshTable();
    }

    @FXML
    private void refreshTable(){
        observableList = FXCollections.observableArrayList(applianceService.getAll());
        tableAppliance.setItems(observableList);
        sumLabel.setText(applianceService.sumPower().toString());
    }

    private void loadDate(){
        refreshTable();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        powerColumn.setCellValueFactory(new PropertyValueFactory<>("powerConsumption"));
        apartmentColumn.setCellValueFactory(new PropertyValueFactory<>("apartment"));
        plugColumn.setCellValueFactory(new PropertyValueFactory<>("plugStatus"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        optionalColumn.setCellValueFactory(data -> {
            Appliance applianceLitreage = data.getValue();
            if(applianceLitreage instanceof LitreageAppliance){
                return new ReadOnlyObjectWrapper<>(((LitreageAppliance) applianceLitreage).getLitreage());
            } else {
                return null;
            }
        });

        optionalColumnBattery.setCellValueFactory(data -> {
            Appliance applianceBattery = data.getValue();
            if(applianceBattery instanceof BatteryAppliance){
                return new ReadOnlyObjectWrapper<>(((BatteryAppliance) applianceBattery).getBatteryOn());
            } else {
                return null;
            }
        });
        final ObservableList<Appliance> data = FXCollections.observableArrayList(applianceService.getAll());
        tableAppliance.setItems(data);
        tableAppliance.refresh();
    }
    @FXML
    private void onClickShow(){
        if(!apText.getText().equals("") && minText.getText().equals("") && maxText.getText().equals("")){
            refreshTableByApt(Integer.parseInt(apText.getText()));
        } else if (!apText.getText().equals("") && !minText.getText().equals("") && !maxText.getText().equals("")) {
            refreshTableInRange(Integer.parseInt(apText.getText()), Integer.parseInt(minText.getText()), Integer.parseInt(maxText.getText()));
        } else {
            refreshTable();
        }

    }

    private void refreshTableByApt(Integer apartment){
        observableList = FXCollections.observableArrayList(applianceService.sortByPower(apartment));
        tableAppliance.setItems(observableList);
    }

    private void refreshTableInRange(Integer apartment, Integer minVal, Integer maxVal){
        observableList = FXCollections.observableArrayList(applianceService.findInRange(apartment, minVal, maxVal));
        tableAppliance.setItems(observableList);
    }

    @FXML
    private void plugBtnClick(){
        Long plug = tableAppliance.getSelectionModel().getSelectedItem().getId();
        applianceService.update(plug);
        refreshTable();
    }
    @FXML
    private void updateBtnClick(){
        Appliance applianceUpdate = tableAppliance.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/org/lukashchuk/appll/UpdateApplianceView.fxml"));
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        UpdateApplianceController updateAppliance = fxmlLoader.getController();
        if(applianceUpdate instanceof LitreageAppliance){
            updateAppliance.setApplianceUpd(applianceUpdate.getId(), applianceUpdate.getName(), applianceUpdate.getManufacturer(),
                    applianceUpdate.getPowerConsumption(), applianceUpdate.getApartment(), applianceUpdate.getPlugStatus(), applianceUpdate.getType(), ((LitreageAppliance) applianceUpdate).getLitreage(), false);
        } else if (applianceUpdate instanceof BatteryAppliance) {
            updateAppliance.setApplianceUpd(applianceUpdate.getId(), applianceUpdate.getName(), applianceUpdate.getManufacturer(),
                    applianceUpdate.getPowerConsumption(), applianceUpdate.getApartment(), applianceUpdate.getPlugStatus(), applianceUpdate.getType(), 0, ((BatteryAppliance) applianceUpdate).getBatteryOn());
        } else if (applianceUpdate instanceof DefaultAppliance){
            updateAppliance.setApplianceUpd(applianceUpdate.getId(), applianceUpdate.getName(), applianceUpdate.getManufacturer(),
                    applianceUpdate.getPowerConsumption(), applianceUpdate.getApartment(), applianceUpdate.getPlugStatus(), applianceUpdate.getType(), 0, false);
        }
        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(MainController.class.getResource("/org/lukashchuk/appll/css/main.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Редагувати прилад");
        stage.setOnCloseRequest(e -> refreshTable());
        stage.showAndWait();
        tableAppliance.getItems().clear();
        loadDate();
    }

    @FXML
    private void deleteBtnClick(){
        Long plug = tableAppliance.getSelectionModel().getSelectedItem().getId();
        applianceService.delete(plug);
        refreshTable();
    }
}