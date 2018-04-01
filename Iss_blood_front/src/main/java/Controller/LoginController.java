package Controller;

import Services.MainService;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    private MainService mainService;

    /***
     *
     * @param mainService
     */
    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    @FXML
    private FontAwesomeIconView closeIcon;

    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private JFXPasswordField passwordTextField;


    private void enableStyle(){

    }

    @FXML
    private void initialize(){
        enableStyle();
    }
    @FXML
    private void closeWindow(){
        Stage current = (Stage) closeIcon.getScene().getWindow();
        current.close();
    }
}
