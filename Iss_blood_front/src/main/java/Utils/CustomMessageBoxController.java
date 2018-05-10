package Utils;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.octicons.OctIconView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CustomMessageBoxController {

    String title;
    String message;
    Type type;

    @FXML
    private Label titleLabel;

    @FXML
    private Label textLabel;

    public enum Type{
        ALERT,INFO
    }

    void setTitle(String title){
        this.title = title;
    }

    void setMessage(String message) {
        this.message = message;
    }

    void setType(Type type){
        this.type = type;
    }

    @FXML
    private OctIconView iconita;

    private void styleStop(){
        iconita.setGlyphName("STOP");
        iconita.setStyle("-fx-fill: indianred");
    }

    private void styleInfo(){
        iconita.setGlyphName("INFO");
        iconita.setStyle("-fx-fill: lightskyblue");
    }

    @FXML
    private void close(){
        getStage().close();
    }
    void createMessageBox(){

        if(type == Type.ALERT)
            styleStop();
        else
            styleInfo();

        titleLabel.setText(title);
        textLabel.setText(message);
    }

    private Stage getStage()
    {
        return (Stage) iconita.getScene().getWindow();
    }

    @FXML
    AnchorPane pane;
    private double xOffset;
    private double yOffset;
    @FXML
    private void initialize(){
        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage primaryStage = getStage();
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
    }
}
