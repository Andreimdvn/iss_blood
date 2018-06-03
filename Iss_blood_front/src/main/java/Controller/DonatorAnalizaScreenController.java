package Controller;

import Model.*;
import Utils.Screen;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.apache.logging.log4j.core.util.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DonatorAnalizaScreenController extends ControlledScreen{

    @FXML
    private AnchorPane mainPane;

    private double xOffset;

    private double yOffset;

    @FXML
    JFXButton closeButton;

    @FXML
    Label l_rh;
    @FXML
    Label l_gr;
    @FXML
    Label l_alt;
    @FXML
    Label l_sif;
    @FXML
    Label l_htlv;
    @FXML
    Label l_hcv;
    @FXML
    Label l_hiv;
    @FXML
    Label l_hbs;
    @FXML
    Label l_responsabil;
    @FXML
    Label l_data;
    @FXML
    Label l_cod;

    @FXML
    private void initialize(){
        //List<GrupaSange> grupeSange = new ArrayList<>(Arrays.asList(GrupaSange.values()));
        //List<RH> rhs = new ArrayList<>(Arrays.asList(RH.values()));
        //grupeSange.remove(GrupaSange.UNKNOWN);
        ///rhs.remove(RH.UNKNOWN);
        enableStyle();

    }
    private void enableStyle(){
        String focusColor = "#fea02f";
        mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) mainPane.getScene().getWindow();
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }
    private String translate(Boolean value){
        return value == true ? "Pozitiv" : "Negativ";
    }
    public void load(DonareInfo info){
        l_cod.setText(String.valueOf(info.getNumarDonare()));
        l_data.setText(info.getData());
        l_responsabil.setText(info.getNumeStaff());
        l_gr.setText(info.getGrupaSange().toString());
        l_rh.setText(info.getRh().toString());
        l_alt.setText(translate(info.getAnaliza().getALT()));
        l_hbs.setText(translate(info.getAnaliza().getHB()));
        l_hcv.setText(translate(info.getAnaliza().getANTIHCV()));
        l_hiv.setText(translate(info.getAnaliza().getANTIHIV()));
        l_htlv.setText(translate(info.getAnaliza().getANTIHTLV()));
        l_sif.setText(translate(info.getAnaliza().getSIF()));
    }
    public void closeWindow(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    void updateThis() {

    }
}
