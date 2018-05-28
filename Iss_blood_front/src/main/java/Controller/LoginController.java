package Controller;

import Model.DonatorInfo;
import Model.MedicInfo;
import Model.UserInfo;
import Utils.CustomMessageBox;
import Utils.Screen;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;


public class LoginController extends ControlledScreen {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private FontAwesomeIconView closeIcon;

    @FXML
    private JFXTextField usernameTextField;

    @FXML
    private JFXPasswordField passwordTextField;


    private double xOffset;

    private double yOffset;

    private void enableStyle(){
        String focusColor = "#fea02f";

        usernameTextField.setFocusColor(Paint.valueOf(focusColor));
        passwordTextField.setFocusColor(Paint.valueOf(focusColor));

        mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage primaryStage = getStage();
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    @FXML
    private void initialize(){
        enableStyle();
    }

    private Logger logger = LogManager.getLogger(LoginController.class.getName());

    @FXML
    private void loginClicked(){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        logger.debug("Login button has been clicked");


//        Pair<Integer, String> canLogin;
        Pair<UserInfo, String> canLogin = getService().login(username, password);
        // Login for debug
//        if(username.equals("donator"))
//            canLogin = new Pair<>(1,"");
//        else if(username.equals("medic"))
//            canLogin = new Pair<>(2,"");
//        else
//            canLogin = new Pair<>(3,"");
        //remove this ^^^ on production

        if (canLogin.getKey() == null) { //failed
            new CustomMessageBox("Login",canLogin.getValue()).show();
            return;
        }

        ScreenController screenController = getScreenController();

        screenController.userInfo = canLogin.getKey();

        if (canLogin.getKey() instanceof DonatorInfo){
            loadScreensDonator();

            screenController.setScreen(Screen.DONATOR_SCREEN);
        }
        else if (canLogin.getKey() instanceof MedicInfo) {
            loadScreensMedic();

            getScreenController().setScreen(Screen.MEDIC_SCREEN);
        }
        else  {
            loadScreensCentru();

            getScreenController().setScreen(Screen.CENTRU_TRANSFUZIE_SCREEN);

            //logger.debug("Credintiale gresite");
            //controller.setScreen();
        }

    }

    /***
     * Load /View/RegisterView.fxml
     */

    @FXML
    private void registerLabelClicked(){
        logger.debug("Buton Go to register screen a fost apasat");
        getScreenController().setScreen(Screen.REGISTER_SCREEN);
    }

    private Stage getStage() {
        return (Stage) closeIcon.getScene().getWindow();
    }

    @FXML
    private void closeWindow(){
        logger.debug("X has been clicked");
        Stage current = getStage();
        current.close();
    }


    private void loadScreensDonator()
    {
        ScreenController screenController = getScreenController();

        screenController.loadScreen(Screen.DONATOR_SCREEN,Screen.DONATOR_RESOURCE);
        screenController.loadScreen(Screen.FORMULAR_DONARE_SCREEN, Screen.FORMULAR_DONARE_RESOURCE);
        screenController.loadScreen(Screen.ISTORIC_DONARI_SCREEN,Screen.ISTORIC_DONARI_RESOURCE);

        screenController.loadScreen(Screen.FORMULAR_1_TEXT1_SCREEN,Screen.FORMULAR_1_TEXT1_RESOURCE);
        screenController.loadScreen(Screen.FORMULAR_1_TEXT2_SCREEN,Screen.FORMULAR_1_TEXT2_RESOURCE);
        screenController.loadScreen(Screen.FORMULAR_1_TEXT3_SCREEN,Screen.FORMULAR_1_TEXT3_RESOURCE);
        screenController.loadScreen(Screen.FORMULAR_1_TEXT4_SCREEN,Screen.FORMULAR_1_TEXT4_RESOURCE);
        screenController.loadScreen(Screen.FORMULAR_1_SCREEN,Screen.FORMULAR_1_RESOURCE);
        screenController.loadScreen(Screen.FORMULAR_3_SCREEN,Screen.FORMULAR_3_RESOURCE);
    }

    private void loadScreensMedic()
    {
        ScreenController screenController = getScreenController();

        screenController.loadScreen(Screen.MEDIC_SCREEN,Screen.MEDIC_RESOURCE);
        screenController.loadScreen(Screen.ISTORIC_CERERI_SCREEN,Screen.ISTORIC_CERERI_RESOURCE);
        screenController.loadScreen(Screen.STARE_PACIENTI_SCREEN,Screen.STARE_PACIENTI_RESOURCE);
        screenController.loadScreen(Screen.CERERE_SANGE_SCREEN,Screen.CERERE_SANGE_RESOURCE);
    }

    private void loadScreensCentru()
    {
        ScreenController screenController = getScreenController();

        screenController.loadScreen(Screen.CENTRU_TRANSFUZIE_SCREEN,Screen.CENTRU_TRANSFUZIE_RESOURCE);
        screenController.loadScreen(Screen.CENTRU_CERERI_DONARI_SCREEN,Screen.CENTRU_CERERI_DONARI_RESOURCE);
        ((CentruCereriDonariController)screenController.getControlledScreen("CENTRU_CERERI_DONARI")).updateThis();
        screenController.loadScreen(Screen.CENTRU_ANALIZA_SCREEN,Screen.CENTRU_ANALIZA_RESOURCE);
        screenController.loadScreen(Screen.CENTRU_CHESTIONAR_SCREEN,Screen.CENTRU_CHESTIONAR_RESOURCE);
        screenController.loadScreen(Screen.CENTRU_CERERI_SANGE_SCREEN,Screen.CENTRU_CERERI_SANGE_RESOURCE);
        screenController.loadScreen(Screen.CENTRU_STOC_PUNGI_SCREEN,Screen.CENTRU_STOC_PUNGI_RESOURCE);
        screenController.loadScreen(Screen.FORMULAR_DONARE_SCREEN, Screen.CENTRU_FORMULAR_RESOURCE);

    }
}
