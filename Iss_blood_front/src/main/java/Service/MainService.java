package Service;

import Communication.FlaskClient;
import javafx.util.Pair;

public class MainService {

    FlaskClient flaskClient;

    public MainService(FlaskClient flaskClient) {
        this.flaskClient = flaskClient;
    }

    public Pair<Boolean, String> login(String user, String pass) {
        return flaskClient.login(user, pass);
    }
}
