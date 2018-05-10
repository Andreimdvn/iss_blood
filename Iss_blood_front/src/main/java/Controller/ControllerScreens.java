package Controller;

import Main.MainApplication;
import Service.MainService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;

public class ControllerScreens extends StackPane {

    HashMap<String, Node> screens = new HashMap<>();

    MainService mainService;

    public ControllerScreens(MainService service) {
        super();
        this.mainService = service;
    }

    /**
     *  Add an entry into hashmap of screens
     *  They are not loaded into scene graph
     * @param name represents the name of screen, they can be found in Utils module, Screen class
     * @param screen represents the node aka screen
     */
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    /**
     *  Return a node from hashmap of screens
     * @param name represents the name of screen returned
     * @return the node of screen
     */
    public Node getScreen(String name) {
        return screens.get(name);
    }

    /**
     *  Load a fxml resource and add the screen into hashmap of screens
     * @param name  represents the name of screen, they can be found in Utils module, Screen class
     * @param fxmlResource represents the name of fxml resource used for screen, they can be found in Utils module, Screen class
     * @return true if the screen was loaded
     *         false if the screen can't be loaded
     */
    public boolean loadScreen(String name, String fxmlResource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlResource));
            Parent loadedScreen = loader.load();
            ControlledScreensInterface controller = loader.getController();
            controller.setScreenParent(this);
            controller.setMainService(mainService);
            addScreen(name, loadedScreen);
            return true;
        } catch (IOException exception){
            exception.printStackTrace();
            return false;
        }
    }

    /**
     *  Set the given screen( given name of the screen) on top of scene graph( load the screen) and unload
     *  the current screen from scene graph (not from hashmap of screens, so the details from the current screen
     *  remains loaded into hashmap)
     * @param name represents the name of screen, they can be found in Utils module, Screen class
     * @return true if the screen was loaded succesfully on scene graph
     *         false if the screen couldn't be loaded on scene graph
     */
    public boolean setScreen(final String name) {
        if(screens.get(name) != null) {
            if(!getChildren().isEmpty()) {
                getChildren().remove(0);
                getChildren().add(0, screens.get(name));
                MainApplication.resizeScreen();
            } else
                getChildren().add(screens.get(name));
            return true;
        } else
            return false;
    }

    /**
     *  Unload the given screen( name of the screen)
     * @param name represents the name of screen, they can be found in Utils module, Screen class
     * @return true if the screen was removed from hashmap of screens
               false if the screen couldn't be removed from hashmap of screens
     */
    public boolean unloadScreen(String name) {
        if(screens.remove(name) != null)
            return false;
        else
            return true;
    }

}
