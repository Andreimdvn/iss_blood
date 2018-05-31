package Controller;

import Model.MedicInfo;
import Utils.Screen;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MedicDashboardController extends ControlledScreen {

    @FXML
    private Label fullnameLabel;

    @FXML
    private Label numeSpitalLabel;

    @FXML
    private Button cerereSangeButton;

    @FXML
    private Button istoricCereriButton;

    @FXML
    private Button stareActualaButton;

    @FXML
    private Button logoutButton;

    @FXML
    private AnchorPane leftAnchorPane;

    @FXML
    private AnchorPane meniuPane;

    @FXML
    private AnchorPane burgerPane;

    private double prefBorderPaneWidth;
    private double prefBurgerPaneWidth;
    private double prefLeftPaneWidth;
    private double animationSpeed;

    private Logger logger = LogManager.getLogger(MedicDashboardController.class.getName());

    @FXML
    private void showLeftMenu(){
        logger.debug("Buton show left menu a fost apasat");
        leftAnchorPane.getChildren().remove(burgerPane);
        Timeline timeline = new Timeline();
        double pref = prefLeftPaneWidth;

        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(animationSpeed),
                        new KeyValue(leftAnchorPane.prefWidthProperty()
                                , pref))

        );
        timeline.play();
        timeline.setOnFinished(
                x-> {
                    leftAnchorPane.getChildren().add(meniuPane);
                    resizeCentralPane();
                }
        );
    }

    @FXML
    private BorderPane borderPane;
    @FXML
    private BorderPane centralPane;
    @FXML
    private void CerereSangeClicked(){

        logger.debug("Buton cerere sange a fost apasat");
        centralPane.setCenter(getScreenController().getScreen("CERERE_SANGE"));

        mainPane.setPrefWidth(950);
        //resizeCentralPane();
    }
    @FXML
    private void IstoricCereriClicked(){
        logger.debug("Buton istoric cereri a fost apasat");
        centralPane.setCenter(getScreenController().getScreen("ISTORIC_CERERI"));
        mainPane.setPrefWidth(950);
       // resizeCentralPane();
    }
    @FXML
    private void stareActualaClicked(){
        logger.debug("Buton stare actuala a fost apasat");
        centralPane.setCenter(getScreenController().getScreen("STARE_PACIENTI"));
        mainPane.setPrefWidth(950);
      //  resizeCentralPane();
    }

    @FXML
    private void homeButtonClicked(){
        logger.debug("Buton home a fost apasat");
        centralPane.setCenter(mainPane);
        mainPane.setPrefWidth(950);
      //  resizeCentralPane();
    }

    @FXML
    private void hideLeftMenu(){

        logger.debug("Buton hide left menu a fost apasat");
        leftAnchorPane.getChildren().remove(meniuPane);
        Timeline timeline = new Timeline();
        double pref = prefBurgerPaneWidth;

       // borderPane.getScene().getWindow().setWidth(prefCentral);

        Window a = borderPane.getScene().getWindow();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(animationSpeed),
                        new KeyValue(leftAnchorPane.prefWidthProperty()
                                , pref))

        );
        timeline.play();
        timeline.setOnFinished(
                x-> {
                    leftAnchorPane.getChildren().add(burgerPane);
                    resizeCentralPane();
                }
        );

    }


    private double getLeftSize(){
        return leftAnchorPane.getPrefWidth();
    }

    double prefWindowSize;

    private void resizeCentralPane(){
        Timeline timeline = new Timeline();

        prefWindowSize = mainPane.getPrefWidth() + getLeftSize();

        borderPane.getScene().getWindow().setWidth(prefWindowSize);
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(animationSpeed),
                        new KeyValue(borderPane.prefWidthProperty(),
                                prefWindowSize))
        );
        timeline.play();
        timeline.setOnFinished(
                x-> {
                }
        );
    }
    @FXML
    private AnchorPane mainPane;


    private double xOffset;
    private double yOffset;

    @FXML
    private void initialize(){
        animationSpeed = 200;
        prefLeftPaneWidth = leftAnchorPane.getPrefWidth();
        prefBurgerPaneWidth = burgerPane.getPrefWidth();
        prefBorderPaneWidth = borderPane.getPrefWidth();
        prefWindowSize = prefBorderPaneWidth;
        leftAnchorPane.getChildren().remove(burgerPane);
        mainPane.setPrefWidth(950);

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


    }

    @Override
    void updateThis() {

    }

    @Override
    public void setScreenController(ScreenController ctr)
    {
        super.setScreenController(ctr);

        MedicInfo info = (MedicInfo)ctr.userInfo;

        fullnameLabel.setText(info.getNume() + " " + info.getPrenume());
        numeSpitalLabel.setText("Stoc " + info.getNumeLocatie());
    }

    @FXML
    private void logout(){
        loadLogin();
    }

    private void loadLogin() {
        unloadAfterLogout();
    }

    @FXML
    private FontAwesomeIconView closeIcon;

    private Stage getStage()
    {
        return (Stage) closeIcon.getScene().getWindow();
    }

    @FXML
    private void closeWindow(){
        Stage current = getStage();
        current.close();
    }
}
