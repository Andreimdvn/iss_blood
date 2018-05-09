package Controller;

import Model.*;
import Service.MainService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;

public class StarePacientiController implements ControlledScreensInterface {

    private MainService mainService;
    private ControllerScreens controller;



    ObservableList<StarePacient> observableList = FXCollections.observableArrayList();

    public void populate(){
        StarePacient s = new StarePacient("Alin",GrupaSange.O1,RH.POZITIV,1,3,5,3,4, Importanta.MEDIE);
        StarePacient b = new StarePacient("Tudor",GrupaSange.B3,RH.NEGATIV,2,4,1,5,1 , Importanta.RIDICATA);

        observableList.addAll(s,b);
        starePacientTableView.setItems(observableList);
    }

    @FXML
    private TableView<StarePacient> starePacientTableView;

    @FXML
    private TableColumn<StarePacient,String> numePacientColumn;

    // @FXML
    //  private TableColumn<CerereSange,String> cnpPacientColumn;

    @FXML
    private TableColumn<StarePacient, GrupaSange> grupaSangeColumn;

    @FXML
    private TableColumn<StarePacient, RH> rhColumn;

    @FXML
    private TableColumn<StarePacient, Integer> trombociteColumn;

    @FXML
    private TableColumn<StarePacient,Integer> globuleColumn;

    @FXML
    private TableColumn<StarePacient,Integer> plasmaColumn;

    @FXML
    private TableColumn<StarePacient,Integer> numarCereriColumn;

    @FXML
    private TableColumn<StarePacient,Integer> donatoriColumn;

    @FXML
    private TableColumn<StarePacient, Importanta> importantaColumn;

    @FXML
    private void initialize(){
        numePacientColumn.setCellValueFactory(new PropertyValueFactory<>("numePacient"));
        // cnpPacientColumn.setCellValueFactory(new PropertyValueFactory<>("cnpPacient"));
        globuleColumn.setCellValueFactory(new PropertyValueFactory<>("numarPungiGlobuleRosii"));
        plasmaColumn.setCellValueFactory(new PropertyValueFactory<>("numarPungiPlasma"));
        trombociteColumn.setCellValueFactory(new PropertyValueFactory<>("numarPungiTrombocite"));
        rhColumn.setCellValueFactory(new PropertyValueFactory<>("rh"));
        importantaColumn.setCellValueFactory(new PropertyValueFactory<>("importanta"));
        donatoriColumn.setCellValueFactory(new PropertyValueFactory<>("donatoriPreferentiali"));
        grupaSangeColumn.setCellValueFactory(new PropertyValueFactory<>("grupaSange"));
        numarCereriColumn.setCellValueFactory(new PropertyValueFactory<>("numarCereri"));

    }


    public void setMainService(MainService mainService){
        this.mainService = mainService;
    }

    @Override
    public void setScreenParent(ControllerScreens screenParent) {
        this.controller = screenParent;
    }
}
