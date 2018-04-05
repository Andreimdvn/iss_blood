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

        String focusColor = "#00af66";
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
    }

    @FXML
    private void registerLabelClicked() {
        controller.setScreen(Screen.REGISTER_SCREEN);
    }

    private Stage getStage() {
        return (Stage) closeIcon.getScene().getWindow();
    }

    @FXML
    private void closeWindow(){
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
