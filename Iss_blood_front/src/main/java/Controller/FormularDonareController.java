package Controller;

import Model.FormularDonare;
import Model.GrupaSange;
import Model.RH;
import Model.Sex;
import Service.MainService;
import Utils.CustomMessageBox;
import Utils.Screen;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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

        FormularDonare formularDonare = GetInfoFormular();

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

    private FormularDonare GetInfoFormular()
    {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String beneficiarFullName = donatFullnameTextField.getText();
        String beneficiarCNP = donatCnpTextField.getText();
        GrupaSange grupa = grupaSangeComboBox.getValue();
        RH rh = rhComboBox.getValue();
        String domiciliuLocalitate = DomiciliuLocalitateTextField.getText();
        String domiciliuJudet = DomiciliuJudetTextField.getText();
        String domiciliuAdresa = DomiciliuAdresaTextField.getText();
        String resedintaLocalitate = ResedintaLocalitateTextField.getText();
        String resedintaJudet = ResedintaJudetTextField.getText();
        String resedintaAdresa = ResedintaAdresaTextField.getText();
        String phone = phoneTextField.getText();
        String genderString = sexToggleGroup.getSelectedToggle().toString();
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

        return new FormularDonare(firstName, lastName, sex, phone, domiciliuLocalitate, domiciliuJudet, domiciliuAdresa,
                resedintaLocalitate, resedintaJudet, resedintaAdresa, beneficiarFullName, beneficiarCNP, grupa, rh, zileDisponibil);
    }

    @FXML
    private void initialize(){
        grupaSangeComboBox.getItems().addAll(GrupaSange.O1,GrupaSange.A2,GrupaSange.B3,GrupaSange.AB4);
        rhComboBox.getItems().addAll(RH.POZITIV,RH.NEGATIV);

        //TO DO: umple fieldurile automat
    }

}
