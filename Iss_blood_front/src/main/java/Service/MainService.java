package Service;

import javafx.util.Pair;

public class MainService {

    public Pair<Boolean, String> login(String user, String pass)
    {
        return new Pair<Boolean, String>(true, "Success!");
    }
}
