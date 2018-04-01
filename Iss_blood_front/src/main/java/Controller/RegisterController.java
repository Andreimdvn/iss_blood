package Controller;

import Services.MainService;
import javafx.fxml.FXML;

public class RegisterController {

    private MainService mainService;

    /***
     *
     * @param mainService
     */
    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    @FXML
    private void initialize(){

    }
}
