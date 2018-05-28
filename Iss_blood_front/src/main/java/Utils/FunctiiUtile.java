package Utils;

import Controller.CentruTransfuzieController;
import Controller.ControlledScreen;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class FunctiiUtile {

    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

}
