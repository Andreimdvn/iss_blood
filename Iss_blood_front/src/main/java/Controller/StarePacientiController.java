package Controller;

import Service.MainService;
import javafx.fxml.FXML;

public class StarePacientiController implements ControlledScreensInterface {

    private MainService mainService;
    private ControllerScreens controller;

    @FXML
    private void initialize(){

    }

    public void setMainService(MainService mainService){
        this.mainService = mainService;
    }

    @Override
    public void setScreenParent(ControllerScreens screenParent) {
        this.controller = screenParent;
    }
}
