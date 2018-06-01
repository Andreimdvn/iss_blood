package Controller;

import Model.DonareInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

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
        model.setAll(istoric); //TO DO: filtrari
    }
}
