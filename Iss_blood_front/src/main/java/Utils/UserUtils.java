package Utils;

import Model.DonatorInfo;
import Model.MedicInfo;
import Model.StaffInfo;
import Model.UserInfo;
import org.json.JSONObject;

import javax.naming.directory.InvalidAttributesException;

public class UserUtils {
    public static UserInfo GetUserInfoFromResponse (JSONObject loginResponse, String username) throws InvalidAttributesException {
        int type = loginResponse.getInt("user_type");
        UserInfo info = null;
        if(type == 1) //donator
        {
            info = new DonatorInfo(username,
                    loginResponse.getString("nume"),
                    loginResponse.getString("prenume"),
                    loginResponse.getString("cnp"),
                    loginResponse.getString("telefon"),
                    loginResponse.getString("domiciliu_localitate"),
                    loginResponse.getString("domiciliu_judet"),
                    loginResponse.getString("domiciliu_adresa"),
                    loginResponse.getString("resedinta_localitate"),
                    loginResponse.getString("resedinta_judet"),
                    loginResponse.getString("resedinta_adresa")
                    );
        }
        else if(type == 2)
        {
            info = new MedicInfo(username,
                    loginResponse.getString("nume"),
                    loginResponse.getString("prenume"),
                    loginResponse.getInt("id_locatie"));
        }
        else if(type == 3)
        {
            info = new StaffInfo(username,
                    loginResponse.getString("nume"),
                    loginResponse.getString("prenume"),
                    loginResponse.getInt("id_locatie"));
        }
        else
        {
            throw new InvalidAttributesException("loginResponse contine un tip necunoscut. Tipul trebuie sa fie 1/2/3");
        }
        return info;
    }
}
