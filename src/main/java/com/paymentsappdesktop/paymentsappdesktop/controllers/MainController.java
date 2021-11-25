package com.paymentsappdesktop.paymentsappdesktop.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentsappdesktop.paymentsappdesktop.Credentials;
import com.paymentsappdesktop.paymentsappdesktop.components.DialogComponentHelper;
import com.paymentsappdesktop.paymentsappdesktop.models.PaymentModel;
import com.paymentsappdesktop.paymentsappdesktop.models.Payment;
import com.paymentsappdesktop.paymentsappdesktop.models.User;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import okhttp3.*;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    //Add
    @FXML
    private TextField descriptionAdd;
    @FXML
    private TextField amountAdd;
    //Delete
    @FXML
    private TextField idDelete;
    //Get
    // add your data here from any source
    private ObservableList<PaymentModel> payments = FXCollections.observableArrayList();
    @FXML
    private TableView<PaymentModel> paymentsTable;
    @FXML
    public TableColumn<PaymentModel, Number> paymentId;
    @FXML
    public TableColumn<PaymentModel, String> paymentDescription;
    @FXML
    public TableColumn<PaymentModel, String> paymentUser;
    @FXML
    public TableColumn<PaymentModel, Number> paymentAmount;
    //Edit
    @FXML
    private TextField idEdit;
    @FXML
    private TextField descriptionEdit;
    @FXML
    private TextField amountEdit;
    //Edit password
    @FXML
    private TextField newPassword;
    @FXML
    private TextField repeatNewPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paymentId.setCellValueFactory(cellData -> cellData.getValue().getPaymentId());
        paymentAmount.setCellValueFactory(cellData -> cellData.getValue().getPaymentAmount());
        paymentDescription.setCellValueFactory(cellData -> cellData.getValue().getPaymentDescription());
        paymentUser.setCellValueFactory(cellData -> cellData.getValue().getPaymentUser());

        String originalInput = Credentials.username+":"+Credentials.password;
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://localhost:8080/payment")
                .method("GET", null)
                .addHeader("Authorization", "Basic " + encodedString)
                .build();
        try {
            ResponseBody responseBody = client.newCall(request).execute().body();
            String data = responseBody.string();

            ObjectMapper objectMapper = new ObjectMapper();
            List<Payment> dataAsListOfPayments = objectMapper.readValue(data, new TypeReference<List<Payment>>() {});

            for (Payment p : dataAsListOfPayments ) {
                PaymentModel pModel = new PaymentModel(p.getId(), p.getAmount(), p.getDescription(), p.getUser().getUsername());
                payments.add(pModel);
            }

        } catch (IOException e) {
            Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Something went wrong.");
            dialog.showAndWait();
        }

        paymentsTable.setItems(payments);
    }

    public void onChange(ActionEvent actionEvent) throws IOException {
        String newPassword = this.newPassword.getText();
        String newPasswordRepeat = this.repeatNewPassword.getText();

        if ( newPassword.isEmpty() || newPasswordRepeat.isEmpty() ){
            Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Password should not be empty");
            dialog.showAndWait();
        } else if ( !newPassword.equals(newPasswordRepeat)){
            Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "The passwords should match.");
            dialog.showAndWait();
        } else {
            String originalInput = Credentials.username+":"+Credentials.password;
            String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n" +
                    "\"username\": \"" + Credentials.username + "\"," +
                    "\n\"password\": \"" + newPassword + "\"\n" +
                    "}");
            Request request = new Request.Builder()
                    .url("http://localhost:8080/user")
                    .method("PUT", body)
                    .addHeader("Authorization", "Basic " + encodedString)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();

            if ( response.code() == 200 ) {
                Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "The password was updated.");
                dialog.showAndWait();
            } else {
                Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Something went wrong.");
                dialog.showAndWait();
            }
        }
    }

    public void onEdit(ActionEvent actionEvent) throws IOException {
        String id = this.idEdit.getText();
        String description = this.descriptionEdit.getText();
        String amountText = this.amountEdit.getText();
        double amount = 0;
        try{
            amount = Double.parseDouble(amountText);
        } catch (Exception e) {
            Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Amount should be a number.");
            dialog.showAndWait();
            return;
        }

        if ( id.isEmpty() ) {
            Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Id should not be empty.");
            dialog.showAndWait();
        }
        else if ( amount <= 0 ){
            Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Amount should be positive.");
            dialog.showAndWait();
        } else {
            String originalInput = Credentials.username+":"+Credentials.password;
            String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n" +
                    "\"description\": \"" + description + "\"," +
                    "\n\"amount\": " + amount + "\n}");
            Request request = new Request.Builder()
                    .url("http://localhost:8080/payment/"+id)
                    .method("PUT", body)
                    .addHeader("Authorization", "Basic " + encodedString)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();

            if ( response.code() == 200 ) {
                Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Correct", "The payment was updated.");
                dialog.showAndWait();

                int size = payments.size();
                for(int index=0; index<size; index++){
                    PaymentModel p = payments.get(index);

                    if (p.getPaymentId().get() == Integer.parseInt(id) ) {
                        payments.set(index, new PaymentModel(Integer.parseInt(id), amount, description, Credentials.username));
                    }
                }
            } else {
                Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Something went wrong.");
                dialog.showAndWait();
            }
        }
    }

    public void onAdd(ActionEvent actionEvent) throws IOException {
        String description = this.descriptionAdd.getText();
        String amountText = this.amountAdd.getText();
        double amount = 0;
        try{
            amount = Double.parseDouble(amountText);
        } catch (Exception e) {
            Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Amount should be a number.");
            dialog.showAndWait();
            return;
        }

        if ( amount <= 0 ){
            Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Amount should be positive.");
            dialog.showAndWait();
        } else {
            String originalInput = Credentials.username+":"+Credentials.password;
            String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n" +
                    "\"description\": \"" + description + "\"," +
                    "\n\"amount\": " + amount + "\n}");
            Request request = new Request.Builder()
                    .url("http://localhost:8080/payment/")
                    .method("POST", body)
                    .addHeader("Authorization", "Basic " + encodedString)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();

            if ( response.code() == 201 ) {
                Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Correct", "The payment was added.");
                dialog.showAndWait();

                String data = response.body().string();

                ObjectMapper objectMapper = new ObjectMapper();
                Payment newPayment = objectMapper.readValue(data, Payment.class);
                payments.add(new PaymentModel(newPayment.getId(), amount,description, Credentials.username ));
            } else {
                Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Something went wrong.");
                dialog.showAndWait();
            }
        }
    }

    public void onDelete(ActionEvent actionEvent) throws IOException {
        String idDelete = this.idDelete.getText();

        if ( idDelete.isEmpty() ){
            Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Id should not be empty.");
            dialog.showAndWait();
        } else {
            String originalInput = Credentials.username+":"+Credentials.password;
            String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            Request request = new Request.Builder()
                    .url("http://localhost:8080/payment/"+idDelete)
                    .method("DELETE", null)
                    .addHeader("Authorization", "Basic " + encodedString)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = client.newCall(request).execute();

            if ( response.code() == 200 ) {
                Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Correct", "The payment was deleted.");
                dialog.showAndWait();

                for ( PaymentModel pM : payments ) {
                    if (pM.getPaymentId().get() == Integer.parseInt(idDelete)){
                        payments.remove(pM);
                    }
                }
            } else {
                Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Something went wrong.");
                dialog.showAndWait();
            }
        }
    }
}
