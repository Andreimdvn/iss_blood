package Controller;

import Main.MainApplication;
import Model.UserInfo;
import Service.MainService;
import Utils.ViewDetails;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

/**
 * Controller manager class. Used as a bridge between controllers.
 */
public class ScreenController extends StackPane {

    private HashMap<String, ViewDetails> screenViews = new HashMap<>();
    MainService mainService;
    UserInfo userInfo;

    public ScreenController(MainService service) {
        super();
        this.mainService = service;
    }

    /**
     * Returns the controller for a screen id
     * @param screen_id
     * @return
     */
    public ControlledScreen getControlledScreen(String screen_id) {
        return screenViews.get(screen_id).getControlledScreen();
    }
    /**
     *  Add an entry into hashmap of screenViews
     *  They are not loaded into scene graph
     * @param name represents the name of screen, they can be found in Utils module, Screen class
     * @param screenDetails
     */
    public void addScreen(String name, ViewDetails screenDetails) {
        screenViews.put(name, screenDetails);
    }

    /**
     *  Return the node associated with a screen_id
     * @param screen_id represents the name of screen returned
     * @return the node of screen
     */
    public Node getScreen(String screen_id) {
        return screenViews.get(screen_id).getNode();
    }

    /**
     *  Loads a screen and a screen's controller
     * @param name the identifier used to locate the screen
     * @param fxmlResource fxml path for the screen
     * @return true if the screen was loaded
     *         false if the screen can't be loaded
     */
    public boolean loadScreen(String name, String fxmlResource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlResource));
            Parent loadedScreen = loader.load();
            ControlledScreen controller = loader.getController();
            controller.setScreenController(this);
            controller.setService(mainService);
            addScreen(name, new ViewDetails(loadedScreen,controller));
            return true;
        } catch (IOException exception){
            exception.printStackTrace();
            return false;
        }
    }

    /**
     *  Set the given screen( given name of the screen) on top of scene graph( load the screen) and unload
     *  the current screen from scene graph (not from hashmap of screenViews, so the details from the current screen
     *  remains loaded into hashmap)
     * @param name represents the name of screen, they can be found in Utils module, Screen class
     * @return true if the screen was loaded succesfully on scene graph
     *         false if the screen couldn't be loaded on scene graph
     */
    public boolean setScreen(final String name) {
        if(screenViews.get(name) != null) {
            if(!getChildren().isEmpty()) {
                getChildren().remove(0);
                getChildren().add(0, screenViews.get(name).getNode());
                MainApplication.resizeScreen();
            } else
                getChildren().add(screenViews.get(name).getNode());
            return true;
        } else
            return false;
    }

    /**
     *  Unload the given screen( name of the screen)
     * @param name represents the name of screen, they can be found in Utils module, Screen class
     * @return true if the screen was removed from hashmap of screenViews
               false if the screen couldn't be removed from hashmap of screenViews
     */
    public boolean unloadScreen(String name) {
        if(screenViews.remove(name) != null)
            return false;
        else
            return true;
    }

}
