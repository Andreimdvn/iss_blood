package Controller;

import Model.*;
import Utils.Screen;
import Validators.FormularValidator;
import Validators.ValidationException;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractFormularDonareController extends ControlledScreen {
    @FXML
    protected JFXTextField firstNameTextField;

    @FXML
    protected JFXTextField lastNameTextField;

    @FXML
    protected JFXTextField donatFullnameTextField;

    @FXML
    protected JFXTextField donatCnpTextField;

    @FXML
    protected JFXTextField DomiciliuLocalitateTextField;

    @FXML
    protected JFXTextField DomiciliuJudetTextField;

    @FXML
    protected JFXTextField DomiciliuAdresaTextField;

    @FXML
    protected JFXTextField ResedintaLocalitateTextField;

    @FXML
    protected JFXTextField ResedintaJudetTextField;

    @FXML
    protected JFXTextField ResedintaAdresaTextField;

    @FXML
    protected JFXTextField phoneTextField;

    @FXML
    protected JFXCheckBox luniCheckbox;

    @FXML
    protected JFXCheckBox martiCheckbox;

    @FXML
    protected JFXCheckBox miercuriCheckbox;

    @FXML
    protected JFXCheckBox joiCheckbox;

    @FXML
    protected JFXCheckBox vineriCheckbox;

    @FXML
    protected ToggleGroup sexToggleGroup;

    @FXML
    protected ToggleButton masculinToggle;

    @FXML
    protected ToggleButton femininToggle;


    @FXML
    protected ComboBox<GrupaSange> grupaSangeComboBox;

    @FXML
    protected ComboBox<RH> rhComboBox;


    protected Logger logger = LogManager.getLogger(FormularDonareController.class.getName());


    @FXML
    public abstract void trimiteFormular();


    /**
     * Sends a request to the main screen controller to load the post-registration view
     */
    protected void loadPostFormular(){
        DonatorDashboardController donatorDashboardController = (DonatorDashboardController)getScreenController().getControlledScreen(Screen.DONATOR_SCREEN);
        donatorDashboardController.loadPostFormular();
    }

    /**
     *
     * @return Informatiile sau null daca un camp obligatoriu nu a fost completat
     */
    protected FormularDonare GetInfoFormular() throws ValidationException {
        String username = getScreenController().userInfo.getUsername();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String beneficiarFullName = donatFullnameTextField.getText();
        String beneficiarCNP = donatCnpTextField.getText();
        GrupaSange grupa = grupaSangeComboBox.getValue();
        if(grupa == null)
            grupa = GrupaSange.UNKNOWN;
        RH rh = rhComboBox.getValue();
        if(rh == null)
            rh = RH.UNKNOWN;
        String domiciliuLocalitate = DomiciliuLocalitateTextField.getText();
        String domiciliuJudet = DomiciliuJudetTextField.getText();
        String domiciliuAdresa = DomiciliuAdresaTextField.getText();
        String resedintaLocalitate = ResedintaLocalitateTextField.getText();
        String resedintaJudet = ResedintaJudetTextField.getText();
        String resedintaAdresa = ResedintaAdresaTextField.getText();
        String phone = phoneTextField.getText();
        if(sexToggleGroup.getSelectedToggle() == null)
        {
            throw new ValidationException("Nu ati selectat sexul");
        }
        String genderString = ((RadioButton)sexToggleGroup.getSelectedToggle()).getText();
        Sex sex;
        if(genderString.toLowerCase().contains("f"))
            sex = Sex.FEMININ;
        else
            sex = Sex.MASCULIN;

        short zileDisponibil = 0; //de ex: 00011 = luni si marti
        short currentDayVal = 1;
        JFXCheckBox[] checkBoxes = new JFXCheckBox[]
                {luniCheckbox, martiCheckbox, miercuriCheckbox, joiCheckbox, vineriCheckbox};

        for(JFXCheckBox checkBox : checkBoxes)
        {
            if(checkBox.isSelected())
                zileDisponibil += currentDayVal;
            currentDayVal *= 2;
        }

        FormularDonare formularDonare = new FormularDonare(lastName, firstName, sex, phone,
                domiciliuLocalitate, domiciliuJudet, domiciliuAdresa,
                resedintaLocalitate, resedintaJudet, resedintaAdresa,
                beneficiarFullName, beneficiarCNP, grupa, rh, zileDisponibil);

        FormularValidator validator = new FormularValidator();
        validator.Validate(formularDonare);

        return formularDonare;
    }

    @FXML
    protected void initialize(){
        grupaSangeComboBox.getItems().addAll(GrupaSange.O1,GrupaSange.A2,GrupaSange.B3,GrupaSange.AB4);
        rhComboBox.getItems().addAll(RH.POZITIV,RH.NEGATIV);
    }





}
