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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;


public class MainApplication extends Application {

    private  static Stage primaryStage;
    public static Properties properties;
    public static String propertiesPath = "/Config/defaultProperties.props";
    private void loadScreens(){
        properties = new Properties();
        loadProperties(propertiesPath,properties);
        MainService service = new MainService(new FlaskClient(properties));
        ControllerScreens controller = new ControllerScreens(service);
        controller.loadScreen(Screen.LOGIN_SCREEN, Screen.LOGIN_RESOURCE);
        controller.loadScreen(Screen.REGISTER_SCREEN, Screen.REGISTER_RESOURCE);
        controller.loadScreen(Screen.DONATOR_SCREEN,Screen.DONATOR_RESOURCE);
        controller.loadScreen(Screen.FORMULAR_DONARE_SCREEN, Screen.FORMULAR_DONARE_RESOURCE);
        controller.loadScreen(Screen.ISTORIC_DONARI_SCREEN,Screen.ISTORIC_DONARI_RESOURCE);
        controller.loadScreen(Screen.MEDIC_SCREEN,Screen.MEDIC_RESOURCE);
        controller.loadScreen(Screen.ISTORIC_CERERI_SCREEN,Screen.ISTORIC_CERERI_RESOURCE);
        controller.loadScreen(Screen.STARE_PACIENTI_SCREEN,Screen.STARE_PACIENTI_RESOURCE);
        controller.loadScreen(Screen.CERERE_SANGE_SCREEN,Screen.CERERE_SANGE_RESOURCE);

        controller.setScreen(Screen.LOGIN_SCREEN);
        Group root = new Group();
        root.getChildren().addAll(controller);
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        this.primaryStage.setResizable(false);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }


    /**
     * Loads application properties from a given path
     * @param path the path of the props
     * @param properties props object
     */
    public void loadProperties(String path,Properties properties){
        try {
            properties.load(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
