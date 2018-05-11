package Controller;

import Service.MainService;
import Utils.CustomMessageBox;
import Utils.Screen;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;


public class LoginController implements ControlledScreensInterface {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private FontAwesomeIconView closeIcon;

    @FXML
    private JFXTextField usernameTextField;

    @FXML
    private JFXPasswordField passwordTextField;

    private MainService mainService;

    private double xOffset;

    private double yOffset;

    private ControllerScreens controller;

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



        Pair<Integer, String> canLogin = mainService.login(username, password);
        if (canLogin.getKey() == 0) {
            new CustomMessageBox("Login",canLogin.getValue()).show();
        } else if (canLogin.getKey() == 1){

            controller.setScreen(Screen.DONATOR_SCREEN);
        }
        else if (canLogin.getKey() == 2) {
            controller.setScreen(Screen.MEDIC_SCREEN);
        }
        else if (canLogin.getKey() == 3) {
            logger.debug("Credintiale gresite");
            //controller.setScreen();
        }

    }

    /***
     * Load /View/RegisterView.fxml
     */

    @FXML
    private void registerLabelClicked(){
        logger.debug("Buton Go to register screen a fost apasat");
        controller.setScreen(Screen.REGISTER_SCREEN);
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

    public void setMainService(MainService mainService){
        this.mainService = mainService;
    }

    @Override
    public void setScreenParent(ControllerScreens screenParent) {
        this.controller = screenParent;
    }
}
