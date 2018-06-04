package Controller;

import Model.*;
import Utils.CustomMessageBox;
import Validators.AddPacientValidator;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class StarePacientiController extends ControlledScreen {

    ObservableList<StarePacient> observableList = FXCollections.observableArrayList();

    private Logger logger = LogManager.getLogger(StarePacient.class.getName());

    public void populate(){
    }

    @FXML
    private TableView<StarePacient> starePacientTableView;

    @FXML
    private TableColumn<StarePacient,String> numePacientColumn;

    @FXML
    private TableColumn<CerereSange,String> cnpPacientColumn;

    @FXML
    private TableColumn<StarePacient, GrupaSange> grupaSangeColumn;

    @FXML
    private TableColumn<StarePacient, RH> rhColumn;

    @FXML
    private TableColumn<StarePacient,Integer> numarCereriColumn;

    @FXML
    private TableColumn<StarePacient,Integer> donatoriColumn;

    @FXML
    private ComboBox<GrupaSange> grupaSangeComboBox;

    @FXML
    private ComboBox<RH> rhComboBox;

    @FXML
    private JFXTextField numePacient;

    @FXML
    private JFXTextField cnpPacient;

    @FXML
    private void initialize(){
        numePacientColumn.setCellValueFactory(new PropertyValueFactory<>("numePacient"));
        cnpPacientColumn.setCellValueFactory(new PropertyValueFactory<>("cnpPacient"));
        rhColumn.setCellValueFactory(new PropertyValueFactory<>("rh"));
        donatoriColumn.setCellValueFactory(new PropertyValueFactory<>("donatoriPreferentiali"));
        grupaSangeColumn.setCellValueFactory(new PropertyValueFactory<>("grupaSange"));
        numarCereriColumn.setCellValueFactory(new PropertyValueFactory<>("numarCereri"));

        grupaSangeComboBox.getItems().addAll(GrupaSange.O1,GrupaSange.A2,GrupaSange.B3,GrupaSange.AB4);
        grupaSangeComboBox.getSelectionModel().selectFirst();
        rhComboBox.getItems().addAll(RH.POZITIV,RH.NEGATIV);
        rhComboBox.getSelectionModel().selectFirst();

        starePacientTableView.setItems(observableList);
    }


    /**
     *  Handle add a new pacient
     *  GrupaSange and RH are filled with first value
     *
     *
     *  Validate name and cnp fields of pacient
     *  If fields are incorrect with errors will appear
     */
    @FXML
    private void handleAddPacient() {
        String namePacientFromField = numePacient.getText();
        String cnpPacientFromField = cnpPacient.getText();
        GrupaSange grupaPacientFromComboBox = grupaSangeComboBox.getValue();
        RH rhPacientFromComboBox = rhComboBox.getValue();
        Pacient pacientForValidation = new Pacient(namePacientFromField, cnpPacientFromField, grupaPacientFromComboBox, rhPacientFromComboBox);
        Pair<Boolean, String> validationResult = new AddPacientValidator().validate(pacientForValidation);
        if(!validationResult.getKey()) {
            logger.debug("Eroare la adaugare pacient \n" + validationResult.getValue());
            new CustomMessageBox("Eroare adaugare pacient", validationResult.getValue(),1).show();
        } else {
            String idMedic = ((MedicInfo)getScreenController().userInfo).getCnp();
            pacientForValidation.setIdMedic(idMedic);
            Pair<Boolean, String> response = getService().addPacient(pacientForValidation);
            new CustomMessageBox("Adaugare pacient", response.getValue(), response.getKey() ? 0 : 1).show();
            logger.debug("Adaugare pacient cu mesajul " + response.getValue());
        }
    }

    @Override
    void updateThis() {
        MedicInfo info = (MedicInfo) getScreenController().userInfo;
        List<StarePacient> x = getService().getStareActuala(info.getIdLocatie());
        observableList.setAll(x);
    }
}
