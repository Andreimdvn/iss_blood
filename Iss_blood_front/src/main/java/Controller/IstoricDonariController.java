package Controller;

import Model.*;
import Utils.CustomMessageBox;
import Utils.Screen;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import java.util.function.Predicate;

public class IstoricDonariController extends ControlledScreen {

    //TO DO: CE PLM e cu Vboxul si hardocarea asta


    private final int MAX_SIZE = 7;
    Logger logger = LogManager.getLogger(IstoricDonariController.class.getName());
    private boolean isFull(){
        return getSize() == MAX_SIZE;
    }
    private int getSize(){
        return viewIstoric.getItems().size() - 2;
    }

    @FXML
    private TableView viewIstoric;

    @FXML
    private TableColumn numarDonareColumn;
    @FXML
    private TableColumn centruDonareColumn;
    @FXML
    private TableColumn statusColumn;

    private Predicate<DonareInfo> filterData;
    private Predicate<DonareInfo> filterCentru;
    private Predicate<DonareInfo> filterStatus;



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
        st.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Screen.DONATOR_ANALIZA_RESOURCE));
                try {
                    Parent loadedScreen = loader.load();
                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);

                    stage.setScene(new Scene(loadedScreen));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

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
        //tabelDonari.getChildren().add(getSize() + 1,hBox);
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
    private void initialize(){

        viewIstoric.setItems(model);
        numarDonareColumn.setCellValueFactory(new PropertyValueFactory<>("numarDonare"));
        centruDonareColumn.setCellValueFactory(new PropertyValueFactory<>("centruDonare"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        initializePopUpForTableView();
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
