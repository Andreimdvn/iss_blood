package Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CustomMessageBox {
    private Stage stage = new Stage();
    private String titlu;
    private String message;
    private int type = 1; //1 = eroare, 0 = info

    /**
     * Creeaza un error message box
     * @param titlu
     * @param message
     */
    public CustomMessageBox(String titlu,String message)
    {
        this.titlu = titlu;
        this.message = message;
    }

    /**
     *
     * @param titlu
     * @param message
     * @param type 0 = info, 1=eroare
     */
    public CustomMessageBox(String titlu,String message,int type)
    {
        this.titlu = titlu;
        this.message = message;
        this.type = type;
    }

    public void show(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/CustomMessageBox.fxml"));
        try {
            Parent root = loader.load();
            CustomMessageBoxController controller = loader.getController();
            if(type == 1)
                stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            controller.setMessage(message);
            controller.setTitle(titlu);
            if(type == 1)
                controller.setType(CustomMessageBoxController.Type.ALERT);
            else if(type == 0)
                controller.setType(CustomMessageBoxController.Type.INFO);
            Scene a = new Scene(root);
            controller.createMessageBox();
            stage.setScene(a);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
