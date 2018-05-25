package Controller;

import Service.MainService;
import Utils.Observer;
import javafx.scene.Node;
import javafx.scene.Parent;


public class ControlledScreen implements Observer {
    private MainService service;
    private ScreenController screenController;
    /**
     * Method called after each logout for each controlled screen in the application
     */
    public void reset_view(){}


    /**
     * Method called after login for each controlled screen in the application
     */
    public void loadAfterLogin(){

    }
    /**
     * Method called when an update notification was received,
     * for each controlled screen in the application
     */
    @Override
    public void update() {

    }



    public MainService getService() {
        return service;
    }

    public void setService(MainService service) {
        this.service = service;
    }

    public ScreenController getScreenController() {
        return screenController;
    }

    public void setScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }

}
