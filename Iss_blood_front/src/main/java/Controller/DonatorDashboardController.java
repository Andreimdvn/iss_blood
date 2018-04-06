package Controller;

import Service.MainService;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DonatorDashboardController {

    private MainService mainService;

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    private ArrayList<Label> formularLabel = new ArrayList<>();
    private ArrayList<Label> istoricLabel = new ArrayList<>();

    private boolean formularLoaded = false;
    private boolean istoricLoaded = false;

    private boolean isAnyLoaded(){
        return formularLoaded || istoricLoaded;
    }

    @FXML
    private BorderPane borderPane;

    @FXML
    private AnchorPane bottomPane;

    @FXML
    private AnchorPane centerPane;

    @FXML
    private HBox dashboardHBox;
    @FXML
    private VBox formularVBox;

    @FXML
    private VBox istoricVBox;

    @FXML
    private ToggleButton formularToggleButton;

    @FXML
    private ToggleButton istoricToggleButton;

    @FXML
    private FontAwesomeIconView closeIcon;

    private AnchorPane auxPane;

    private double prefYHBox;
    private double animationSpeed;

    @FXML
    private boolean isAnySelected(){
        return formularToggleButton.isSelected() || istoricToggleButton.isSelected();
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

    private void styleRemoveAndAddDashboard(String removedStyle,String addedStyle)
    {
        styleRemove(formularVBox,removedStyle);
        styleAdd(formularVBox,addedStyle);
        styleRemove(istoricVBox,removedStyle);
        styleAdd(istoricVBox,addedStyle);

    }

    private void styleAdd(Node obiect,String style){
        obiect.getStyleClass().add(style);
    }

    private void styleRemove(Node obiect,String style){
        obiect.getStyleClass().remove(style);
    }

    private boolean okStyle = false;

    @FXML
    private void animationDashboardButtons(){
        mouseLeftFormularContainer();
        mouseLeftIstoricContainer();

        if(this.isAnySelected() && this.isAnyLoaded())
        {
         loadBottomPane();
        }
        else if(this.isAnySelected() && !okStyle) {
            moveCenterToTop();
        }
        else if(!this.isAnySelected() && okStyle) {
            unloadFormular();
            moveTopToCenter();
            istoricLoaded = false;
            formularLoaded = false;
        }
    }

    private void showTopBar()
    {
        if(!navSegmentHbox.getChildren().contains(formularToggleButton)
                && !navSegmentHbox.getChildren().contains(istoricToggleButton)
                )
        navSegmentHbox.getChildren().addAll(formularToggleButton,istoricToggleButton);
    }
    private void hideTopBar(){
        navSegmentHbox.getChildren().removeAll(formularToggleButton,istoricToggleButton);
    }

    private void showDashboard(){
        if(!bottomPane.getChildren().contains(dashboardHBox))
        bottomPane.getChildren().add(dashboardHBox);

    }
    private void hideDashboard(){
        if(bottomPane.getChildren().contains(dashboardHBox))
            bottomPane.getChildren().remove(dashboardHBox);
    }
    @FXML
    private void moveCenterToTop(){

        hideStats();
        hideDashboard();
        topPaneGoUp();


    }
    private void topPaneGoUp()
    {
        Timeline timeline1 = new Timeline();
        double pref = 10d;
        timeline1.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(animationSpeed),
                        new KeyValue(centerPane.prefHeightProperty(), pref))
        );
        timeline1.play();
        timeline1.setOnFinished(
                x->
                {
                    if(!okStyle) {
                        styleRemoveAndAddDashboard
                                ("vboxButtonDashBoard", "vboxButtonDashBoardSelected");
                        okStyle=true;
                        ok = false;
                    }
                    selectedButtonLoad();
                }
        );
    }

    private void moveTopToCenter(){

        Timeline timeline = new Timeline();
        double pref = prefYHBox;

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(animationSpeed), new KeyValue(dashboardHBox.layoutYProperty(), pref))
        );
        timeline.play();
        timeline.setOnFinished(
                x->
                {
                    showStats();
                    moveBottomDown();
                }
        );
    }
    boolean ok = true;
    private void selectedButtonLoad(){
        if(!isAnyLoaded()) {
            moveBottomToTop();
        }

    }
    private void loadBottomPane()
    {

        if(formularToggleButton.isSelected() && !formularLoaded)
        {
            istoricLoaded = false;
            formularLoaded = true;
            showTopBar();
            loadFormular();
        }
        else if(istoricToggleButton.isSelected() && !istoricLoaded)
        {
            formularLoaded = false;
            istoricLoaded = true;
            showTopBar();
            loadIstoric();
        }

    }

    private void fadeIn(Node node){
        {
            FadeTransition ft = new FadeTransition(Duration.millis(1000), node);
            ft.setFromValue(0.1);
            ft.setToValue(1.0);
            ft.play();
        }
    }

    private void loadIstoric() {


        borderPane.setBottom(istoricRoot);
        fadeIn(istoricRoot);

    }

    private void loadFormular() {
        borderPane.setBottom(formularRoot);
    }

    private void preloadFormular(){
        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/FormularDonareView.fxml"));
        MainService service = new MainService();
        try {
            AnchorPane root = loader.load();
            FormularDonareController loginController = new FormularDonareController();
            loginController.setMainService(service);
            formularRoot = root;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void unloadFormular(){

        emptyPane.setPrefHeight(borderPane.getHeight());
        borderPane.setBottom(emptyPane);
    }



    private void setBottomAfterMovement(){


        bottomPane.setPrefHeight(prefBottomPaneHeight);
        borderPane.setBottom(bottomPane);
        this.showDashboard();

        formularVBox.getChildren().add(0,formularToggleButton);
        istoricVBox.getChildren().add(0,istoricToggleButton);

        styleRemoveAndAddDashboard
                ("vboxButtonDashBoardSelected", "vboxButtonDashBoard");

        okStyle = false;
    }
    private AnchorPane istoricRoot;
    private AnchorPane formularRoot;

    private void preloadIstoric(){
        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/IstoricDonariView.fxml"));
        MainService service = new MainService();
        try {
            AnchorPane root = loader.load();
            IstoricDonariController loginController = new IstoricDonariController();
            loginController.setMainService(service);

            istoricRoot = root;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveBottomDown()
    {
        hideTopBar();
        Timeline timeline = new Timeline();
        System.out.println(prefBottomPaneHeight);
        double pref = prefBottomPaneHeight;
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(animationSpeed),
                        new KeyValue(emptyPane.prefHeightProperty()
                                , pref))
        );
        timeline.play();
        timeline.setOnFinished(
                x-> {
                    setBottomAfterMovement();
                }
        );
    }
    @FXML
    private AnchorPane navBar;

    @FXML
    private HBox navSegmentHbox;


    private void moveBottomToTop(){

        Timeline timeline = new Timeline();
        double pref = borderPane.getPrefHeight() - this.navBar.getHeight();

        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(animationSpeed),
                        new KeyValue(bottomPane.prefHeightProperty()
                                , pref))
        );
        timeline.play();
        timeline.setOnFinished( x-> loadBottomPane());
    }

    double xOffset;
    double yOffset;
    double prefBottomPaneHeight;
    private AnchorPane emptyPane;
    @FXML
    private void initialize(){
        prefYHBox = dashboardHBox.getLayoutY();
        prefBottomPaneHeight = ((AnchorPane) borderPane.getBottom()).getPrefHeight();
        emptyPane = new AnchorPane();
        emptyPane.setStyle("-fx-background-color: white");
        animationSpeed = 200.0d;
        preloadFormular();
        preloadIstoric();

        formularVBox.getChildren().forEach(
                x -> {
                    if( x instanceof Label )
                        formularLabel.add((Label)x);
                }
        );

        istoricVBox.getChildren().forEach(
                x -> {
                    if( x instanceof Label )
                        istoricLabel.add((Label)x);
                }
        );

        borderPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        borderPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage primaryStage = getStage();
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        mouseLeftFormularContainer();
        mouseLeftIstoricContainer();
    }

    @FXML
    private Label ultimaDataDonare;

    @FXML
    private Label urmatoareDonatie;

    @FXML
    private Label donariAnCurent;

    @FXML
    private Label donariRamaseAnCurent;

    @FXML
    private AnchorPane statsPane;

    private void showStats()
    {

        AnchorPane center = (AnchorPane) borderPane.getCenter();
        if(!center.getChildren().contains(statsPane) && !isAnyLoaded())
            center.getChildren().add(statsPane);

    }

    private void hideStats(){
        AnchorPane center = (AnchorPane) borderPane.getCenter();
        if(center.getChildren().contains(statsPane))
            center.getChildren().remove(statsPane);
    }

    private Stage getStage()
    {
        return (Stage) closeIcon.getScene().getWindow();
    }

    @FXML
    private void closeWindow(){
        Stage current = getStage();
        current.close();
    }
    @FXML
    private void logout(){
        Stage current = getStage();
        current.close();
        loadLogin();
    }

    private void loadLogin() {
        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/LoginView.fxml"));
        MainService service = new MainService();
        try {
            Parent root = loader.load();
            Stage primaryStage = getStage();
            LoginController loginController = new LoginController();
            loginController.setMainService(service);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
