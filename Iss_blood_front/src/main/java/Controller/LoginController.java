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
            new CustomMessageBox("Login", canLogin.getValue()).show();
            return;
        }

        ScreenController screenController = getScreenController();

        screenController.userInfo = canLogin.getKey();

        loadAfterLogin();
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


    @Override
    protected void updateThis() {
    }
}
