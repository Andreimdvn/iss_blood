package Controller;

import Service.MainService;
import javafx.fxml.FXML;

public class FormularDonareController {

    private MainService mainService;

    @FXML
    private void initialize(){

    }

    public void setMainService(MainService service) {
        this.mainService = service;
    }
}
