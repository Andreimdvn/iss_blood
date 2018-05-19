package Controller;

import Model.*;
import Utils.Screen;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.core.util.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CentruAnalizaScreenController extends ControlledScreen{

    @FXML
    JFXComboBox cb_rh;
    @FXML
    JFXComboBox cb_gr;
    @FXML
    JFXComboBox cb_alt;
    @FXML
    JFXComboBox cb_sif;
    @FXML
    JFXComboBox cb_htlv;
    @FXML
    JFXComboBox cb_hcv;
    @FXML
    JFXComboBox cb_hiv;
    @FXML
    JFXComboBox cb_hbs;
    @FXML
    Label label_responsabil;
    @FXML
    Label label_nume;
    @FXML
    Label label_prenume;
    @FXML
    Label label_varsta;
    @FXML
    Label label_sex;
    @FXML
    Label label_cod;

    @FXML
    private void initialize(){
        List<GrupaSange> grupeSange = new ArrayList<>(Arrays.asList(GrupaSange.values()));
        List<RH> rhs = new ArrayList<>(Arrays.asList(RH.values()));
        grupeSange.remove(GrupaSange.UNKNOWN);
        rhs.remove(RH.UNKNOWN);
        cb_gr.setItems( FXCollections.observableArrayList( grupeSange));
        cb_rh.setItems( FXCollections.observableArrayList( rhs));
        cb_alt.setItems( FXCollections.observableArrayList( PozNegEnum.values()));
        cb_sif.setItems( FXCollections.observableArrayList( PozNegEnum.values()));
        cb_htlv.setItems( FXCollections.observableArrayList( PozNegEnum.values()));
        cb_hcv.setItems( FXCollections.observableArrayList( PozNegEnum.values()));
        cb_hiv.setItems( FXCollections.observableArrayList( PozNegEnum.values()));
        cb_hbs.setItems( FXCollections.observableArrayList( PozNegEnum.values()));
    }

    public void setFields(String surname,String forename,Sex donatorSex,Integer age,Integer idBloodBag){
        label_nume.setText(surname);
        label_prenume.setText(forename);
        label_cod.setText(idBloodBag.toString());
        //todo add getScreenController.getCurrentAccountName() here
        label_responsabil.setText("missing implementation");
        label_varsta.setText(age.toString());
        label_sex.setText(donatorSex.toString());
    }
    public void back(){
        getScreenController().setScreen(Screen.CENTRU_TRANSFUZIE_SCREEN);
    }

}
