package Utils;

import Controller.ChatController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Chat {

    private Stage stage = new Stage();

    public Chat(){
        showChat();
    }

    private void showChat(){
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/View/ChatView.fxml"));
        try {
            Parent root = loader.load();
            ChatController controller = loader.getController();

            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);

            Scene a = new Scene(root);
            stage.setScene(a);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
