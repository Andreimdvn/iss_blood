package Controller;

import Service.MainService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CentruTransfuzieController implements ControlledScreensInterface {


    private MainService mainService;
    private ControllerScreens controller;
    @FXML
    private PieChart stocPieChart;
    private double xOffset;
    private double yOffset;


    public void setMainService(MainService mainService){
        this.mainService = mainService;
    }

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane homePane;

    @FXML
    private void homeClicked(){
        borderPane.setCenter(homePane);
    }
    @FXML
    private void cereriDonariClicked(){
        borderPane.setCenter(controller.getScreen("CENTRU_CERERI_DONARI"));
    }

    @FXML
    private void stocCurentClicked(){
        borderPane.setCenter(controller.getScreen("CENTRU_STOC_PUNGI"));
    }

    @FXML
    private void cereriSangeClicked(){
        borderPane.setCenter(controller.getScreen("CENTRU_CERERI_SANGE"));
    }

    @FXML
    private void initialize(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Pungi trombocite",61),
                new PieChart.Data("Pungi globule rosii",12),
                new PieChart.Data("Pungi plasma",7),
                new PieChart.Data("Pungi sange",32)
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

    @Override
    public void setScreenParent(ControllerScreens screenParent) {
        this.controller = screenParent;
    }
}
