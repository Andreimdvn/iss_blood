package Service;

import Communication.FlaskClient;
import Model.RegisterInfo;
import javafx.util.Pair;

public class MainService {

    FlaskClient flaskClient;

    public MainService(FlaskClient flaskClient) {
        this.flaskClient = flaskClient;
    }

    public Pair<Boolean, String> login(String user, String pass) {
        return flaskClient.login(user, pass);
    }

    public Pair<Boolean, String> register(RegisterInfo info)
    {
        return flaskClient.register(info);
    }

}
