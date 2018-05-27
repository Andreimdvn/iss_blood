package Controller;

import Model.*;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class CentruCereriDonariController extends ControlledScreen{

    @FXML
    private TableView<FormularDonare> donareTableView;

    private ObservableList<FormularDonare> donareObservableList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<FormularDonare,String> numeColumn;
    @FXML
    private TableColumn<FormularDonare,String> prenumeColumn;
    @FXML
    private TableColumn<FormularDonare, GrupaSange> grupaSangeColumn;
    @FXML
    private TableColumn<FormularDonare, RH> rhColumn;
    @FXML
    private TableColumn<FormularDonare, Status> statusColumn;

    @FXML
    private void initialize(){
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        prenumeColumn.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        grupaSangeColumn.setCellValueFactory(new PropertyValueFactory<>("grupa"));
        rhColumn.setCellValueFactory(new PropertyValueFactory<>("rh"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        donareTableView.setItems(donareObservableList);
    }
    private FormularDonare getSelectedItem(){
        return donareTableView.getSelectionModel().getSelectedItem();
    }

    private void changeStatus(Boolean prelevare,Boolean pregatire,Boolean calificare){
        prelevareButton.setDisable(!prelevare);
        pregatireButton.setDisable(!pregatire);
        calificareButton.setDisable(!calificare);
    }

    /***
     *  Remove on production
     *  70% Yes
     */
    void updateThis(){

        List<FormularDonare> list = new ArrayList<>();
        list.addAll(donareObservableList);
        donareObservableList.setAll(list);
        //donareTableView.getSelectionModel().select(null);
        updateStatus();
    }

    @FXML
    private void populateDummy(){

        FormularDonare a = new FormularDonare("Moldovan","Daniel",Sex.MASCULIN, "0744176894", "Arad", "Arad", "Str.Scoalei", "Cluj", "Cluj-Napoca", "Str. Iustin","Ciprian","1234567890123",GrupaSange.UNKNOWN,RH.UNKNOWN,Status.IN_ASTEPTARE);
        donareObservableList.add(a);
    }

    private FormularDonare getSelected(){
        return donareTableView.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void button1Clicked(){
        CentruTransfuzieController cr = (CentruTransfuzieController) getScreenController().getControlledScreen("CENTRU_TRANSFUZIE");
        CentruPrelevareController cv = (CentruPrelevareController) getScreenController().getControlledScreen("CENTRU_CHESTIONAR");
        cv.setFormularDonare(getSelected());
        cr.setCenter(getScreenController().getScreen("CENTRU_CHESTIONAR"));

    }
    @FXML
    private void button2Clicked(){
        getSelected().setStatus(Status.PREGATIRE);
        updateThis();
    }
    @FXML
    private void button3Clicked(){
        CentruTransfuzieController cr = (CentruTransfuzieController) getScreenController().getControlledScreen("CENTRU_TRANSFUZIE");
        cr.setCenter(getScreenController().getScreen("CENTRU_ANALIZA"));
        CentruAnalizaController ca = (CentruAnalizaController) getScreenController().getControlledScreen("CENTRU_ANALIZA");
        ca.setFormularDonare(getSelected());
    }

    private void updateStatus(){
        FormularDonare cr = getSelectedItem();
        if(cr == null) {
            changeStatus(false,false,false);
        }
        else{
            if(cr.getStatus() == Status.IN_ASTEPTARE)
                changeStatus(true,false,false);
            else if(cr.getStatus() == Status.PRELEVARE)
                changeStatus(false,true,false);
            else if(cr.getStatus() == Status.PREGATIRE)
                changeStatus(false,false,true);
            else
                changeStatus(false,false,false);
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
