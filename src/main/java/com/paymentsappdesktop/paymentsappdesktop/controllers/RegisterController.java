package com.paymentsappdesktop.paymentsappdesktop.controllers;

import com.paymentsappdesktop.paymentsappdesktop.PaymentsApp;
import com.paymentsappdesktop.paymentsappdesktop.components.DialogComponentHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private Label link;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public void onRegister(ActionEvent actionEvent) throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();

        if ( username.isEmpty() || password.isEmpty() ){
            Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Username/Password should not be empty");
            dialog.showAndWait();
        } else {
            
            // Todo register
            goToLogin();
        }
    }

    private void goToLogin () throws IOException {
        Stage stage = (Stage) link.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(PaymentsApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Payments app");
        stage.setScene(scene);
        stage.show();
    }

    public void onLinkClicked() throws IOException {
        goToLogin();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
