package Controller;

import Service.MainService;
import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

public class DonatorDashboardController {

    private MainService mainService;

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    private ArrayList<Label> formularLabel = new ArrayList<>();
    private ArrayList<Label> istoricLabel = new ArrayList<>();
    private ArrayList<Label> anunturiLabel = new ArrayList<>();

    @FXML
    private HBox dashboardHBox;
    @FXML
    private VBox formularVBox;

    @FXML
    private VBox istoricVBox;

    @FXML
    private VBox anunturiVBox;

    @FXML
    private ToggleButton formularToggleButton;

    @FXML
    private ToggleButton istoricToggleButton;

    @FXML
    private ToggleButton anunturiToggleButton;

    private double prefYHBox;

    @FXML
    private boolean isAnySelected(){
        return formularToggleButton.isSelected() || istoricToggleButton.isSelected()
                || anunturiToggleButton.isSelected();
    }

    @FXML
    private void mouseEnteredFormularContainer(){
        if(!isAnySelected())
        formularLabel.forEach(
                label -> formularVBox.getChildren().add(label)
        );
    }
    @FXML
    private void mouseLeftFormularContainer(){
        formularLabel.forEach(
                label -> formularVBox.getChildren().remove(label)
        );
    }

    @FXML
    private void mouseEnteredAnunturiContainer(){
        if(!isAnySelected())
        anunturiLabel.forEach(
                label -> anunturiVBox.getChildren().add(label)
        );
    }
    @FXML
    private void mouseLeftAnunturiContainer(){
        anunturiLabel.forEach(
                label -> anunturiVBox.getChildren().remove(label)
        );
    }

    @FXML
    private void mouseEnteredIstoricContainer(){
        if(!isAnySelected())
        istoricLabel.forEach(
                label -> istoricVBox.getChildren().add(label)
        );
    }
    @FXML
    private void mouseLeftIstoricContainer(){
        istoricLabel.forEach(
                label -> istoricVBox.getChildren().remove(label)
        );
    }

    @FXML
    private void animationDashboardButtons(){
        if(this.isAnySelected())
            moveCenterToTop();
        else
            moveTopToCenter();
    }

    @FXML
    private void moveCenterToTop(){
        Timeline timeline = new Timeline();
        double pref = 0;
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(50.0d), new KeyValue(dashboardHBox.layoutYProperty(), 0))
        );
        timeline.play();
    }

    private void moveTopToCenter(){
        Timeline timeline = new Timeline();
        double pref = prefYHBox;
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(50.0d), new KeyValue(dashboardHBox.layoutYProperty(), pref))
        );
        timeline.play();
    }

    private void moveBottomToTop(){
        Timeline timeline = new Timeline();
        AnchorPane s = new AnchorPane();
        double pref = prefYHBox;
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(100.0d), new KeyValue(dashboardHBox.layoutYProperty(), pref))
        );
        timeline.play();
    }

    @FXML
    private void initialize(){

        prefYHBox = dashboardHBox.getLayoutY();

        formularVBox.getChildren().forEach(
                x -> {
                    if( x instanceof Label )
                        formularLabel.add((Label)x);
                }
        );

        anunturiVBox.getChildren().forEach(
                x -> {
                    if( x instanceof Label )
                        anunturiLabel.add((Label)x);
                }
        );

        istoricVBox.getChildren().forEach(
                x -> {
                    if( x instanceof Label )
                        istoricLabel.add((Label)x);
                }
        );


        mouseLeftFormularContainer();
        mouseLeftAnunturiContainer();
        mouseLeftIstoricContainer();
    }

}
