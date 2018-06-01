package Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class ChatController extends ControlledScreen{

    public void setCnpDestinar(String cnpDestinar) {
        this.cnpDestinar = cnpDestinar;
    }

    private String cnpDestinar;

    private double xOffset;
    private double yOffset;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private void initialize(){
        mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage primaryStage = getStage();
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    @FXML
    private FontAwesomeIconView closeIcon;

    private Stage getStage() {
        return (Stage) closeIcon.getScene().getWindow();
    }
    private void setStyledNodeForMe(Node node){
        node.getStyleClass().add("vboxMesajStanga");
    }

    private void setStyledNodeForHim(Node node)
    {
        node.getStyleClass().add("vboxMesajDreapta");
    }

    @FXML
    private VBox mainChat;

    @FXML
    private void close(){
        getStage().close();
    }

    private AnchorPane createMesaj(){
        AnchorPane anchorPane = new AnchorPane();
        return anchorPane;
    }

    @FXML
    private void sent(){
        Random x = new Random();
        boolean y = x.nextBoolean();
        handleMessage("Pizda lui Alin ",y);

        getService().getActiveUser();

    }
    private void handleMessage(String string, Boolean meSentIt){
        Label l = new Label(string);
        AnchorPane mesaj = createMesaj();
        mesaj.setStyle("-fx-pref-width: 600px");
        mesaj.getChildren().setAll(l);

        if(meSentIt)
            setStyledNodeForMe(l);
        else
        {
            mesaj.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            setStyledNodeForHim(l);
        }

            mainChat.getChildren().add(mesaj);
    }
    @Override
    void updateThis() {
    }



}
