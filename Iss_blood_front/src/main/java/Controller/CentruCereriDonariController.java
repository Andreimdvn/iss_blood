package Controller;

import Model.*;
import Service.MainService;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CentruCereriDonariController extends ControlledScreen{

    @FXML
    private TableView<CerereDonare> donareTableView;

    private ObservableList<CerereDonare> donareObservableList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<CerereDonare,String> numeColumn;
    @FXML
    private TableColumn<CerereDonare,String> prenumeColumn;
    @FXML
    private TableColumn<CerereDonare, GrupaSange> grupaSangeColumn;
    @FXML
    private TableColumn<CerereDonare, RH> rhColumn;
    @FXML
    private TableColumn<CerereDonare, Status> statusColumn;

    @FXML
    private void initialize(){
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        prenumeColumn.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        grupaSangeColumn.setCellValueFactory(new PropertyValueFactory<>("grupaSange"));
        rhColumn.setCellValueFactory(new PropertyValueFactory<>("rh"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        donareTableView.setItems(donareObservableList);
    }
    private CerereDonare getSelectedItem(){
        return donareTableView.getSelectionModel().getSelectedItem();
    }

    private void changeStatus(Boolean prelevare,Boolean pregatire,Boolean calificare){
        prelevareButton.setDisable(!prelevare);
        pregatireButton.setDisable(!pregatire);
        calificareButton.setDisable(!calificare);
    }

    @FXML
    private void populateDummy(){
        CerereDonare a = new CerereDonare(
                "Moldovan","Daniel", GrupaSange.UNKNOWN, RH.UNKNOWN,Status.IN_ASTEPTARE
        );
        CerereDonare b = new CerereDonare(
                "Oancea","Alin", GrupaSange.O1, RH.POZITIV,Status.PRELEVARE
        );
        CerereDonare c = new CerereDonare(
                "Moldovan","Andrei", GrupaSange.B3, RH.NEGATIV,Status.PREGATIRE
        );
        donareObservableList.addAll(a,b,c);
    }

    @FXML
    private void button1Clicked(){
            CentruTransfuzieController cr = (CentruTransfuzieController) getScreenController().getControlledScreen("CENTRU_TRANSFUZIE");
            cr.setCenter(getScreenController().getScreen("CENTRU_CHESTIONAR"));

    }
    @FXML
    private void button2Clicked(){

    }
    @FXML
    private void button3Clicked(){

    }

    private void updateStatus(){
        CerereDonare cr = getSelectedItem();
        if(cr == null) {
            changeStatus(false,false,false);
        }
        else{
            if(cr.getStatus() == Status.IN_ASTEPTARE)
                changeStatus(true,false,false);
            else if(cr.getStatus() == Status.PRELEVARE)
                changeStatus(true,true,false);
            else if(cr.getStatus() == Status.PREGATIRE)
                changeStatus(true,true,true);
        }
    }

    @FXML
    private JFXButton prelevareButton;

    @FXML
    private JFXButton pregatireButton;

    @FXML
    private JFXButton calificareButton;

    @FXML
    private void selectedRow(){
        updateStatus();
    }

}
