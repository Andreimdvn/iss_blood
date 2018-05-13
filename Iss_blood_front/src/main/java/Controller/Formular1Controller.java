package Controller;

import Service.MainService;
import Utils.Screen;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class Formular1Controller extends ControlledScreen {
    List<Node> text_panes = new ArrayList<>();
    public int currentScreen = 0;

    @FXML
    JFXButton button_next;
    @FXML
    JFXButton button_inapoi;

    @FXML
    AnchorPane pane_conditii;

    @FXML
    private void initialize(){
    }

    /**
     * Stores the information text panes in an array
     */
    public void loadTextScreens(){
        text_panes.clear();
        text_panes.add(getScreenController().getScreen(Screen.FORMULAR_1_TEXT1_SCREEN));
        text_panes.add(getScreenController().getScreen(Screen.FORMULAR_1_TEXT2_SCREEN));
        text_panes.add(getScreenController().getScreen(Screen.FORMULAR_1_TEXT3_SCREEN));
        text_panes.add(getScreenController().getScreen(Screen.FORMULAR_1_TEXT4_SCREEN));
    }

    /**
     * Displays the nth text screen, if it exists
     */
    public void displayCurrentScreen(){
        if(currentScreen == 0)
            button_inapoi.setVisible(false);
        else
            button_inapoi.setVisible(true);
        pane_conditii.getChildren().clear();
        pane_conditii.getChildren().add(text_panes.get(currentScreen));
    }

    /**
     *  Displays the next informative screen
     */
    public void nextScreen(){
        if(currentScreen<text_panes.size()-1)
        {
            currentScreen++;
            displayCurrentScreen();
        }
    }

    /**
     * Displays the previous informative screen
     */
    public void previousScreen(){
        if(currentScreen>0)
        {
            currentScreen--;
            displayCurrentScreen();
        }
    }
}
