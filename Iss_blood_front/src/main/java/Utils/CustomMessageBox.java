package Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CustomMessageBox {
    private Stage stage = new Stage();
    private String titlu;
    private String message;
    private int type = 1;

    public CustomMessageBox(String titlu,String message)
    {
        this.titlu = titlu;
        this.message = message;
    }
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
