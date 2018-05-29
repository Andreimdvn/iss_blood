package Communication;

import Model.*;
import Utils.UserUtils;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.naming.directory.InvalidAttributesException;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FlaskClient {

    private String urlRoot;


    private Logger logger = LogManager.getLogger(FlaskClient.class.getName());

    public FlaskClient(Properties properties) {
        this.urlRoot = "http://"+properties.getProperty("serverIp")+":"+properties.getProperty("serverPort");
    }

    /**
     * Creates a http connection using the given path
     * @param urlRelativePath: String containing the type of request, for example /login
     * @return HttpURLConnection that will be used to send json to the server
     */
    private HttpURLConnection getConnection(String urlRelativePath) {
        try {
            URL url = new URL(this.urlRoot + urlRelativePath);
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("POST");
            http.setDoOutput(true);

            return http;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param http : HttpURLConnection returned by getConnection method
     * @param jsonString : String in json format to be sent to the server
     * @return JsonObject returned by the server
     */
    private JSONObject sendRequest(HttpURLConnection http, String jsonString){
        byte[] outJson = jsonString.getBytes(StandardCharsets.UTF_8);
        int length = outJson.length;

        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        try {
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(outJson);
            }
            InputStream inputStream = http.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            return new JSONObject(responseStrBuilder.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sends a login request to the server
     * @param user : Username for login
     * @param password : Password for login
     * @return <UserInfo,string> If the login request was unsuccessful,
     *        UserInfo will be set to null and the string will contain an error message
     * + a string message describing the status
     */
    public Pair<UserInfo, String> login(String user, String password){

        HttpURLConnection http = getConnection("/login");

        if(http == null){
            return new Pair<>(null, "Client connection request Error");
        }

        String jsonString = new JSONObject().put("username", user).put("password", password).toString();
        logger.debug("SENDING: " + jsonString);
        JSONObject jsonResponse = this.sendRequest(http, jsonString);
        logger.debug("RESPONSE : " + jsonResponse);
        if(jsonResponse == null) {
            return new Pair<>(null, "Connection error.");
        }
        if (jsonResponse.getInt("status")== 0) {
            try {
                return new Pair<>(UserUtils.GetUserInfoFromResponse(jsonResponse, user), "Success!");
            } catch (InvalidAttributesException e) {
                return new Pair<>(null, e.getMessage());
            }
        }
        else {
            return new Pair<>(null, jsonResponse.getString("message"));
        }
    }

    public Pair<Boolean, String> register(RegisterInfo info)
    {
        HttpURLConnection connection = getConnection("/register");

        if(connection == null)
            return new Pair<>(false, "Client connection request Error");

        String jsonString = new JSONObject().put("username", info.getUsername()).put("password", info.getPassword())
                .put("email", info.getEmail()).put("nume", info.getNume()).put("prenume", info.getPrenume())
                .put("cnp", info.getCnp()).put("judet", info.getJudet())
                .put("localitate", info.getLocalitate()).put("address", info.getAddress())
                .put("phone", info.getPhone()).put("accountType", info.getAccountType().toString())
                .put("license", info.getLicence()).toString();

        logger.debug("SENDING: " + jsonString);

        JSONObject jsonResponse = sendRequest(connection, jsonString);

        logger.debug("RESPONSE : " + jsonResponse);

        if(jsonResponse == null)
            return new Pair<>(false, "Connection error.");

        if(jsonResponse.getString("status").equals("0"))
            return new Pair<>(true, "Registered successfully");

        return new Pair<>(false, jsonResponse.getString("message"));
    }

    public Pair<Boolean, String> userTrimiteFormularDonare(FormularDonare formular, String username)
    {
        HttpURLConnection connection = getConnection("/user_trimite_formular_donare");

        if(connection == null)
            return new Pair<>(false, "Client connection request Error");

        String jsonString = new JSONObject().put("nume", formular.getNume()).put("prenume", formular.getPrenume())
                .put("sex", formular.getSex().toString()).put("telefon", formular.getTelefon())
                .put("domiciliu_localitate", formular.getDomiciliuLocalitate())
                .put("domiciliu_judet", formular.getDomiciliuJudet())
                .put("domiciliu_adresa", formular.getDomiciliuAdresa())
                .put("resedinta_localitate", formular.getResedintaLocalitate())
                .put("resedinta_judet", formular.getResedintaJudet())
                .put("resedinta_adresa", formular.getResedintaAdresa())
                .put("beneficiar_full_name", formular.getBeneficiarFullName())
                .put("beneficiar_CNP", formular.getBeneficiarCNP())
                .put("grupa", formular.getGrupa()).put("rh", formular.getRh().toString())
                .put("zile_disponibil", formular.getZileDisponibil())
                .put("username", username).toString();

        logger.debug("SENDING: " + jsonString);
        JSONObject jsonResponse = sendRequest(connection, jsonString);
        logger.debug("RESPONSE : " + jsonResponse);


        return new Pair<>(true, "Success");

    }

    public Pair<Boolean,String> staffUpdateFormularDonare(FormularDonare formular, int id_locatie)
    {
        HttpURLConnection connection = getConnection("/staff_update_formular_donare");

        if(connection == null)
            return new Pair<>(false, "Client connection request Error");

        String jsonString = new JSONObject().put("nume", formular.getNume()).put("prenume", formular.getPrenume())
                .put("sex", formular.getSex().toString()).put("telefon", formular.getTelefon())
                .put("domiciliu_localitate", formular.getDomiciliuLocalitate())
                .put("domiciliu_judet", formular.getDomiciliuJudet())
                .put("domiciliu_adresa", formular.getDomiciliuAdresa())
                .put("resedinta_localitate", formular.getResedintaLocalitate())
                .put("resedinta_judet", formular.getResedintaJudet())
                .put("resedinta_adresa", formular.getResedintaAdresa())
                .put("beneficiar_full_name", formular.getBeneficiarFullName())
                .put("beneficiar_CNP", formular.getBeneficiarCNP())
                .put("grupa", formular.getGrupa())
                .put("rh", formular.getRh().toString())
                .put("zile_disponibil", formular.getZileDisponibil())
                .put("id",formular.getId())
                .put("status",formular.getStatus())
                .put("id_locatie",id_locatie).toString();

        logger.debug("SENDING: " + jsonString);
        JSONObject jsonResponse = sendRequest(connection, jsonString);
        logger.debug("RESPONSE : " + jsonResponse);


        return new Pair<>(true, "Success");
    }

    public List<FormularDonare> getFormulareDonariDupaLocatie(int id_locatie)
    {
        List<FormularDonare> list = new ArrayList<>();
        HttpURLConnection connection = getConnection("/staff_cere_formulare_donari");

        if(connection == null)
            System.out.println("Pula");

        String jsonString = new JSONObject().put("id_locatie", id_locatie).toString();

        logger.debug("SENDING: " + jsonString);
        JSONObject jsonResponse = sendRequest(connection, jsonString);
        logger.debug("RESPONSE : " + jsonResponse);

        if(jsonResponse != null)
        {
            JSONArray formularDonares = jsonResponse.getJSONArray("entities");
            System.out.println(formularDonares.length());
            for(int i = 0; i < formularDonares.length() ;i++)
            {
                JSONObject x = formularDonares.getJSONObject(i);
                int id = x.getInt("id");
                String nume = x.getString("nume");
                String prenume = x.getString("prenume");
                Sex sex = Sex.valueOf(x.getString("sex"));
                String telefon = x.getString("telefon");
                String domiciliuLocalitate = x.getString("domiciliuLocalitate");
                String domiciliuJudet = x.getString("domiciliuJudet");
                String domiciliuAdresa = x.getString("domiciliuAdresa");
                String resedintaLocalitate = x.getString("resedintaLocalitate");
                String resedintaJudet = x.getString("resedintaJudet");
                String resedintaAdresa = x.getString("resedintaAdresa");
                String beneficiarFullName = x.get("beneficiar_full_name").toString();
                String beneficiarCNP = x.get("beneficiar_cnp").toString();
                GrupaSange grupa = GrupaSange.valueOf(x.getString("grupa").toUpperCase());
                RH rh = RH.valueOf(x.getString("rh").toUpperCase());
                Status status = Status.valueOf(x.getString("status"));
                FormularDonare a = new FormularDonare(id,nume,prenume,sex,telefon,
                        domiciliuLocalitate,domiciliuJudet,domiciliuAdresa,
                        resedintaLocalitate,resedintaJudet,resedintaAdresa,
                        beneficiarFullName,beneficiarCNP,grupa,rh,status);

                list.add(a);
            }
        }

        return list;


    }


    public Pair<Boolean, String> staffTrimiteFormularDonare(FormularDonare formular)
    {
        HttpURLConnection connection = getConnection("/staff_trimite_formular_donare");

        if(connection == null)
            return new Pair<>(false, "Client connection request Error");

        String jsonString = new JSONObject().put("nume", formular.getNume()).put("prenume", formular.getPrenume())
                .put("sex", formular.getSex().toString()).put("telefon", formular.getTelefon())
                .put("domiciliu_localitate", formular.getDomiciliuLocalitate())
                .put("domiciliu_judet", formular.getDomiciliuJudet())
                .put("domiciliu_adresa", formular.getDomiciliuAdresa())
                .put("resedinta_localitate", formular.getResedintaLocalitate())
                .put("resedinta_judet", formular.getResedintaJudet())
                .put("resedinta_adresa", formular.getResedintaAdresa())
                .put("beneficiar_full_name", formular.getBeneficiarFullName())
                .put("beneficiar_CNP", formular.getBeneficiarCNP())
                .put("grupa", formular.getGrupa()).put("rh", formular.getRh().toString())
                .put("zile_disponibil", formular.getZileDisponibil()).toString();

        logger.debug("SENDING: " + jsonString);
        JSONObject jsonResponse = sendRequest(connection, jsonString);
        logger.debug("RESPONSE : " + jsonResponse);


        return new Pair<>(true, "Success");
    }

    /** Sends a json string to a given relative path
     * @param jsonString : String to be sent to the server
     * @param urlRelativePath : Type of request, like /login
     * @return JsonObject returned from the server
     */
    public JSONObject send_json_request(String jsonString, String urlRelativePath) {
        HttpURLConnection http = getConnection(urlRelativePath);

        if(http == null){
            return null;
        }

        return this.sendRequest(http, jsonString);
    }
}
