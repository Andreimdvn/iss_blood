package Controller;

import Model.*;
import Service.MainService;
import Utils.CustomMessageBox;
import Utils.Screen;
import Validators.FormularValidator;
import Validators.ValidationException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FormularDonareController extends ControlledScreen {

    @FXML
    private JFXTextField firstNameTextField;

    @FXML
    private JFXTextField lastNameTextField;

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
    private ToggleGroup sexToggleGroup;

    @FXML
    private ToggleButton masculinToggle;

    @FXML
    private ToggleButton femininToggle;


    @FXML
    private ComboBox<GrupaSange> grupaSangeComboBox;

    @FXML
    private ComboBox<RH> rhComboBox;


    private Logger logger = LogManager.getLogger(FormularDonareController.class.getName());

    /**
     * Sends a request to the main screen controller to load the post-registration view
     */
    private void loadPostFormular(){
        DonatorDashboardController donatorDashboardController = (DonatorDashboardController)getScreenController().getControlledScreen(Screen.DONATOR_SCREEN);
        donatorDashboardController.loadPostFormular();
    }

    @FXML
    public void trimiteFormular()
    {
        //ia field-urile
        //le trimite la service
        //daca e ok, trece la ecranul urmator

        FormularDonare formularDonare = null;
        try {
            formularDonare = GetInfoFormular();
        } catch (ValidationException e) {
            CustomMessageBox msg = new CustomMessageBox("Eroare", "Formularul nu a fost completat corect. " +
                    "\n" + e.getMessage(), 1);
            msg.show();
            return;
        }

        Pair<Boolean, String> rez = getService().trimiteFormularDonare(formularDonare);
        if(rez.getKey())
        {
            CustomMessageBox msg = new CustomMessageBox("Info", "Formularul a fost trimis cu succes", 0);
            msg.show();
            loadPostFormular();
        }
        else
        {
            CustomMessageBox msg = new CustomMessageBox("Eroare", rez.getValue(), 1);
            msg.show();
        }
    }

    /**
     *
     * @return Informatiile sau null daca un camp obligatoriu nu a fost completat
     */
    private FormularDonare GetInfoFormular() throws ValidationException {
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

        FormularDonare formularDonare = new FormularDonare(username, lastName, firstName, sex, phone,
                domiciliuLocalitate, domiciliuJudet, domiciliuAdresa,
                resedintaLocalitate, resedintaJudet, resedintaAdresa,
                beneficiarFullName, beneficiarCNP, grupa, rh, zileDisponibil);

        FormularValidator validator = new FormularValidator();
        validator.Validate(formularDonare);

        return formularDonare;
    }

    @FXML
    private void initialize(){
        grupaSangeComboBox.getItems().addAll(GrupaSange.O1,GrupaSange.A2,GrupaSange.B3,GrupaSange.AB4);
        rhComboBox.getItems().addAll(RH.POZITIV,RH.NEGATIV);
    }

    @Override
    public void setScreenController(ScreenController screenController) {
        super.setScreenController(screenController);

        autoFillTextFields();
    }


    private void autoFillTextFields()
    {
        DonatorInfo info = (DonatorInfo) getScreenController().userInfo;
        firstNameTextField.setText(info.getPrenume());
        lastNameTextField.setText(info.getNume());
        DomiciliuJudetTextField.setText(info.getDomiciliuJudet());
        DomiciliuLocalitateTextField.setText(info.getDomiciliuLocalitate());
        DomiciliuAdresaTextField.setText(info.getDomiciliuAdresa());
        ResedintaJudetTextField.setText(info.getResedintaJudet());
        ResedintaLocalitateTextField.setText(info.getResedintaLocalitate());
        ResedintaAdresaTextField.setText(info.getResedintaAdresa());
        phoneTextField.setText(info.getTelefon());
        sexToggleGroup.selectToggle(info.getSex() == Sex.FEMININ ? femininToggle : masculinToggle);
    }

}
