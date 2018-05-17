package Controller;

import Service.MainService;
import Utils.FunctiiUtile;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CentruStocPungiController implements ControlledScreensInterface {

    private MainService mainService;
    private ControllerScreens controller;

    public void setMainService(MainService mainService){
        this.mainService = mainService;
    }

    @FXML
    private Label globuleO1PozitivLabel;

    @FXML
    private Label globuleA2PozitivLabel;

    @FXML
    private Label globuleB3PozitivLabel;

    @FXML
    private Label globuleAB4PozitivLabel;

    @FXML
    private Label globuleO1NegativLabel;

    @FXML
    private Label globuleA2NegativLabel;

    @FXML
    private Label globuleB3NegativLabel;

    @FXML
    private Label globuleAB4NegativLabel;

    @FXML
    private Label plasmaO1PozitivLabel;

    @FXML
    private Label plasmaA2PozitivLabel;

    @FXML
    private Label plasmaB3PozitivLabel;

    @FXML
    private Label plasmaAB4PozitivLabel;

    @FXML
    private Label plasmaO1NegativLabel;

    @FXML
    private Label plasmaA2NegativLabel;

    @FXML
    private Label plasmaB3NegativLabel;

    @FXML
    private Label plasmaAB4NegativLabel;

    @FXML
    private Label trombociteO1PozitivLabel;

    @FXML
    private Label trombociteA2PozitivLabel;

    @FXML
    private Label trombociteB3PozitivLabel;

    @FXML
    private Label trombociteAB4PozitivLabel;

    @FXML
    private Label trombociteO1NegativLabel;

    @FXML
    private Label trombociteA2NegativLabel;

    @FXML
    private Label trombociteB3NegativLabel;

    @FXML
    private Label trombociteAB4NegativLabel;

    @FXML
    private Label totalTrombocite;

    @FXML
    private Label totalPlasma;

    @FXML
    private Label totalGlobule;

    @FXML
    private AnchorPane trombocitePane;
    @FXML
    private AnchorPane globulePane;
    @FXML
    private AnchorPane plasmaPane;


    private int getTotal(AnchorPane pane){
        int total = 0;
        for (Node node : pane.getChildren()) {
            if(node instanceof Label)
            {
                String text = ((Label) node).getText();
                if(FunctiiUtile.isNumeric(text))
                    total += Integer.parseInt(text);
            }
        }
        return total;
    }

    private void calculateTotal(){
            setIntLabel(totalGlobule,getTotal(globulePane));
            setIntLabel(totalPlasma,getTotal(plasmaPane));
            setIntLabel(totalTrombocite,getTotal(trombocitePane));
    }

    static {

    }

    private void setIntLabel(Label label, int numar)
    {
        label.setText(String.valueOf(numar));
    }

    private void updateLabels(){
        setIntLabel(globuleO1PozitivLabel,1);
        setIntLabel(globuleA2PozitivLabel,2);
        setIntLabel(globuleB3PozitivLabel,3);
        setIntLabel(globuleAB4PozitivLabel,4);
        setIntLabel(globuleO1NegativLabel,5);
        setIntLabel(globuleA2NegativLabel,6);
        setIntLabel(globuleB3NegativLabel,7);
        setIntLabel(globuleAB4NegativLabel,8);

        setIntLabel(plasmaO1PozitivLabel,9);
        setIntLabel(plasmaA2PozitivLabel,10);
        setIntLabel(plasmaB3PozitivLabel,11);
        setIntLabel(plasmaAB4PozitivLabel,12);
        setIntLabel(plasmaO1NegativLabel,13);
        setIntLabel(plasmaA2NegativLabel,14);
        setIntLabel(plasmaB3NegativLabel,15);
        setIntLabel(plasmaAB4NegativLabel,16);

        setIntLabel(trombociteO1PozitivLabel,17);
        setIntLabel(trombociteA2PozitivLabel,18);
        setIntLabel(trombociteB3PozitivLabel,19);
        setIntLabel(trombociteAB4PozitivLabel,20);
        setIntLabel(trombociteO1NegativLabel,21);
        setIntLabel(trombociteA2NegativLabel,22);
        setIntLabel(trombociteB3NegativLabel,23);
        setIntLabel(trombociteAB4NegativLabel,24);

        calculateTotal();
    }

    private void update(){
        updateLabels();
    }

    @FXML
    private void initialize(){
        updateLabels();
    }

    @Override
    public void setScreenParent(ControllerScreens screenParent) {
        this.controller = screenParent;
    }
}
