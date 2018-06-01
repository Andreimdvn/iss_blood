package Controller;

import Model.DonatorInfo;
import Model.MedicInfo;
import Model.StaffInfo;
import Service.MainService;
import Utils.Observer;
import Utils.Screen;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.ArrayList;


public abstract class ControlledScreen implements Observer {
    private MainService service;
    private ScreenController screenController;

    /**
     * Method called after each logout for each controlled screen in the application
     */
    public void reset_view(){}


    private void loadScreensDonator()
    {
        ScreenController screenController = getScreenController();

        screenController.loadScreen(Screen.DONATOR_SCREEN,Screen.DONATOR_RESOURCE);
        screenController.loadScreen(Screen.FORMULAR_DONARE_SCREEN, Screen.FORMULAR_DONARE_RESOURCE);
        screenController.loadScreen(Screen.ISTORIC_DONARI_SCREEN,Screen.ISTORIC_DONARI_RESOURCE);

        screenController.loadScreen(Screen.FORMULAR_1_TEXT1_SCREEN,Screen.FORMULAR_1_TEXT1_RESOURCE);
        screenController.loadScreen(Screen.FORMULAR_1_TEXT2_SCREEN,Screen.FORMULAR_1_TEXT2_RESOURCE);
        screenController.loadScreen(Screen.FORMULAR_1_TEXT3_SCREEN,Screen.FORMULAR_1_TEXT3_RESOURCE);
        screenController.loadScreen(Screen.FORMULAR_1_TEXT4_SCREEN,Screen.FORMULAR_1_TEXT4_RESOURCE);
        screenController.loadScreen(Screen.FORMULAR_1_SCREEN,Screen.FORMULAR_1_RESOURCE);
        screenController.loadScreen(Screen.FORMULAR_3_SCREEN,Screen.FORMULAR_3_RESOURCE);
        screenController.setScreen(Screen.DONATOR_SCREEN);
    }

    private void loadScreensMedic()
    {
        ScreenController screenController = getScreenController();

        screenController.loadScreen(Screen.MEDIC_SCREEN,Screen.MEDIC_RESOURCE);
        screenController.loadScreen(Screen.ISTORIC_CERERI_SCREEN,Screen.ISTORIC_CERERI_RESOURCE);
        screenController.loadScreen(Screen.STARE_PACIENTI_SCREEN,Screen.STARE_PACIENTI_RESOURCE);
        screenController.loadScreen(Screen.CERERE_SANGE_SCREEN,Screen.CERERE_SANGE_RESOURCE);
        screenController.setScreen(Screen.MEDIC_SCREEN);

    }

    private void loadScreensCentru()
    {
        ScreenController screenController = getScreenController();

        screenController.loadScreen(Screen.CENTRU_TRANSFUZIE_SCREEN,Screen.CENTRU_TRANSFUZIE_RESOURCE);
        screenController.loadScreen(Screen.CENTRU_CERERI_DONARI_SCREEN,Screen.CENTRU_CERERI_DONARI_RESOURCE);
        screenController.loadScreen(Screen.CENTRU_ANALIZA_SCREEN,Screen.CENTRU_ANALIZA_RESOURCE);
        screenController.loadScreen(Screen.CENTRU_CHESTIONAR_SCREEN,Screen.CENTRU_CHESTIONAR_RESOURCE);
        screenController.loadScreen(Screen.CENTRU_CERERI_SANGE_SCREEN,Screen.CENTRU_CERERI_SANGE_RESOURCE);
        screenController.loadScreen(Screen.CENTRU_STOC_PUNGI_SCREEN,Screen.CENTRU_STOC_PUNGI_RESOURCE);
        screenController.loadScreen(Screen.FORMULAR_DONARE_SCREEN, Screen.CENTRU_FORMULAR_RESOURCE);
        screenController.setScreen(Screen.CENTRU_TRANSFUZIE_SCREEN);
    }

