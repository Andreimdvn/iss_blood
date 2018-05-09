package Controller;

import Service.MainService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class IstoricDonariController implements ControlledScreensInterface{
    private MainService mainService;


    private ControllerScreens controller;

    @Override
    public void setScreenParent(ControllerScreens screenParent) {
        this.controller = screenParent;
    }

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    @FXML
    private VBox tabelDonari;

    private final int MAX_SIZE = 7;

    private boolean isFull(){
        return getSize() == MAX_SIZE;
    }
    private int getSize(){
        return tabelDonari.getChildren().size() - 2;
    }


    int contor = 0;
    @FXML
    private void dummyTest()
    {
        if(!isFull()) {
            contor ++;
            createHBoxIstoric(contor, "01/01/1990",
                    "Nicu camin 16", "Ready to view");
        }
    }
    private void createHBoxIstoric(int numarDonare,String dataDonare,String numeCentru,String status){
        HBox hBox = new HBox();
        String numeStyle = "hboxIstoric";
        hBox.getStyleClass().add(numeStyle);
        Label id = new Label(String.valueOf(numarDonare));
        Label data = new Label(dataDonare);
        Label nume = new Label(numeCentru);
        Label st = new Label(status);


        String numeLabelStyle = "labelIstoric";
        id.getStyleClass().add(numeLabelStyle);
        data.getStyleClass().add(numeLabelStyle);
        nume.getStyleClass().add(numeLabelStyle);
        st.getStyleClass().add(numeLabelStyle);

        id.setPrefWidth(114);
        data.setPrefWidth(114);
        nume.setPrefWidth(225);
        st.setPrefWidth(84);

        hBox.getChildren().addAll(id,data,nume,st);
        tabelDonari.getChildren().add(getSize() + 1,hBox);
    }

    @FXML
    private void initialize(){

    }
}
