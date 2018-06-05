package Controller;

import Model.StaffInfo;
import Model.UserInfo;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

public class ChatController extends ControlledScreen {

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


        activeUsers.setItems(map);
    }

    @FXML
    private ListView<Pair<String,String>> activeUsers;

    @FXML
    private FontAwesomeIconView closeIcon;

    private void setStyledNodeForMe(Node node){
        node.getStyleClass().add("vboxMesajStanga");
    }

    private void setStyledNodeForHim(Node node)
    {
        node.getStyleClass().add("vboxMesajDreapta");
    }

    @FXML
    private VBox mainChat;


    private AnchorPane createMesaj(){
        AnchorPane anchorPane = new AnchorPane();
        return anchorPane;
    }

    @FXML
    private JFXTextField messageTextField;
    @FXML
    private void sent(){
        //Random x = new Random();
        //boolean y = x.nextBoolean();
        //handleMessage(messageTextField.getText(),true);
        getService().addMessage(
                sender,
                getScreenController().userInfo.getUsername(),
                messageTextField.getText());
        messageTextField.clear();

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

        Platform.runLater(() -> {
            mainChat.getChildren().add(mesaj);
        });
    }

    ObservableList<Pair<String,String>> map = FXCollections.observableArrayList();

    private void addActiveUsers(){
        //


    }

    @FXML
    Label numeDestinatar;

    @FXML
    private void listSelected() {

        Pair<String,String> x = activeUsers.getSelectionModel().getSelectedItem();

        if(x != null)
        {
                sender = x.getKey();
                numeDestinatar.setText(sender);
                updateThis();
        }
    }

    String sender = null;
    ObservableList<Pair<String,Boolean>> messages = FXCollections.observableArrayList();
    UserInfo userInfo;

    @Override
    void updateThis() {

        userInfo = getScreenController().userInfo;
        map.setAll(getService().getActiveUser(userInfo.getUsername()));

        Platform.runLater(() -> {
            mainChat.getChildren().clear();
        });

        if(sender == null)
            messages.clear();
        else
        {
            messages .setAll(getService().getMessages(userInfo.getUsername(),sender));
            for (Pair<String, Boolean> message : messages) {
                handleMessage(message.getKey(), !message.getValue());
            }
        }
    }
}
