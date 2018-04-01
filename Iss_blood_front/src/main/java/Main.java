import Controller.LoginController;
import Services.MainService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

public class Main extends Application{


    private void loadLogin(Stage primaryStage){
        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/LoginView.fxml"));
        MainService service = new MainService();
        try {
            Parent root = loader.load();
            LoginController loginController = new LoginController();
            loginController.setMainService(service);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void loadRegister(Stage primaryStage){

    }
    @Override
    public void start(Stage primaryStage) {
        loadLogin(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
