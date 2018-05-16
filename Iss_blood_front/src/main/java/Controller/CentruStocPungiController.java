package Controller;

import Service.MainService;
import javafx.fxml.FXML;

public class CentruStocPungiController implements ControlledScreensInterface {

    private MainService mainService;
    private ControllerScreens controller;

    public void setMainService(MainService mainService){
        this.mainService = mainService;
    }

    @FXML
    private void initialize(){

    }

    @Override
    public void setScreenParent(ControllerScreens screenParent) {
        this.controller = screenParent;
    }
}
