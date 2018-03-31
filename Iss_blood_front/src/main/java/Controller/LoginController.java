package Controller;

import Services.MainService;
import javafx.fxml.FXML;

public class LoginController {

    private MainService mainService;

    /***
     *
     * @param mainService
     */
    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    @FXML
    void initialize(){

    }

}
