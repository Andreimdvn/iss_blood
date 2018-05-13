package Main;

import Communication.FlaskClient;
import Controller.ScreenController;

import Service.MainService;
import Utils.Screen;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainApplication extends Application {

    private  static Stage primaryStage;

    private void loadScreens(){
        MainService service = new MainService(new FlaskClient());
        ScreenController controller = new ScreenController(service);
        controller.loadScreen(Screen.LOGIN_SCREEN, Screen.LOGIN_RESOURCE);
        controller.loadScreen(Screen.REGISTER_SCREEN, Screen.REGISTER_RESOURCE);
        controller.loadScreen(Screen.DONATOR_SCREEN,Screen.DONATOR_RESOURCE);
        controller.loadScreen(Screen.FORMULAR_DONARE_SCREEN, Screen.FORMULAR_DONARE_RESOURCE);
        controller.loadScreen(Screen.ISTORIC_DONARI_SCREEN,Screen.ISTORIC_DONARI_RESOURCE);
        controller.loadScreen(Screen.MEDIC_SCREEN,Screen.MEDIC_RESOURCE);
        controller.loadScreen(Screen.ISTORIC_CERERI_SCREEN,Screen.ISTORIC_CERERI_RESOURCE);
        controller.loadScreen(Screen.STARE_PACIENTI_SCREEN,Screen.STARE_PACIENTI_RESOURCE);
        controller.loadScreen(Screen.CERERE_SANGE_SCREEN,Screen.CERERE_SANGE_RESOURCE);


        controller.loadScreen(Screen.FORMULAR_1_TEXT1_SCREEN,Screen.FORMULAR_1_TEXT1_RESOURCE);
        controller.loadScreen(Screen.FORMULAR_1_TEXT2_SCREEN,Screen.FORMULAR_1_TEXT2_RESOURCE);
        controller.loadScreen(Screen.FORMULAR_1_TEXT3_SCREEN,Screen.FORMULAR_1_TEXT3_RESOURCE);
        controller.loadScreen(Screen.FORMULAR_1_TEXT4_SCREEN,Screen.FORMULAR_1_TEXT4_RESOURCE);
        controller.loadScreen(Screen.FORMULAR_1_SCREEN,Screen.FORMULAR_1_RESOURCE);
        controller.loadScreen(Screen.FORMULAR_3_SCREEN,Screen.FORMULAR_3_RESOURCE);

        controller.setScreen(Screen.LOGIN_SCREEN);
        Group root = new Group();
        root.getChildren().addAll(controller);
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        this.primaryStage.setResizable(false);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        loadScreens();
    }

    @Override
    public void init() throws Exception {
        Thread.sleep(2000);
    }

    public static void resizeScreen() {
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
    }

}
