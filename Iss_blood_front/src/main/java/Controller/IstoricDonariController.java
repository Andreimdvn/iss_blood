package Controller;

import Model.DonareInfo;
import Model.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IstoricDonariController extends ControlledScreen {

    //TO DO: CE PLM e cu Vboxul si hardocarea asta

    @FXML
    private VBox tabelDonari;

    private final int MAX_SIZE = 7;
    Logger logger = LogManager.getLogger(IstoricDonariController.class.getName());
    private boolean isFull(){
        return getSize() == MAX_SIZE;
    }
    private int getSize(){
        return tabelDonari.getChildren().size() - 2;
    }

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


    int contor = 0;
    @FXML
    private void dummyTest()
    {
        if(!isFull()) {
            contor ++;
            createHBoxIstoric(contor, "01/01/1990",
                    "Nicu camin 16", "Ready to view");
        }
    }
    private void createHBoxIstoric(int numarDonare,String dataDonare,String numeCentru,String status){

        logger.debug("Buton dummy a fost apasat");
        HBox hBox = new HBox();
        String numeStyle = "hboxIstoric";
        hBox.getStyleClass().add(numeStyle);
        Label id = new Label(String.valueOf(numarDonare));
        Label data = new Label(dataDonare);
        Label nume = new Label(numeCentru);
        Label st = new Label(status);


        String numeLabelStyle = "labelIstoric";
        id.getStyleClass().add(numeLabelStyle);
        data.getStyleClass().add(numeLabelStyle);
        nume.getStyleClass().add(numeLabelStyle);
        st.getStyleClass().add(numeLabelStyle);

        id.setPrefWidth(114);
        data.setPrefWidth(114);
        nume.setPrefWidth(225);
        st.setPrefWidth(84);

        hBox.getChildren().addAll(id,data,nume,st);
        tabelDonari.getChildren().add(getSize() + 1,hBox);
    }

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

    }

    @FXML
    public void OnDateChanged()
    {
        filterDate = donareInfo -> donareInfo.getData().equals(datePicker.getValue().toString());
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
        filterStatus = donareInfo -> donareInfo.getStatus().equals(statusComboBox.getValue());
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
