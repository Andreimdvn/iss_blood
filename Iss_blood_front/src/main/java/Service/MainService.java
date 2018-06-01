package Service;

import Communication.FlaskClient;
import Controller.ControlledScreen;
import Model.*;
import Utils.Observer;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public class MainService {

    FlaskClient flaskClient;

    public MainService(FlaskClient flaskClient) {
        this.flaskClient = flaskClient;
    }

    public Pair<UserInfo, String> login(String user, String pass) {
        return flaskClient.login(user, pass);
    }

    public Pair<Boolean, String> register(RegisterInfo info)
    {
        return flaskClient.register(info);
    }

    public Pair<Boolean, String> userTrimiteFormularDonare(FormularDonare formular, String username){return flaskClient.userTrimiteFormularDonare(formular, username);}

    public Pair<Boolean, String> staffTrimiteFormularDonare (FormularDonare formularDonare) { return flaskClient.staffTrimiteFormularDonare(formularDonare);}

    public List<FormularDonare> getFormulareDonariDupaLocatie(int i){
        return flaskClient.getFormulareDonariDupaLocatie(i);

    }
    public Pair<Boolean, String> staffUpdateFormularDonare(FormularDonare formularDonare,int id_locatie){
        return flaskClient.staffUpdateFormularDonare(formularDonare,id_locatie);
    }

    public void staffTrimiteAnaliza(Integer idLocatie, FormularDonare cerereDonare, Analiza analiza) {
        flaskClient.staffTrimiteAnaliza(cerereDonare,analiza,idLocatie);
    }

    public Map<String, List<Integer>> getStocCurent(int idLocatie){
        return flaskClient.getStocCurent(idLocatie);
    }

    public List<Analiza> getAnalize(String cnp){
        return flaskClient.getAnalize(cnp);
    }

    public Pair<Boolean, String> trimitePungi(int idCerere, int idLocatie,
                                              GrupaSange grupaSange, RH rh, int plasma, int trombocite, int globule){
        return flaskClient.trimitePungi(idCerere,  idLocatie, grupaSange, rh, plasma,trombocite, globule);

    }

    public void addObserver(Observer controlledScreen) {
        flaskClient.addObserver(controlledScreen);
    }

    public List<CerereSange> getCereriSange(int id_locatie,String status,boolean fromSpital)
    {
     return flaskClient.getCereriSange(id_locatie,status,fromSpital);
    }

    public Pair<Boolean, String> trimiteCerereSange(CerereSange cerere, String cnpMedic) {
        return flaskClient.trimiteCerereSange(cerere, cnpMedic);
    }

    public void anulare(Integer id) {
        flaskClient.anulareCerere(id);
    }
}
