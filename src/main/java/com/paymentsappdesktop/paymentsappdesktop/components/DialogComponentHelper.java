package com.paymentsappdesktop.paymentsappdesktop.components;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class DialogComponentHelper {
    public static Dialog<String> createErrorDialog(String title, String content) {
        Dialog<String> dialog = new Dialog<>();

        dialog.setTitle(title);
        dialog.setContentText(content);
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);

        return dialog;
    }
}
