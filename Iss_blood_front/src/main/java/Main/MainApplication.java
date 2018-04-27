package Main;

import Communication.FlaskClient;
import Controller.ControllerScreens;

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
        ControllerScreens controller = new ControllerScreens(service);
        controller.loadScreen(Screen.LOGIN_SCREEN, Screen.LOGIN_RESOURCE);
        controller.loadScreen(Screen.REGISTER_SCREEN, Screen.REGISTER_RESOURCE);
        controller.loadScreen(Screen.DONATOR_SCREEN,Screen.DONATOR_RESOURCE);
        controller.loadScreen(Screen.FORMULAR_DONARE_SCREEN, Screen.FORMULAR_DONARE_RESOURCE);
        controller.loadScreen(Screen.ISTORIC_DONARI_SCREEN,Screen.ISTORIC_DONARI_RESOURCE);

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
