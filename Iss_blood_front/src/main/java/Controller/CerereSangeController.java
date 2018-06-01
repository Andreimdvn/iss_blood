package Controller;

import Model.*;
import Utils.CustomMessageBox;
import Utils.FunctiiUtile;
import Validators.CerereSangeValidator;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CerereSangeController extends ControlledScreen {
    @FXML
    private JFXTextField trombocitetTextField;
    @FXML
    private JFXTextField globuleRosiTextField;
    @FXML
    private JFXTextField plasmaTextField;

    @FXML
    private JFXTextField numePacientTextField;

    @FXML
    private JFXTextField cnpPacientTextField;

    @FXML
    private JFXComboBox<GrupaSange> grupaSangeComboBox;

    @FXML
    private JFXComboBox<RH> rhComboBox;

    @FXML
    private JFXRadioButton scazutaRadioButton;

    @FXML
    private JFXRadioButton medieRadioButton;

    @FXML
    private JFXRadioButton ridicataRadioButton;


    @FXML
    private void initialize(){
        grupaSangeComboBox.getItems().addAll(GrupaSange.O1,GrupaSange.A2,GrupaSange.B3,GrupaSange.AB4);
        rhComboBox.getItems().addAll(RH.POZITIV,RH.NEGATIV);

    }

    private Logger logger = LogManager.getLogger(CerereSangeController.class.getName());


    @FXML
    private void trimiteCerere() {
        logger.debug("S-a apasat butonul pentru a trimite cererea.");

        CerereSange cerere;
        try {
            cerere = getCerereSange();
        }catch (Exception ex)
        {
            new CustomMessageBox("Error", "Completati toate campurile!").show();
            return;
        }
        CerereSangeValidator validator = new CerereSangeValidator();
        Pair<Boolean,String> validatorVerdict = validator.validate(cerere);

        if (validatorVerdict.getKey()) {
            String cerereLogDetails = "Importanta : " + cerere.getImportanta() + ". Contine: "
                    + cerere.getNumarPungiTrombocite() + " pungi trombocite,\n"
                    + cerere.getNumarPungiGlobuleRosii() + " pungi globule rosi, "
                    + cerere.getNumarPungiPlasma() + " pungi plasma";

            logger.info("Date introduse corect in cerere de sange. " + cerereLogDetails);
<<<<<<< HEAD

=======
>>>>>>> 527d2c6d19298cca63b038dccf16bddb68c99fea

            cerere.setNumeMedic(((MedicInfo)getScreenController().userInfo).getFullName());
            String cnpMedic = ((MedicInfo)getScreenController().userInfo).getCnp();
            Pair<Boolean, String> requestVerdict = getService().trimiteCerereSange(cerere, cnpMedic);
            if (requestVerdict.getKey() == null) {
                this.logger.warn("request key is null!");
                new CustomMessageBox("", requestVerdict.getValue()).show();
                return;
            }

            if (requestVerdict.getKey()){
                CustomMessageBox msg = new CustomMessageBox("Cerere trimisa", requestVerdict.getValue() +
                        "\n" + cerereLogDetails,0);
                msg.show();
                clearCerereSange();

            }
            else
            {
                new CustomMessageBox("Error", requestVerdict.getValue()).show();
            }
        }
        else{
            logger.info("Date introduse gresit in cererea de sange. Error: " + validatorVerdict.getValue());
            CustomMessageBox msg = new CustomMessageBox("Error", validatorVerdict.getValue());
            msg.show();
        }
    }

    private void changeResult(JFXTextField punga,int value)
    {
        if(FunctiiUtile.isNumeric(punga.getText()))
        {
            String valoareNoua = String.valueOf((Integer.parseInt(punga.getText())+ value));

            if(Integer.parseInt(valoareNoua) >= 0)
            punga.setText(valoareNoua);
        }
        else
        {
            //Alert
            punga.setText("0");
        }
    }

    private CerereSange getCerereSange(){
        RH rh = getRH();
        Importanta importanta = getImportanta();
        GrupaSange grupaSange = getGrupaSange();

        String nume = numePacientTextField.getText();
        String cnp = cnpPacientTextField.getText();
        Integer trombocite = Integer.valueOf(trombocitetTextField.getText());
        Integer plasma = Integer.valueOf(plasmaTextField.getText());
        Integer globule = Integer.valueOf(globuleRosiTextField.getText());

        return new CerereSange(nume,cnp,grupaSange,rh,trombocite,globule,plasma,importanta);
    }

    private void clearCerereSange(){
        rhComboBox.setValue(null);
        grupaSangeComboBox.setValue(null);
        scazutaRadioButton.setSelected(false);
        medieRadioButton.setSelected(true);
        ridicataRadioButton.setSelected(false);
        numePacientTextField.clear();
        cnpPacientTextField.clear();
        trombocitetTextField.setText("0");
        plasmaTextField.setText("0");
        globuleRosiTextField.setText("0");
    }

    @FXML
    private void trombociteDown(){
        logger.debug("Buton - pentru trombocite a fost apasat");
        changeResult(trombocitetTextField,-1);
    }
    @FXML
    private void trombociteUp(){
        logger.debug("Buton + pentru trombocite a fost apasat");
        changeResult(trombocitetTextField,+1);
    }
    @FXML
    private void globuleRosiDown(){

        logger.debug("Buton - pentru globule rosii a fost apasat");
        changeResult(globuleRosiTextField,-1);
    }
    @FXML
    private void globuleRosiUp(){

        logger.debug("Buton + pentru globule rosii a fost apasat");
        changeResult(globuleRosiTextField,+1);
    }
    @FXML
    private void plasmaDown(){
        logger.debug("Buton - pentru plasma a fost apasat");
        changeResult(plasmaTextField,-1);
    }
    @FXML
    private void plasmaUp(){
        logger.debug("Buton + pentru plasma a fost apasat");
        changeResult(plasmaTextField,1);
    }

    private Importanta getImportanta(){
        if(scazutaRadioButton.isSelected())
            return Importanta.SCAZUTA;
        else if(medieRadioButton.isSelected())
            return Importanta.MEDIE;
        else if(ridicataRadioButton.isSelected())
            return Importanta.RIDICATA;
        else
            return null;
    }

    private RH getRH(){
        return rhComboBox.getValue();
    }

    private GrupaSange getGrupaSange(){
        return grupaSangeComboBox.getValue();
    }

    @Override
    void updateThis() {

    }
}
