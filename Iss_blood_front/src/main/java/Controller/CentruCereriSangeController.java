package Controller;

import Model.CerereSange;
import Model.GrupaSange;
import Model.Importanta;
import Model.RH;
import Service.MainService;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;

public class CentruCereriSangeController implements ControlledScreensInterface{

    private MainService mainService;
    private ControllerScreens controller;
    ObservableList<CerereSange> cereriSange = FXCollections.observableArrayList();

    private Logger logger = LogManager.getLogger(IstoricCereriController.class.getName());

    @FXML
    private TableView<CerereSange> cerereSangeTableView;

    @FXML
    private TableColumn<CerereSange,String> numePacientColumn;

    // @FXML
    //  private TableColumn<CerereSange,String> cnpPacientColumn;

    @FXML
    private TableColumn<CerereSange, GrupaSange> grupaSangeColumn;

    @FXML
    private TableColumn<CerereSange, RH> rhColumn;

    @FXML
    private TableColumn<CerereSange, Integer> trombociteColumn;

    @FXML
    private TableColumn<CerereSange,Integer> globuleColumn;

    @FXML
    private TableColumn<CerereSange,Integer> plasmaColumn;

    @FXML
    private TableColumn<CerereSange,Date> dateColumn;

    @FXML
    private TableColumn<CerereSange, Importanta> importantaColumn;

    @FXML
    private void initialize(){

        numePacientColumn.setCellValueFactory(new PropertyValueFactory<>("numePacient"));
        // cnpPacientColumn.setCellValueFactory(new PropertyValueFactory<>("cnpPacient"));
        globuleColumn.setCellValueFactory(new PropertyValueFactory<>("numarPungiGlobuleRosii"));
        plasmaColumn.setCellValueFactory(new PropertyValueFactory<>("numarPungiPlasma"));
        trombociteColumn.setCellValueFactory(new PropertyValueFactory<>("numarPungiTrombocite"));
        rhColumn.setCellValueFactory(new PropertyValueFactory<>("rh"));
        importantaColumn.setCellValueFactory(new PropertyValueFactory<>("importanta"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        grupaSangeColumn.setCellValueFactory(new PropertyValueFactory<>("grupaSange"));
        cerereSangeTableView.setItems(cereriSange);
    }

    @FXML
    private Label spitalLabel;

    @FXML
    private Label doctorLabel;

    @FXML
    private Label plasmaLabel;

    @FXML
    private Label trombociteLabel;

    @FXML
    private Label globuleLabel;


    @FXML
    private void selectedRow(){
        CerereSange cr =  cerereSangeTableView.getSelectionModel().getSelectedItem();

        if(cr != null) {
            spitalLabel.setText(cr.getSpital());
            doctorLabel.setText(cr.getNumeMedic());
            plasmaLabel.setText(cr.getNumarPungiPlasma().toString());
            trombociteLabel.setText(cr.getNumarPungiTrombocite().toString());
            globuleLabel.setText(cr.getNumarPungiGlobuleRosii().toString());

        }
    }

    @FXML
    private void populateDummy() {
        CerereSange cr = new CerereSange("NumePacient", "dsds", GrupaSange.A2, RH.NEGATIV,
                1,2,3, Date.valueOf("2017-06-13"),
                Importanta.MEDIE, "Moldovan Andrei", "Tudor's Hospital");
        this.cereriSange.add(cr);
    }

    public void setMainService(MainService mainService){
        this.mainService = mainService;
    }

    @Override
    public void setScreenParent(ControllerScreens screenParent) {
        this.controller = screenParent;
    }

}
