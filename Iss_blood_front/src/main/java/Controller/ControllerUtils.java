package Controller;

import javafx.scene.control.Alert;

/**
 * Metode statice pentru message box-uri and shit
 */
public class ControllerUtils {
    public static void ShowErrorMessage(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void ShowConfirmationMessage(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
