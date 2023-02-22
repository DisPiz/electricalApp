package org.lukashchuk.automation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.lukashchuk.appll.HelloApplication;
import org.lukashchuk.appll.models.electricalAppliances.Appliance;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TableViewMatchers.*;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

@TestMethodOrder(OrderAnnotation.class)
public class ApplianceTest extends ApplicationTest {

    private TableView<Appliance> tableView;
    private TableColumn<Appliance, Boolean> myColumn;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = HelloApplication.class.getResource("css/main.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Менеджер електроприладів");
        stage.setScene(scene);
        stage.show();
        tableView = lookup("#tableAppliance").queryTableView();
        myColumn = (TableColumn<Appliance, Boolean>) tableView.getColumns().get(5);
    }
    @Test
    @Order(1)
    public void testCreateAppliance() {
        clickOn("#createMainBtn");
        verifyThat("#addAppliance", isVisible());
        clickOn("#okBtn");
        clickOn("#nameText").write("NameTest");
        clickOn("#manuText").write("ManufacturerTest");
        clickOn("#powerText").write("1");
        clickOn("#apText").write("999");
        clickOn("#createBtn");
        TableView<Appliance> tableView = lookup("#tableAppliance").query();
        interact(() ->{
            int lastIndex = tableView.getItems().size() - 1;
            tableView.scrollTo(lastIndex);
        });
        verifyThat("#tableAppliance", hasTableCell("NameTest"));
    }

    @Test
    @Order(2)
    public void testUpdateAppliance() {
        TableView<Appliance> tableView = lookup("#tableAppliance").queryTableView();
        int lastIndex = tableView.getItems().size() - 1;
        interact(() ->{
            tableView.scrollTo(lastIndex);
        });
        Node lastCell = lookup(".table-row-cell").nth(lastIndex).query();
        clickOn(lastCell);
        clickOn("#updateBtn");
        verifyThat("#updAppliance", isVisible());
        clickOn("#nameText").write("Upd");
        clickOn("#updBtn");
        verifyThat("#tableAppliance", hasTableCell("NameTestUpd"));
    }

    @Test
    @Order(3)
    public void testPlugAppliance() {
        TableView<Appliance> tableView = lookup("#tableAppliance").queryTableView();
        int lastIndex = tableView.getItems().size() - 1;
        interact(() ->{
            tableView.scrollTo(lastIndex);
        });
        Node lastCell = lookup(".table-row-cell").nth(lastIndex).query();
        clickOn(lastCell);
        clickOn("#plugBtn");
        Boolean lastValue = (Boolean) myColumn.getCellObservableValue(lastIndex).getValue();
        verifyThat("#tableAppliance", hasTableCell("NameTestUpd"));
        assertTrue(lastValue != null && lastValue);
    }

    @Test
    @Order(4)
    public void testDeleteAppliance() {
        TableView<Appliance> tableView = lookup("#tableAppliance").queryTableView();
        int lastIndex = tableView.getItems().size() - 1;
        interact(() ->{
            tableView.scrollTo(lastIndex);
        });
        Node lastCell = lookup(".table-row-cell").nth(lastIndex).query();
        clickOn(lastCell);
        Appliance lastAppliance = tableView.getItems().get(lastIndex);
        clickOn("#deleteBtn");
        List<Appliance> appliances = tableView.getItems();
        assertFalse(appliances.contains(lastAppliance));
    }
}
