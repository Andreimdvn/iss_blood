package Service;

import Communication.FlaskClient;
import Model.FormularDonare;
import Model.RegisterInfo;
import Model.UserInfo;
import javafx.util.Pair;

import java.util.List;

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
}
