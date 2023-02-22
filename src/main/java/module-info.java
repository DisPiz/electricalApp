module org.lukashchuk.appll {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;
    requires lombok;


    opens org.lukashchuk.appll to javafx.fxml;
    exports org.lukashchuk.appll.controller;
    opens org.lukashchuk.appll.controller to javafx.fxml;
    opens org.lukashchuk.appll.models.electricalAppliances;
    exports org.lukashchuk.appll;
}