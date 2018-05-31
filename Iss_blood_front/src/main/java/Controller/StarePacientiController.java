package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StarePacientiController extends ControlledScreen {

    ObservableList<StarePacient> observableList = FXCollections.observableArrayList();

    private Logger logger = LogManager.getLogger(StarePacient.class.getName());

    public void populate(){

        StarePacient s = new StarePacient("Alin","1234567890123",GrupaSange.O1,RH.POZITIV,1,3);
        StarePacient b = new StarePacient("Tudor","1234567890123",GrupaSange.B3,RH.NEGATIV,2,4);

        observableList.addAll(s,b);
        starePacientTableView.setItems(observableList);
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
    private void initialize(){
        numePacientColumn.setCellValueFactory(new PropertyValueFactory<>("numePacient"));
        cnpPacientColumn.setCellValueFactory(new PropertyValueFactory<>("cnpPacient"));
        rhColumn.setCellValueFactory(new PropertyValueFactory<>("rh"));
        donatoriColumn.setCellValueFactory(new PropertyValueFactory<>("donatoriPreferentiali"));
        grupaSangeColumn.setCellValueFactory(new PropertyValueFactory<>("grupaSange"));
        numarCereriColumn.setCellValueFactory(new PropertyValueFactory<>("numarCereri"));

    }

    @Override
    void updateThis() {

    }
}
