package Controller;

import Model.RegisterInfo;
import Service.MainService;
import Utils.CustomMessageBox;
import Utils.Screen;
import Validators.RegisterValidator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Pair;


public class RegisterController implements ControlledScreensInterface{

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
    private JFXTextField fullnameTextField;

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
    private FontAwesomeIconView closeIcon;

    private double xOffset;

    private double yOffset;

    private MainService mainService;

    private ControllerScreens controller;

    private void removeLicentaHBox(){
        registerPane.getChildren().remove(licentaHbox);
        licentaHbox = null;
    }

    private boolean isAnySelected(){
        return transfuzieToggleButton.isSelected() ||
                medicToggleButton.isSelected();

    }

    private void addLicentaHBox(){
        licentaHbox = new HBox();
        FontAwesomeIconView a = new FontAwesomeIconView();
        a.setIcon(FontAwesomeIcon.BARCODE);
        a.setSize("22");
        a.setFill(Paint.valueOf("white"));

        licentaTextField = new JFXTextField();
        licentaTextField.getStyleClass().add("textbox");
        licentaTextField.setUnFocusColor(Paint.valueOf("white"));
        licentaTextField.setFocusColor(judetTextField.getFocusColor());
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
        String fullname = fullnameTextField.getText();
        String cnp = cnpTextField.getText();
        String judet = judetTextField.getText();
        String localitate = localitateTextField.getText();
        String address = addressTextField.getText();
        String phone = phoneTextField.getText();
        String accountType = accountTypeToggleGroup.getSelectedToggle().toString();
        String license = "";
        if(licentaTextField != null)
            license = licentaTextField.getText();

        RegisterInfo info = new RegisterInfo(username, password, email, fullname, fullname, cnp, judet, localitate, address, phone, accountType, license);
        RegisterValidator validator = new RegisterValidator();
        Pair<Boolean, String> validationResult = validator.Validate(info);
        if(validationResult.getKey())
        {
            Pair<Boolean, String> response = mainService.register(info);
            if(response.getKey())
            {
                CustomMessageBox customMessageBox = new CustomMessageBox("info", "Registered successfully", 0);
                customMessageBox.show();
                controller.setScreen(Screen.LOGIN_SCREEN);
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
    }

    @FXML
    private void loginLabelClicked(){
       controller.setScreen(Screen.LOGIN_SCREEN);
    }

    private void enableStyle(){
        String focusColor = "#fea02f";
        judetTextField.setFocusColor(Paint.valueOf(focusColor));
        passwordTextField.setFocusColor(Paint.valueOf(focusColor));
        usernameTextField.setFocusColor(Paint.valueOf(focusColor));
        emailTextField.setFocusColor(Paint.valueOf(focusColor));
        fullnameTextField.setFocusColor(Paint.valueOf(focusColor));
        phoneTextField.setFocusColor(Paint.valueOf(focusColor));


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

    public void setMainService(MainService mainService){
        this.mainService = mainService;
    }

    @Override
    public void setScreenParent(ControllerScreens screenParent) {
        this.controller = screenParent;
    }
}
