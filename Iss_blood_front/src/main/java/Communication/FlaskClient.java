package Communication;

import Model.Pacient;
import Model.RegisterInfo;
import Model.*;
import Utils.Observer;
import Utils.UserUtils;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.naming.directory.InvalidAttributesException;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.*;

public class FlaskClient {

    private String urlRoot;

    private Logger logger = LogManager.getLogger(FlaskClient.class.getName());

    public FlaskClient(Properties properties) {
        this.urlRoot = "http://"+properties.getProperty("serverIp")+":"+properties.getProperty("serverPort");
        initializeConnection();
    }

    private void initializeConnection() {
        try {
            Socket socket = IO.socket(this.urlRoot);

            socket.on("update", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Am primit update!");
                    update();
                }
            });

            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            this.logger.error(e.getMessage());
        }
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
            return null;
        }
    }

    public Pair<Boolean, String> isAValidDonation(String cnpDonator) {
        HttpURLConnection connection = getConnection("/valid_donation");

        if(connection == null){
            return new Pair<>(null, "Client connection request Error");
        }

        String jsonString = new JSONObject().put("cnpDonator", cnpDonator).toString();
        logger.debug("SENDING VALIDATION DONATION   " + jsonString);

        JSONObject jsonResponse = sendRequest(connection, jsonString);

        logger.debug("RESPONSE VALIDATION DONATION" + jsonResponse);

        if(jsonResponse == null)
            return new Pair<>(false, "Connection error.");
        if(jsonResponse.getString("status").equals("0"))
            return new Pair<>(true, "Is a valid donation");

        return new Pair<>(false, jsonResponse.getString("message"));
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

        HttpURLConnection connection = getConnection("/login");

        if(connection == null){
            return new Pair<>(null, "Client connection request Error");
        }

        String jsonString = new JSONObject().put("username", user).put("password", password).toString();
        logger.debug("SENDING: " + jsonString);
        JSONObject jsonResponse = this.sendRequest(connection, jsonString);
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

    public Pair<Object, String> addActiveUser(String cnp){
        HttpURLConnection connection = getConnection("/chat_add_active_user");

        if(connection == null){
            return new Pair<>(null, "Client connection request Error");
        }

        String jsonString = new JSONObject().put("cnp", cnp).toString();
        logger.debug("SENDING: " + jsonString);
        JSONObject jsonResponse = this.sendRequest(connection, jsonString);
        logger.debug("RESPONSE : " + jsonResponse);

        return new Pair<>(jsonResponse.getInt("status"), jsonResponse.getString("message"));

    }

    public Pair<Integer, List<List<String>>> getActiveUser(){

        HttpURLConnection connection = getConnection("/chat_get_active_users");

        if(connection == null){
        }
        else {
            String jsonString = new JSONObject().put("ds",2).toString();

            logger.debug("SENDING: " + jsonString);
            JSONObject jsonResponse = this.sendRequest(connection, jsonString);
            logger.debug("RESPONSE : " + jsonResponse);

            if(jsonResponse != null)
            {
                JSONArray formularDonares = jsonResponse.getJSONArray("message");
                System.out.println(formularDonares.length());
                for(int i = 0; i < formularDonares.length() ;i++)
                {
                    JSONObject x = formularDonares.getJSONObject(i);

                }
            }

        }
        return new Pair<>(null,null);
    }



    public List<Analiza> getAnalize(String cnp){
        HttpURLConnection connection = getConnection("/get_analize");

        if(connection == null)
            logger.debug("Client connection request Error");

        String jsonString = new JSONObject().put("cnp",cnp).toString();

        logger.debug("SENDING: " + jsonString);
        JSONObject jsonResponse = sendRequest(connection, jsonString);
        logger.debug("RESPONSE : " + jsonResponse);

        List<Analiza> list = new ArrayList<>();

        JSONArray analize = jsonResponse.getJSONArray("entities");

        for(int i = 0; i < analize.length() ;i++)
        {
            JSONObject analiza = analize.getJSONObject(i);

            int id = analiza.getInt("id");
            boolean alt = analiza.getBoolean("alt");
            boolean sif = analiza.getBoolean("sif");
            boolean htcv = analiza.getBoolean("htcv");
            boolean htlv = analiza.getBoolean("htlv");
            boolean hiv = analiza.getBoolean("hiv");
            boolean hb = analiza.getBoolean("hb");
            list.add(new Analiza(id,alt,sif,htlv,htcv,hiv,hb));
        }

        return list;
    }

    public Pair<Boolean, String> addPacient(Pacient pacient) {
        HttpURLConnection connection = getConnection("/add_pacient");

        if(connection == null)
            return new Pair<>(false, "Add pacient connection request error");

        String jsonString = new JSONObject().put("idMedic", pacient.getIdMedic())
                .put("numePacient",pacient.getNume())
                .put("cnpPacient", pacient.getCnp())
                .put("grupaSangePacient", pacient.getGrupaSange().toString())
                .put("rhPacient", pacient.getRh().toString()).toString();
        logger.debug("SENDING ADD PACIENT REQUEST " + jsonString);

        JSONObject jsonResponse = sendRequest(connection, jsonString);

        logger.debug("RESPONSE ADD PACIENT" + jsonResponse);

        if(jsonResponse == null)
            return new Pair<>(false, "Connection error.");
        if(jsonResponse.getString("status").equals("0"))
            return new Pair<>(true, "Added new pacient successfully");

        return new Pair<>(false, jsonResponse.getString("message"));

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

    public Pair<Boolean,String> staffUpdateFormularDonare(FormularDonare formular, int id_locatie, String staffFullName)
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
                .put("id_locatie",id_locatie)
                .put("staff_full_name", staffFullName).toString();

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
            System.out.println("Probleme la conectare");

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

    public Pair<Boolean, String> staffTrimiteAnaliza(FormularDonare formular, Analiza analiza, Integer idLocatie) {
        HttpURLConnection connection = getConnection("/staff_trimite_analiza");

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
                .put("id_locatie",idLocatie)
                .put("alt",analiza.getALT())
                .put("sif",analiza.getSIF())
                .put("htlv",analiza.getANTIHTLV())
                .put("htcv",analiza.getANTIHCV())
                .put("hiv",analiza.getANTIHIV())
                .put("hb",analiza.getHB())
                .toString();

        logger.debug("SENDING: " + jsonString);
        JSONObject jsonResponse = sendRequest(connection, jsonString);
        logger.debug("RESPONSE : " + jsonResponse);


        return new Pair<>(true, "Success");
    }

    public Pair<Boolean,String> trimitePungi(int idCerere, int idLocatie,
                                             GrupaSange grupaSange, RH rh,
                                             int plasma,int trombocite, int globule)
    {
            HttpURLConnection connection = getConnection("/desavarsire_cerere_medic");

        if(connection == null)
            System.out.println("Problema la conexiune");

        String jsonString = new JSONObject().put("id_cerere", idCerere)
                .put("id_locatie_curenta",idLocatie)
                .put("grupa",grupaSange.toString())
                .put("rh",rh.toString())
                .put("plasma",plasma)
                .put("trombocite",trombocite)
                .put("globule",globule)
                .toString();

        logger.debug("SENDING: " + jsonString);
        JSONObject jsonResponse = sendRequest(connection, jsonString);
        logger.debug("RESPONSE : " + jsonResponse);

        boolean status =jsonResponse.getInt("status") == 0;
        String mesaj = jsonResponse.getString("message");

        logger.debug(status + " "+ mesaj);


        return new Pair<>(status,mesaj);
    }

    public Map<String, List<Integer>> getStocCurent(int id_locatie) {
        HttpURLConnection connection = getConnection("/staff_get_stoc_curent");

        if(connection == null)
            System.out.println("Problema la conexiune");

        String jsonString = new JSONObject().put("id_locatie", id_locatie).toString();

        logger.debug("SENDING: " + jsonString);
        JSONObject jsonResponse = sendRequest(connection, jsonString);
        logger.debug("RESPONSE : " + jsonResponse);

        Map<String, List<Integer>> map = new HashMap<>();

        if(jsonResponse != null)
        {
            String endMessage;
            for(int i = 0;i< 2;i++) {
                endMessage = "_pozitiv";
                if (i == 1)
                    endMessage = "_negativ";
                int contor = 0;
                    while (contor < 4) {
                        String beginMessage="";
                        switch (contor) {
                            case 0:
                                beginMessage="O1";
                                break;
                            case 1:
                                beginMessage="A2";
                                break;
                            case 2:
                                beginMessage="B3";
                                break;
                            case 3:
                                beginMessage="AB4";
                                break;
                        }
                        JSONObject lista = jsonResponse.getJSONObject(beginMessage+endMessage);
                        List<Integer> stoc = new ArrayList<>();

                        stoc.add(lista.getInt("Plasma"));
                        stoc.add(lista.getInt("Trombocite"));
                        stoc.add(lista.getInt("Globule_rosii"));

                        map.put(beginMessage+endMessage,stoc);
                        contor++;
                        }
                    }
            }

        return map;
        }



    private Observer observer;

    private void update() {
        try {
            observer.update();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void addObserver(Observer controlledScreen) {
        observer = controlledScreen;
        //update();
    }

    public Pair<Boolean,String> trimiteCerereSange(CerereSange cerere, String cnpMedic) {
        this.logger.debug("Sending request trimitereCerereSange");
        HttpURLConnection connection = getConnection("/trimite_cerere_sange");

        if(connection == null)
            return new Pair<>(false, "Client connection request Error");

        String jsonString = new JSONObject()
                .put("cnp_medic", cnpMedic)
                .put("nume_pacient", cerere.getNumePacient())
                .put("cnp_pacient", cerere.getCnpPacient())
                .put("spital", cerere.getSpital())
                .put("grupa_sange", cerere.getGrupaSange().toString())
                .put("rh", cerere.getRh().toString())
                .put("numar_pungi_trombocite", cerere.getNumarPungiTrombocite().toString())
                .put("numar_pungi_globule_rosii", cerere.getNumarPungiGlobuleRosii().toString())
                .put("numar_pungi_plasma", cerere.getNumarPungiPlasma())
                .put("importanta", cerere.getImportanta().toString()).toString();

        logger.debug("SENDING: " + jsonString);
        JSONObject jsonResponse = sendRequest(connection, jsonString);
        logger.debug("RESPONSE : " + jsonResponse);

        if(jsonResponse == null) {
            return new Pair<>(null, "Connection error.");
        }
        return new Pair<>(jsonResponse.getInt("status")== 0, jsonResponse.getString("message"));
    }

    public List<CerereSange> getCereriSange(int idLocatie, String status, Boolean fromSpital)
    {
        List<CerereSange> list = new ArrayList<>();
        HttpURLConnection connection = getConnection("/get_cereri_sange");

        if(connection == null)
            System.out.println("Pula");

        String jsonString = new JSONObject().put("id_locatie", idLocatie)
                .put("status",status)
                .put("from_spital",fromSpital).toString();

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
                int trombo = x.getInt("numar_pungi_trombocite");
                int globule = x.getInt("numar_pungi_globule_rosii");
                int plasma = x.getInt("numar_pungi_plasma");
                String cnpPacient = x.getString("cnp_pacient");
                GrupaSange grupa = GrupaSange.valueOf(x.getString("grupa").toUpperCase());
                RH rh = RH.valueOf(x.getString("rh").toUpperCase());
                String numePacient =  x.getString("nume_pacient");
                Date date= Date.valueOf(x.getString("data"));
                Importanta im = Importanta.valueOf(x.getString("importanta").toUpperCase());
                String numeMedic = x.getString("nume_medic");
                String numeSpital = x.getString("spital");
                CerereSange a = new CerereSange(id, numePacient, cnpPacient, grupa, rh, trombo, globule, plasma, date, im, numeMedic, numeSpital);

                list.add(a);
            }
        }

        return list;
    }


    public List<DonareInfo> getIstoricDonare(String username) {
        this.logger.debug("Sending request get istoric");
        HttpURLConnection connection = getConnection("/get_istoric_donare");

        List<DonareInfo> rez = new ArrayList<>();

        if (connection == null)
            return null;

        String jsonString = new JSONObject()
                .put("username", username)
                .toString();


        logger.debug("SENDING: " + jsonString);
        JSONObject jsonResponse = sendRequest(connection, jsonString);
        logger.debug("RESPONSE : " + jsonResponse);


        JSONArray formularDonares = jsonResponse.getJSONArray("entities");
        for (int i = 0; i < formularDonares.length(); i++) {
            JSONObject jsonObject = formularDonares.getJSONObject(i);
            Analiza analiza = null;
            String data = "";
            String staffResponsabil = "";
            GrupaSange grupaSange = GrupaSange.UNKNOWN;
            RH rh = RH.UNKNOWN;
            if (jsonObject.getInt("id_analiza") >= 0) //altfel nu e gata inca
            {
                analiza = new Analiza(
                        jsonObject.getInt("id_analiza"),
                        jsonObject.getBoolean("ALT"),
                        jsonObject.getBoolean("SIF"),
                        jsonObject.getBoolean("ANTIHTLV"),
                        jsonObject.getBoolean("ANTIHCV"),
                        jsonObject.getBoolean("ANTIHIV"),
                        jsonObject.getBoolean("HB")
                );
                data = jsonObject.getString("data");
                grupaSange = GrupaSange.valueOf(jsonObject.getString("grupa"));
                rh = RH.valueOf(jsonObject.getString("rh").toUpperCase());
                staffResponsabil = jsonObject.getString("staff_full_name");
            }


            DonareInfo info = new DonareInfo(
                    jsonObject.getInt("numar_donare"),
                    jsonObject.getString("centru_donare"),
                    Status.valueOf(jsonObject.getString("status")),
                    analiza,
                    "",
                    data,
                    grupaSange,
                    rh
            );

            rez.add(info);
        }

        return rez;

    }


    public Pair<Boolean, String> anulareCerere(Integer id) {
        HttpURLConnection connection = getConnection("/anulare_cerere");

        if(connection == null)
            return new Pair<>(false, "Client connection request Error");

        String jsonString = new JSONObject().put("id_cerere", id).toString();

        logger.debug("SENDING: " + jsonString);
        JSONObject jsonResponse = sendRequest(connection, jsonString);
        logger.debug("RESPONSE : " + jsonResponse);


        return new Pair<>(true, "Success");
    }

    public Map<String,Integer> getCentruHomeScreenData(Integer idLocatie) {
        HttpURLConnection connection = getConnection("/get_centru_home_screen_data");

        if(connection == null) {
            logger.error("Problema la conexiune!");
            return null;
        }

        String jsonString = new JSONObject().put("id_locatie", idLocatie).toString();

        logger.debug("SENDING request for get_centru_home_screen_data: " + jsonString);
        JSONObject jsonResponse = sendRequest(connection, jsonString);
        logger.debug("RESPONSE for get_centru_home_screen_data for id : " + idLocatie.toString() + ": " + jsonResponse);

        if (jsonResponse == null)
        {
            this.logger.error("Json response is nulll!");
            return null;
        }
        Map<String, Integer> map = new HashMap<>();
        List<String> keys = new ArrayList<String>(Arrays.asList("pungi_globule_rosii", "pungi_plasma",
                "pungi_trombocite", "total_donari", "cereri_sange_in_asteptare", "cereri_donari_in_asteptare"));

        for(String key : keys){
            try {
                map.put(key, jsonResponse.getInt(key));
            }catch (Exception ex)
            {
                this.logger.error("Error for key: " + key + ": " + ex.getMessage());
            }
        }
        return map;
    }
}

