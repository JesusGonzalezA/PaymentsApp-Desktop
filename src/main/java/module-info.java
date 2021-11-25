module com.paymentsappdesktop.paymentsappdesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;

    opens com.paymentsappdesktop.paymentsappdesktop.models to com.fasterxml.jackson.databind;
    exports com.paymentsappdesktop.paymentsappdesktop.models;
    opens com.paymentsappdesktop.paymentsappdesktop to javafx.fxml;
    exports com.paymentsappdesktop.paymentsappdesktop;
    exports com.paymentsappdesktop.paymentsappdesktop.controllers;
    opens com.paymentsappdesktop.paymentsappdesktop.controllers to javafx.fxml;
    exports com.paymentsappdesktop.paymentsappdesktop.components;
    opens com.paymentsappdesktop.paymentsappdesktop.components to javafx.fxml;
}