package Controller;

import Utils.Screen;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class IstoricDonariController extends ControlledScreen {

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
        tabelDonari.getChildren().add(getSize() + 1,hBox);
    }

    @FXML
    private void initialize(){

    }
}
