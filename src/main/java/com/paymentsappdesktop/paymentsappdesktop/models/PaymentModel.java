package com.paymentsappdesktop.paymentsappdesktop.models;

import javafx.beans.property.*;

public class PaymentModel {
    private IntegerProperty paymentId;
    private DoubleProperty paymentAmount;
    private StringProperty paymentDescription;
    private StringProperty paymentUser;

    public PaymentModel(Integer paymentId, Double paymentAmount, String paymentDescription, String paymentUser) {
        this.paymentId = new SimpleIntegerProperty(paymentId);
        this.paymentAmount = new SimpleDoubleProperty(paymentAmount);
        this.paymentDescription = new SimpleStringProperty(paymentDescription);
        this.paymentUser = new SimpleStringProperty(paymentUser);
    }

    public DoubleProperty getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = new SimpleDoubleProperty(paymentAmount);
    }

    public IntegerProperty getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = new SimpleIntegerProperty(paymentId);
    }

    public StringProperty getPaymentUser() {
        return paymentUser;
    }

    public void setPaymentUser(String paymentUser) {
        this.paymentUser = new SimpleStringProperty(paymentUser);
    }

    public StringProperty getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = new SimpleStringProperty(paymentDescription);
    }
}
