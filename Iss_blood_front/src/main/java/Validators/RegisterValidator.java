package Validators;

import Model.RegisterInfo;
import javafx.util.Pair;

public class RegisterValidator {

    /**
     * @param info - the RegisterInfo to be validated
     * @return True if the info is valid, false otherwise
     */
    public Pair<Boolean, String> Validate(RegisterInfo info)
    {
        return new Pair<>(true, "ok");
//        if(info.getAddress().equals("") || info.getEmail().equals("") || info.getFullname().equals("") ||
//                info.getPassword().equals("") || info.getPhone().equals("") || info.getUsername().equals(""))
//            return false;
//
//        if(info.getPhone().length() != 10)
//            return false;
//
//        for(char c : info.getPhone().toCharArray())
//            if(c < '0' || c > '9')
//                return false;
//
//        return true;
    }
}
