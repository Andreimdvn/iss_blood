package Controller;

import Model.*;
import Utils.CustomMessageBox;
import Utils.Screen;
import javafx.application.Application;
import javafx.event.EventHandler;
import Model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IstoricDonariController extends ControlledScreen {
    Logger logger = LogManager.getLogger(IstoricDonariController.class.getName());

    @FXML
    private TableView viewIstoric;

    @FXML
    private TableColumn numarDonareColumn;
    @FXML
    private TableColumn centruDonareColumn;
    @FXML
    private TableColumn statusColumn;

    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField centruDonareTextField;
    @FXML
    private ComboBox statusComboBox;


    private Predicate<DonareInfo> filterDate = info -> true;
    private Predicate<DonareInfo> filterCentru = info -> true;
    private Predicate<DonareInfo> filterStatus = info -> true;

    private Collection<DonareInfo> istoric;
    private ObservableList<DonareInfo> model = FXCollections.observableArrayList();


    @FXML
    private void initialize(){
        viewIstoric.setItems(model);
        numarDonareColumn.setCellValueFactory(new PropertyValueFactory<>("numarDonare"));
        centruDonareColumn.setCellValueFactory(new PropertyValueFactory<>("centruDonare"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList lst = FXCollections.observableArrayList();
        lst.add(Status.DISTRIBUIRE);
        lst.add(Status.IN_ASTEPTARE);
        lst.add(Status.NONCONFORM);
        lst.add(Status.CALIFICARE);
        lst.add(Status.PREGATIRE);
        lst.add(Status.PRELEVARE);
        statusComboBox.setItems(lst);
        initializePopUpForTableView();
    }

    @FXML
    public void OnDateChanged()
    {
        if(datePicker.getValue() == null)
            return;
        filterDate = donareInfo -> donareInfo.getData().equals("") || donareInfo.getData().equals(datePicker.getValue().toString());
        refreshView();
    }

    @FXML
    public void OnCentruChanged()
    {
        String centru = centruDonareTextField.getText();
        if (centru.equals(""))
            filterCentru = donareInfo -> true;
        else
            filterCentru = donareInfo -> donareInfo.getCentruDonare().equals(centru);
        refreshView();
    }

    @FXML
    public void OnStatusChanged()
    {
        if(statusComboBox.getValue() == null)
            return;
        filterStatus = donareInfo -> donareInfo.getStatus().equals(statusComboBox.getValue());
        refreshView();
    }


    /**
     * Adauga un action listener la tabelul cu istoric donari pentru deschiderea ferestrelor cu rezultate
     */
    private void initializePopUpForTableView(){
        viewIstoric.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {

                DonareInfo infoDonare = (DonareInfo)newSelection;
                //testing
                //infoDonare = new DonareInfo(103,"centru",Status.NONCONFORM,new Analiza(100,false,false,false,false,false,false, GrupaSange.A2, RH.NEGATIV),"Vlad","10/10/2019");

                if(infoDonare.getStatus()!=Status.NONCONFORM || infoDonare.getStatus()!= Status.NONCONFORM)
                {
                    CustomMessageBox msg = new CustomMessageBox("Info", "Analizele nu sunt finalizate inca. Va rugam reveniti mai tarziu." +
                            "\n", 1);
                    msg.show();
                    return;
                }else if(infoDonare.getAnaliza() == null){
                    CustomMessageBox msg = new CustomMessageBox("Error", "A aparut o eroare la procesarea analizelor. Va rugam contactati un cadru de la centrul de transfuzii." +
                            "\n", 1);
                    msg.show();
                    return;
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Screen.DONATOR_ANALIZA_RESOURCE));
                try {
                    Parent loadedScreen = loader.load();
                    ((DonatorAnalizaScreenController)loader.getController()).load(infoDonare);
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(new Scene(loadedScreen));
                    stage.show();
                } catch (IOException e) {
                    logger.log(Level.ERROR,e.getStackTrace().toString());
                }
            }
        });
    }
    @FXML
    public void clearFilters()
    {
        datePicker.setValue(null);
        centruDonareTextField.setText("");
        statusComboBox.setValue(null);
        filterDate = info -> true;
        filterCentru = info -> true;
        filterStatus = info -> true;

        refreshView();
    }


    @Override
    void updateThis() {
        //Trimite un request catre sever si asteapta pana primeste raspunsul
        istoric =  getService().getIstoricDonare(getScreenController().userInfo.getUsername());
        refreshView();
    }

    /**
     * Reaplica filtrarile pe istoric si updateaza TableView
     */
    private void refreshView()
    {
        List<DonareInfo> rez = istoric.stream()
                .filter(filterCentru.and(filterDate).and(filterStatus))
                .collect(Collectors.toList());
        model.setAll(rez);
    }
}
