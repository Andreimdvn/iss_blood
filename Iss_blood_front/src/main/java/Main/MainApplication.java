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
        controller.loadScreen(Screen.CENTRU_TRANSFUZIE_SCREEN,Screen.CENTRU_TRANSFUZIE_RESOURCE);
        controller.loadScreen(Screen.CENTRU_CERERI_DONARI_SCREEN,Screen.CENTRU_CERERI_DONARI_RESOURCE);
        controller.loadScreen(Screen.CENTRU_CERERI_SANGE_SCREEN,Screen.CENTRU_CERERI_SANGE_RESOURCE);
        controller.loadScreen(Screen.CENTRU_STOC_PUNGI_SCREEN,Screen.CENTRU_STOC_PUNGI_RESOURCE);
        controller.loadScreen(Screen.FORMULAR_1_TEXT1_SCREEN,Screen.FORMULAR_1_TEXT1_RESOURCE);
        controller.loadScreen(Screen.FORMULAR_1_TEXT2_SCREEN,Screen.FORMULAR_1_TEXT2_RESOURCE);
        controller.loadScreen(Screen.FORMULAR_1_TEXT3_SCREEN,Screen.FORMULAR_1_TEXT3_RESOURCE);
        controller.loadScreen(Screen.FORMULAR_1_TEXT4_SCREEN,Screen.FORMULAR_1_TEXT4_RESOURCE);
        controller.loadScreen(Screen.FORMULAR_1_SCREEN,Screen.FORMULAR_1_RESOURCE);
        controller.loadScreen(Screen.FORMULAR_3_SCREEN,Screen.FORMULAR_3_RESOURCE);


        controller.setScreen(Screen.CENTRU_TRANSFUZIE_SCREEN);

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
