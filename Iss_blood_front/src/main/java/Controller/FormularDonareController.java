package Controller;

import Service.MainService;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class FormularDonareController implements ControlledScreensInterface {

    private MainService mainService;

    @FXML
    private JFXTextField fullnameTextField;

    @FXML
    private JFXTextField addressTextField;

    @FXML
    private JFXTextField phoneTextField;

    @FXML
    private JFXCheckBox luniCheckbox;

    @FXML
    private JFXCheckBox martiCheckbox;

    @FXML
    private JFXCheckBox miercuriCheckbox;

    @FXML
    private JFXCheckBox joiCheckbox;

    @FXML
    private JFXCheckBox vineriCheckbox;

    @FXML
    private void initialize(){

    }

    private ControllerScreens controller;

    @Override
    public void setScreenParent(ControllerScreens screenParent) {
        this.controller = screenParent;
    }

    public void setMainService(MainService service) {
        this.mainService = service;
    }
}
