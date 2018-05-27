package Controller;

import Model.StaffInfo;
import Service.MainService;
import Utils.Screen;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class CentruTransfuzieController extends ControlledScreen {

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
    private void logout(){
        loadLogin();
    }

    private void loadLogin() {
        getScreenController().setScreen(Screen.LOGIN_SCREEN);
    }

    @FXML
    private void initialize(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Pungi trombocite",61),
                new PieChart.Data("Pungi globule rosii",12),
                new PieChart.Data("Pungi plasma",7)
                );

        stocPieChart.setData(pieChartData);

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

    @Override
    public void setScreenController(ScreenController ctr)
    {
        super.setScreenController(ctr);
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


}
