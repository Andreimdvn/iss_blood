package Controller;

import Model.RegisterInfo;
import Utils.CustomMessageBox;
import Utils.Screen;
import Validators.RegisterValidator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.util.Pair;


public class RegisterController extends ControlledScreen {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXTextField usernameTextField;

    @FXML
    private JFXPasswordField passwordTextField;

    @FXML
    private JFXTextField emailTextField;

    @FXML
    private JFXTextField phoneTextField;

    @FXML
    private JFXTextField judetTextField;

    @FXML
    private JFXTextField localitateTextField;

    @FXML
    private JFXTextField addressTextField;

    @FXML
    private JFXTextField nameTextField;

    @FXML
    private JFXTextField surnameTextField;

    @FXML
    private JFXTextField cnpTextField;

    @FXML
    private ToggleButton donatorToggleButton;

    @FXML
    private ToggleButton medicToggleButton;


    @FXML
    private ToggleButton transfuzieToggleButton;

    private ToggleGroup accountTypeToggleGroup;

    @FXML
    private JFXTextField licentaTextField;

    @FXML
    private VBox registerPane;

    @FXML
    private HBox licentaHbox;

    @FXML
    private JFXButton buttonRegister;

    @FXML
    private FontAwesomeIconView closeIcon;

    private double xOffset;

    private double yOffset;


    private void removeLicentaHBox(){
        registerPane.getChildren().remove(licentaHbox);
        licentaHbox = null;
    }

    private boolean isAnySelected(){
        return transfuzieToggleButton.isSelected() ||
                medicToggleButton.isSelected();

    }

    private Logger logger = LogManager.getLogger(RegisterController.class.getName());


    private void addLicentaHBox(){
        logger.debug("Camp licenta a fost creat");
        licentaHbox = new HBox();
        FontAwesomeIconView a = new FontAwesomeIconView();
        a.setIcon(FontAwesomeIcon.BARCODE);
        a.setSize("22");
        a.setStyleClass("icon");

        licentaTextField = new JFXTextField();

        licentaTextField.getStyleClass().add("textboxPrimary");

        licentaTextField.setPromptText("Licenta");

        licentaHbox.setSpacing(4);
        licentaHbox.getChildren().addAll(a, licentaTextField);
        registerPane.getChildren().add(licentaHbox);
    }

    @FXML
    private void selectedToggleButton(){
        if(donatorToggleButton.isSelected()) {
            removeLicentaHBox();
        }
        else if(this.isAnySelected()) {
            if(licentaHbox == null) {
               addLicentaHBox();
            }
        }
        else if(!this.isAnySelected()) {
            removeLicentaHBox();
            donatorToggleButton.setSelected(true);
        }
    }

    @FXML
    private void closeWindow(){
        Stage current = getStage();
        current.close();
    }

    private Stage getStage(){
        return (Stage) closeIcon.getScene().getWindow();
    }

    @FXML
    private void registerClicked(){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String email = emailTextField.getText();
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String cnp = cnpTextField.getText();
        String judet = judetTextField.getText();
        String localitate = localitateTextField.getText();
        String address = addressTextField.getText();
        String phone = phoneTextField.getText();
        String accountType = accountTypeToggleGroup.getSelectedToggle().toString();
        String license = "";
        if(licentaTextField != null)
            license = licentaTextField.getText();
        RegisterInfo info = new RegisterInfo(username, password, email, name, surname, cnp, judet, localitate, address, phone, accountType, license);

        logger.debug("Buton register a fost apasat");
        buttonRegister.setDisable(true);

        Platform.runLater(()->
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            RegisterValidator validator = new RegisterValidator();
            Pair<Boolean, String> validationResult = validator.validate(info);
            if(validationResult.getKey())
            {
                getScreenController().setScreen(Screen.LOGIN_SCREEN);
                Pair<Boolean, String> response = getService().register(info);
                if(response.getKey())
                {
                    CustomMessageBox customMessageBox = new CustomMessageBox("info", "Registered successfully", 0);
                    customMessageBox.show();
                    getScreenController().setScreen(Screen.LOGIN_SCREEN);
                    ClearFields();
                }
                else
                {
                    CustomMessageBox messageBox = new CustomMessageBox("Error", response.getValue(), 1);
                    messageBox.show();
                }
            }
            else
            {
                CustomMessageBox messageBox = new CustomMessageBox("Error", validationResult.getValue(), 1);
                messageBox.show();
            }

            buttonRegister.setDisable(false);
        });
    }

    private void ClearFields()
    {
        usernameTextField.clear();
        passwordTextField.clear();
        emailTextField.clear();
        nameTextField.clear();
        surnameTextField.clear();
        cnpTextField.clear();
        judetTextField.clear();
        localitateTextField.clear();
        addressTextField.clear();
        phoneTextField.clear();
        if(licentaTextField != null)
            licentaTextField.clear();

    }


    @FXML
    private void loginLabelClicked(){
       getScreenController().setScreen(Screen.LOGIN_SCREEN);
        logger.debug("Buton Go back to login screen a fost apasat");
    }

    private void enableStyle(){

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
        accountTypeToggleGroup = donatorToggleButton.getToggleGroup();
    }
}
