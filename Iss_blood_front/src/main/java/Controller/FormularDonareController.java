package Controller;

import Model.GrupaSange;
import Model.RH;
import Service.MainService;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class FormularDonareController extends ControlledScreen {

    @FXML
    private JFXTextField fullnameTextField;

    @FXML
    private JFXTextField donatFullnameTextField;

    @FXML
    private JFXTextField donatCnpTextField;

    @FXML
    private JFXTextField DomiciliuLocalitateTextField;

    @FXML
    private JFXTextField DomiciliuJudetTextField;

    @FXML
    private JFXTextField DomiciliuAdresaTextField;

    @FXML
    private JFXTextField ResedintaLocalitateTextField;

    @FXML
    private JFXTextField ResedintaJudetTextField;

    @FXML
    private JFXTextField ResedintaAdresaTextField;

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
    private ComboBox<GrupaSange> grupaSangeComboBox;

    @FXML
    private ComboBox<RH> rhComboBox;


    @FXML
    private void initialize(){
        grupaSangeComboBox.getItems().addAll(GrupaSange.O1,GrupaSange.A2,GrupaSange.B3,GrupaSange.AB4);
        rhComboBox.getItems().addAll(RH.POZITIV,RH.NEGATIV);
    }

}
