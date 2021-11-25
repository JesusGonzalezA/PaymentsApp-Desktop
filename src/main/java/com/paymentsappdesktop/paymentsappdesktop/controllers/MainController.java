package com.paymentsappdesktop.paymentsappdesktop.controllers;

import com.paymentsappdesktop.paymentsappdesktop.components.DialogComponentHelper;
import com.paymentsappdesktop.paymentsappdesktop.models.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
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
    private ObservableList<Payment> payments = FXCollections.observableArrayList();
    @FXML
    private TableView<Payment> paymentsTable;
    @FXML
    public TableColumn<Payment, Number> paymentId;
    @FXML
    public TableColumn<Payment, String> paymentDescription;
    @FXML
    public TableColumn<Payment, String> paymentUser;
    @FXML
    public TableColumn<Payment, Number> paymentAmount;
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

        // TODO get payments
        paymentsTable.setItems(payments);
    }

    public void onChange(ActionEvent actionEvent){
        String newPassword = this.newPassword.getText();
        String newPasswordRepeat = this.repeatNewPassword.getText();

        if ( newPassword.isEmpty() || newPasswordRepeat.isEmpty() ){
            Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Password should not be empty");
            dialog.showAndWait();
        } else if ( !newPassword.equals(newPasswordRepeat)){
            Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "The passwords should match.");
            dialog.showAndWait();
        } else {
            // TODO change password
        }
    }

    public void onEdit(ActionEvent actionEvent){
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
            // TODO edit
        }
    }

    public void onAdd(ActionEvent actionEvent){
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
            // TODO add
        }
    }

    public void onDelete(ActionEvent actionEvent){
        String idDelete = this.idDelete.getText();

        if ( idDelete.isEmpty() ){
            Dialog<String> dialog = DialogComponentHelper.createErrorDialog("Error", "Id should not be empty.");
            dialog.showAndWait();
        } else {
            // TODO delete
        }
    }
}
