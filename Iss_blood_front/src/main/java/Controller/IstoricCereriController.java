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

import java.sql.Date;

public class IstoricCereriController extends ControlledScreen {
    ObservableList<CerereSange> cereriSange = FXCollections.observableArrayList();
    ObservableList<CerereSange> cereriSangeCompletate = FXCollections.observableArrayList();

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
    private TableColumn<CerereSange,Importanta> importantaColumn;

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

        numePacient2Column.setCellValueFactory(new PropertyValueFactory<>("numePacient"));
        // cnpPacientColumn.setCellValueFactory(new PropertyValueFactory<>("cnpPacient"));
        globule2Column.setCellValueFactory(new PropertyValueFactory<>("numarPungiGlobuleRosii"));
        plasma2Column.setCellValueFactory(new PropertyValueFactory<>("numarPungiPlasma"));
        trombocite2Column.setCellValueFactory(new PropertyValueFactory<>("numarPungiTrombocite"));
        rh2Column.setCellValueFactory(new PropertyValueFactory<>("rh"));
        importanta2Column.setCellValueFactory(new PropertyValueFactory<>("importanta"));
        date2Column.setCellValueFactory(new PropertyValueFactory<>("date"));
        grupaSange2Column.setCellValueFactory(new PropertyValueFactory<>("grupaSange"));

    }

    @FXML
    private TableView<CerereSange> cerereSangeCompletateTableView;

    @FXML
    private TableColumn<CerereSange,String> numePacient2Column;

    // @FXML
    //  private TableColumn<CerereSange,String> cnpPacientColumn;

    @FXML
    private TableColumn<CerereSange, GrupaSange> grupaSange2Column;

    @FXML
    private TableColumn<CerereSange, RH> rh2Column;

    @FXML
    private TableColumn<CerereSange, Integer> trombocite2Column;

    @FXML
    private TableColumn<CerereSange,Integer> globule2Column;

    @FXML
    private TableColumn<CerereSange,Integer> plasma2Column;

    @FXML
    private TableColumn<CerereSange,Date> date2Column;

    @FXML
    private TableColumn<CerereSange,Importanta> importanta2Column;


    public void populate(){
    }

    @FXML
    private void anulare(){
        CerereSange cr = cerereSangeTableView.getSelectionModel().getSelectedItem();
        if(cr != null)
        {
            getService().anulare(cr.getId());
        }
    }

    @Override
    void updateThis() {

        MedicInfo info = (MedicInfo) getScreenController().userInfo;
        cereriSange.setAll(getService().getCereriSange(info.getIdLocatie(),"IN_ASTEPTARE",true));
        cereriSangeCompletate.setAll(getService().getCereriSange(info.getIdLocatie(),"REZOLVATA",true));
        cerereSangeTableView.setItems(cereriSange);
        cerereSangeCompletateTableView.setItems(cereriSangeCompletate);
    }
}
