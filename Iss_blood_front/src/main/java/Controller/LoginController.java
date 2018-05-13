package Controller;

import Service.MainService;
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

    @FXML
    private void loginClicked(){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if(Objects.equals(username, "donator"))
        getScreenController().setScreen(Screen.DONATOR_SCREEN);
        else if(Objects.equals(username, "medic"))
            getScreenController().setScreen(Screen.MEDIC_SCREEN);
/*        Pair<Boolean, String> canLogin = mainService.login(username, password);
        if (canLogin.getKey()) {
            controller.setScreen(Screen.DONATOR_SCREEN);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login");
            alert.setContentText(canLogin.getValue());
            alert.showAndWait();
        }
  */
    }

    /***
     * Load /View/RegisterView.fxml
     */

    @FXML
    private void registerLabelClicked() {
        getScreenController().setScreen(Screen.REGISTER_SCREEN);
    }

    private Stage getStage() {
        return (Stage) closeIcon.getScene().getWindow();
    }

    @FXML
    private void closeWindow(){
        Stage current = getStage();
        current.close();
    }
}
