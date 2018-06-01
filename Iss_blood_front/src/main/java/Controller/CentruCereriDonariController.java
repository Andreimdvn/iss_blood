package Controller;

import Model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private List<FormularDonare> list = new ArrayList<>();

    @FXML
    private void initialize(){
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        prenumeColumn.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        grupaSangeColumn.setCellValueFactory(new PropertyValueFactory<>("grupa"));
        rhColumn.setCellValueFactory(new PropertyValueFactory<>("rh"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        donareTableView.setItems(donareObservableList);
        statusCombo.getItems().addAll(
                "ANY STATUS",Status.IN_ASTEPTARE.toString(),Status.PRELEVARE.toString()
                ,Status.PREGATIRE.toString());
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

    void getElements(){

    }

    public void updateThis(){

        list = getService().getFormulareDonariDupaLocatie(getInfo().getIdLocatie());
        donareObservableList.setAll(list);
        //donareTableView.getSelectionModel().select(null);
        updateStatus();
        filter();
    }

    private StaffInfo getInfo(){
        return (StaffInfo) getScreenController().userInfo;
    }

    @FXML
    private JFXTextField searchNume;

    @FXML
    private JFXTextField searchPrenume;

    @FXML
    private ComboBox<String> statusCombo;

    @FXML
    private void reset(){
        searchPrenume.setText("");
        searchNume.setText("");
        statusCombo.getSelectionModel().select(0);
        filter();
    }

    @FXML
    private void filter(){

        List<FormularDonare> donare = list.stream().filter(
                p -> p.getNume().toLowerCase().contains(searchNume.getText().toLowerCase()))
                .collect(Collectors.toList());

        donare = donare.stream().filter(
                p -> p.getPrenume().toLowerCase().contains(searchPrenume.getText().toLowerCase()))
                .collect(Collectors.toList());

        if(!statusCombo.getSelectionModel().isSelected(0) && !statusCombo.getSelectionModel().isEmpty())
        donare = donare.stream().filter(
                p -> Objects.equals(p.getStatus().toString(),
                        statusCombo.getSelectionModel().getSelectedItem())).collect(Collectors.toList());

        donareObservableList.setAll(donare);
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
        getService().staffUpdateFormularDonare(getSelected(),getInfo().getIdLocatie());
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
