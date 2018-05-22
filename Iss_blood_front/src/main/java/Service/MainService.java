package Service;

import Communication.FlaskClient;
import Model.FormularDonare;
import Model.RegisterInfo;
import Model.UserInfo;
import javafx.util.Pair;

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

    public Pair<Boolean, String> trimiteFormularDonare (FormularDonare formular){return flaskClient.trimiteFormularDonare(formular);}
}
