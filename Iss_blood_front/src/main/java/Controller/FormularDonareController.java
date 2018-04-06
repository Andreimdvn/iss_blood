package Controller;

import Service.MainService;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class FormularDonareController {

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

    public void setMainService(MainService service) {
        this.mainService = service;
    }
}