    /**
     * Method called after login for each controlled screen in the application
     */
    protected void loadAfterLogin(){
        if(screenController.userInfo instanceof MedicInfo)
            loadScreensMedic();
        else if(screenController.userInfo instanceof StaffInfo)
            loadScreensCentru();
        else if(screenController.userInfo instanceof DonatorInfo)
            loadScreensDonator();
        unloadLoginRegister();
    }
    
    private void unloadLoginRegister() {
        screenController.unloadScreen(Screen.LOGIN_SCREEN);
        screenController.unloadScreen(Screen.REGISTER_SCREEN);
    }

    private void loadLoginRegister() {
        screenController.loadScreen(Screen.LOGIN_SCREEN, Screen.LOGIN_RESOURCE);
        screenController.loadScreen(Screen.REGISTER_SCREEN, Screen.REGISTER_RESOURCE);
        screenController.setScreen(Screen.LOGIN_SCREEN);
    }

    protected void unloadAfterLogout(){
        if(screenController.userInfo instanceof MedicInfo)
            unloadScreensMedic();
        else if(screenController.userInfo instanceof StaffInfo)
            unloadScreensCentru();
        else if(screenController.userInfo instanceof DonatorInfo)
            unloadScreensDonator();
        loadLoginRegister();
    }

    private void unloadScreensDonator()
    {
        ScreenController screenController = getScreenController();

        screenController.unloadScreen(Screen.DONATOR_SCREEN);
        screenController.unloadScreen(Screen.FORMULAR_DONARE_SCREEN);
        screenController.unloadScreen(Screen.ISTORIC_DONARI_SCREEN);
        screenController.unloadScreen(Screen.FORMULAR_1_TEXT1_SCREEN);
        screenController.unloadScreen(Screen.FORMULAR_1_TEXT2_SCREEN);
        screenController.unloadScreen(Screen.FORMULAR_1_TEXT3_SCREEN);
        screenController.unloadScreen(Screen.FORMULAR_1_TEXT4_SCREEN);
        screenController.unloadScreen(Screen.FORMULAR_1_SCREEN);
        screenController.unloadScreen(Screen.FORMULAR_3_SCREEN);
    }

    private void unloadScreensMedic()
    {
        ScreenController screenController = getScreenController();

        screenController.unloadScreen(Screen.MEDIC_SCREEN);
        screenController.unloadScreen(Screen.ISTORIC_CERERI_SCREEN);
        screenController.unloadScreen(Screen.STARE_PACIENTI_SCREEN);
        screenController.unloadScreen(Screen.CERERE_SANGE_SCREEN);
    }

    private void unloadScreensCentru()
    {
        ScreenController screenController = getScreenController();

        screenController.unloadScreen(Screen.CENTRU_TRANSFUZIE_SCREEN);
        screenController.unloadScreen(Screen.CENTRU_CERERI_DONARI_SCREEN);
        screenController.unloadScreen(Screen.CENTRU_ANALIZA_SCREEN);
        screenController.unloadScreen(Screen.CENTRU_CHESTIONAR_SCREEN);
        screenController.unloadScreen(Screen.CENTRU_CERERI_SANGE_SCREEN);
        screenController.unloadScreen(Screen.CENTRU_STOC_PUNGI_SCREEN);
        screenController.unloadScreen(Screen.FORMULAR_DONARE_SCREEN);

    }

    /**
     * Method called when an update notification was received,
     * for each controlled screen in the application
     */
    @Override
    public void update() {
        getScreenController().getAllScreens().forEach(
                x -> {
                    x.getControlledScreen().updateThis();
                }
        );
    }

    abstract void updateThis();

    public MainService getService() {
        return service;
    }

    public void setService(MainService service) {
        this.service = service;
        this.service.addObserver(this);

    }

    public ScreenController getScreenController() {
        return screenController;
    }

    public void setScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }

}
