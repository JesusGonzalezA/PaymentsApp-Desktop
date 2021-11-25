module com.paymentsappdesktop.paymentsappdesktop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.paymentsappdesktop.paymentsappdesktop to javafx.fxml;
    exports com.paymentsappdesktop.paymentsappdesktop;
    exports com.paymentsappdesktop.paymentsappdesktop.controllers;
    opens com.paymentsappdesktop.paymentsappdesktop.controllers to javafx.fxml;
    exports com.paymentsappdesktop.paymentsappdesktop.components;
    opens com.paymentsappdesktop.paymentsappdesktop.components to javafx.fxml;
}