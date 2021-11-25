package com.paymentsappdesktop.paymentsappdesktop.controllers;

import com.paymentsappdesktop.paymentsappdesktop.Credentials;
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
import okhttp3.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label link;
    @FXML
    private TextField username;
    @FXML
    private TextField password;

    public void onLogin(ActionEvent actionEvent) throws IOException {
        String username = this.username.getText();
        String password = this.password.getText();

        if ( username.isEmpty() || password.isEmpty() ){
            Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Username/Password should not be empty");
            dialog.showAndWait();
        } else {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("http://localhost:8080/user/login?username=" + username + "&password=" + password)
                    .method("PATCH", body)
                    .build();
            Response response = client.newCall(request).execute();

            if ( response.code() == 200 ) {
                Credentials.password = password;
                Credentials.username = username;
                goTo("main.fxml");
            } else {
                System.out.println(response);
                Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Bad credentials");
                dialog.showAndWait();
            }

        }
    }

    private void goTo(String resource) throws IOException {
        Stage stage = (Stage) link.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(PaymentsApp.class.getResource(resource));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Payments app");
        stage.setScene(scene);
        stage.show();
    }

    public void onLinkClicked() throws IOException {
        goTo("register.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
