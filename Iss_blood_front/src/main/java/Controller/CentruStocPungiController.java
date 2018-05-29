package Controller;

import Model.StaffInfo;
import Service.MainService;
import Utils.FunctiiUtile;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.List;
import java.util.Map;

public class CentruStocPungiController extends ControlledScreen {


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

    private StaffInfo getInfo(){
        return (StaffInfo) getScreenController().userInfo;
    }

    void updateThis(){
        updateLabels();
    }

    @FXML
    private void updateLabels(){
        // Globule 2, tromobicite 1 , plasma 0
        Map<String,List<Integer>> map = getService().getStocCurent(getInfo().getIdLocatie());
        setIntLabel(globuleO1PozitivLabel,map.get("O1_pozitiv").get(2));
        setIntLabel(globuleA2PozitivLabel,map.get("A2_pozitiv").get(2));
        setIntLabel(globuleB3PozitivLabel,map.get("B3_pozitiv").get(2));
        setIntLabel(globuleAB4PozitivLabel,map.get("AB4_pozitiv").get(2));
        setIntLabel(globuleO1NegativLabel,map.get("O1_negativ").get(2));
        setIntLabel(globuleA2NegativLabel,map.get("A2_negativ").get(2));
        setIntLabel(globuleB3NegativLabel,map.get("B3_negativ").get(2));
        setIntLabel(globuleAB4NegativLabel,map.get("AB4_negativ").get(2));

        setIntLabel(plasmaO1PozitivLabel,map.get("O1_pozitiv").get(0));
        setIntLabel(plasmaA2PozitivLabel,map.get("A2_pozitiv").get(0));
        setIntLabel(plasmaB3PozitivLabel,map.get("B3_pozitiv").get(0));
        setIntLabel(plasmaAB4PozitivLabel,map.get("AB4_pozitiv").get(0));
        setIntLabel(plasmaO1NegativLabel,map.get("O1_negativ").get(0));
        setIntLabel(plasmaA2NegativLabel,map.get("A2_negativ").get(0));
        setIntLabel(plasmaB3NegativLabel,map.get("B3_negativ").get(0));
        setIntLabel(plasmaAB4NegativLabel,map.get("AB4_negativ").get(0));

        setIntLabel(trombociteO1PozitivLabel,map.get("O1_pozitiv").get(1));
        setIntLabel(trombociteA2PozitivLabel,map.get("A2_pozitiv").get(1));
        setIntLabel(trombociteB3PozitivLabel,map.get("B3_pozitiv").get(1));
        setIntLabel(trombociteAB4PozitivLabel,map.get("AB4_pozitiv").get(1));
        setIntLabel(trombociteO1NegativLabel,map.get("O1_negativ").get(1));
        setIntLabel(trombociteA2NegativLabel,map.get("A2_negativ").get(1));
        setIntLabel(trombociteB3NegativLabel,map.get("B3_negativ").get(1));
        setIntLabel(trombociteAB4NegativLabel,map.get("AB4_negativ").get(1));

        calculateTotal();
    }

    @FXML
    private void initialize(){

    }

}
