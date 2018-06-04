package Controller;

import Communication.FlaskClient;
import Model.StaffInfo;
import Utils.Screen;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static Utils.Screen.CENTRU_TRANSFUZIE_SCREEN;

public class CentruTransfuzieController extends ControlledScreen {

    private Logger logger = LogManager.getLogger(FlaskClient.class.getName());

    @FXML
    public Label homeLabelPungiGlobuleRosii;
    @FXML
    public Label homeLabelPungiPlasma;
    @FXML
    public Label homeLabelPungiTrombocite;

    @FXML
    public Label homeLabelDonariTotal;
    @FXML
    public Label homeLabelCereriSange;
    @FXML
    public Label homeLabelDonariAsteptare;

    @FXML
    private Label numeStaffLabel;
    @FXML
    private Label numeCentruLabel;

    @FXML
    private PieChart stocPieChart;
    private double xOffset;
    private double yOffset;

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane homePane;

    @FXML
    private ToggleButton t1;
    @FXML
    private ToggleButton t2;
    @FXML
    private ToggleButton t3;
    @FXML
    private ToggleButton t4;
    @FXML
    private ToggleButton t5;

    private void checkSelected(ToggleButton toggleButton){
        if(!anySelected()) {
            toggleButton.setSelected(true);
        }
    }

    private boolean anySelected(){
        return t1.isSelected() || t2.isSelected() || t3.isSelected() || t4.isSelected() || t5.isSelected();
    }

    @FXML
    protected void setCenter(Node a){
        borderPane.setCenter(a);
    }

    @FXML
    private void homeClicked(){
        borderPane.setCenter(homePane);
        checkSelected(t1);
        getScreenController().getControlledScreen(CENTRU_TRANSFUZIE_SCREEN).updateThis();

    }

    @FXML
    private void cereriDonariClicked(){
        borderPane.setCenter(super.getScreenController().getScreen("CENTRU_CERERI_DONARI"));
        checkSelected(t3);

    }

    @FXML
    private void stocCurentClicked(){
        borderPane.setCenter(super.getScreenController().getScreen("CENTRU_STOC_PUNGI"));
        checkSelected(t5);

    }

    @FXML
    private void cereriSangeClicked(){
        borderPane.setCenter(super.getScreenController().getScreen("CENTRU_CERERI_SANGE"));
        checkSelected(t4);


    }

    @FXML
    private void cerereDonareClicked(){
        borderPane.setCenter(super.getScreenController().getScreen("FORMULAR_DONARE"));
        checkSelected(t2);


        //borderPane.getCenter().setTranslateX(85);
        //borderPane.getCenter().setTranslateY(30);

        // borderPane.getCenter().setLayoutY(6500);
    }
    @FXML

    private void chatClicked(){

    borderPane.setCenter(super.getScreenController().getScreen(Screen.CHAT_SCREEN));
    }
    @FXML
    private void logout(){
        loadLogin();
    }

    private void loadLogin() {
        unloadAfterLogout();
    }

    @FXML
    private void initialize(){
        topBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        topBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage primaryStage = getStage();
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    private StaffInfo getInfo(){
        return (StaffInfo) getScreenController().userInfo;
    }

    @Override
    void updateThis() {
        this.logger.debug("Updating Home screen for staff!");
        try{
            Map<String,Integer> map = getService().getCentruHomeScreenData(getInfo().getIdLocatie());
            homeLabelPungiGlobuleRosii.setText(String.valueOf(map.get("pungi_globule_rosii")));
            homeLabelPungiPlasma.setText(String.valueOf(map.get("pungi_plasma")));
            homeLabelPungiTrombocite.setText(String.valueOf(map.get("pungi_trombocite")));
            homeLabelDonariTotal.setText(String.valueOf(map.get("total_donari")));
            homeLabelCereriSange.setText(String.valueOf(map.get("cereri_sange_in_asteptare")));
            homeLabelDonariAsteptare.setText(String.valueOf(map.get("cereri_donari_in_asteptare")));

            Integer trombocite = map.get("pungi_trombocite");
            Integer glob_rosii = map.get("pungi_globule_rosii");
            Integer plasma = map.get("pungi_plasma");
            Integer total = trombocite + glob_rosii + plasma;

            PieChart.Data pie_trombocite = new PieChart.Data("Pungi trombocite",trombocite);

            PieChart.Data pie_glob_rosii = new PieChart.Data("Pungi globule rosii",glob_rosii);

            PieChart.Data pie_plasma = new PieChart.Data("Pungi plasma",plasma);

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    pie_trombocite, pie_glob_rosii, pie_plasma
            );

            stocPieChart.setData(pieChartData);
            pie_trombocite.getNode().setStyle("-fx-background-color: #E64850;");
            pie_glob_rosii.getNode().setStyle("-fx-background-color : #7BB2D9;");
            pie_plasma.getNode().setStyle("-fx-background-color: #F8C537;");

        }catch (Exception ex)
        {
            this.logger.error("FAILED to update Home screen for staff!" + ex.getMessage());
        }
    }

    @Override
    public void setScreenController(ScreenController ctr)
    {
        super.setScreenController(ctr);

        StaffInfo info = (StaffInfo)ctr.userInfo;

        numeStaffLabel.setText(info.getNume());
        numeCentruLabel.setText(info.getNumeLocatie());
    }

    @FXML
    private AnchorPane topBar;

    @FXML
    private FontAwesomeIconView closeIcon;

    private Stage getStage() {
        return (Stage) closeIcon.getScene().getWindow();
    }

    @FXML
    private void closeWindow(){
        Stage current = getStage();
        current.close();
    }


    public void buttonViewDonari(ActionEvent actionEvent) {
        t1.setSelected(false);
        cereriDonariClicked();
    }

    public void buttonViewCereriSange(ActionEvent actionEvent) {
        t1.setSelected(false);
        cereriSangeClicked();
    }

    public void buttonViewDonariAsteptare(ActionEvent actionEvent) {
        t1.setSelected(false);
        cereriDonariClicked();
        CentruCereriDonariController ctr = (CentruCereriDonariController)getScreenController().getControlledScreen(Screen.CENTRU_CERERI_DONARI_SCREEN);
        ctr.statusCombo.getSelectionModel().select("IN_ASTEPTARE");
        ctr.filter();
    }
}
