package Controller;

import Service.MainService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class CentruTransfuzieController implements ControlledScreensInterface {


    private MainService mainService;
    private ControllerScreens controller;
    @FXML
    private PieChart stocPieChart;


    public void setMainService(MainService mainService){
        this.mainService = mainService;
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
    }


    @Override
    public void setScreenParent(ControllerScreens screenParent) {
        this.controller = screenParent;
    }
}
