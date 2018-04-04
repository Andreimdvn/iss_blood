package Controller;

import Service.MainService;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginController {

    private MainService mainService;

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

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

        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/DonatorDashboardView.fxml"));
        MainService service = new MainService();
        try {
            Stage primaryStage = new Stage();
            Parent root = loader.load();
            DonatorDashboardController loginController = new DonatorDashboardController();
            loginController.setMainService(service);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        closeWindow();
    }

    /***
     * Load /View/RegisterView.fxml
     */
    @FXML
    private void registerLabelClicked()
    {
        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/RegisterView.fxml"));
        MainService service = new MainService();
        try {
            Stage primaryStage = new Stage();
            Parent root = loader.load();
            RegisterController loginController = new RegisterController();
            loginController.setMainService(service);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        closeWindow();
    }

    private Stage getStage()
    {
        return (Stage) closeIcon.getScene().getWindow();
    }

    @FXML
    private void closeWindow(){
        Stage current = getStage();
        current.close();
    }
}
