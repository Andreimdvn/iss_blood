package Main;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PreloaderApp  extends Preloader {

    private Scene preloaderScene;
    private Stage preloaderStage;

    @Override
    public void init() throws Exception {
        super.init();
        ImageView imageView = new ImageView(new Image("../resources/CG_Heart.gif"));
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(imageView);
        //borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(23,32,39), CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setBackground(Background.EMPTY);
        preloaderScene = new Scene(borderPane ,800,600);
        preloaderScene.setFill(Color.TRANSPARENT);
    }

    @Override
    public void start(Stage primaryStage) {
        preloaderStage = primaryStage;
        primaryStage.setScene(preloaderScene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {
        // Handle state change notifications.
        Preloader.StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_LOAD:
                // Called after MyPreloader#start is called.
                System.out.println("BEFORE_LOAD");
                break;
            case BEFORE_INIT:
                // Called before MyApplication#init is called.
                System.out.println("BEFORE_INIT");
                break;
            case BEFORE_START:
                // Called after MyApplication#init and before MyApplication#start is called.
                System.out.println("BEFORE_START");
                preloaderStage.close();
                break;
        }
    }
}
