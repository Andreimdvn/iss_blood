package Communication;

import org.json.JSONObject;
import org.json.JSONPointer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.util.Properties;

import static org.junit.Assert.*;

public class FlaskClientTest {

    private FlaskClient flaskClient;
    private String propertiesPath = "/Config/defaultProperties.props";
    private Properties properties;
    @Before
    public void setUp() throws Exception {
        properties = new Properties();
        properties.load(getClass().getResourceAsStream(propertiesPath));
        this.flaskClient = new FlaskClient(properties);
    }

    @After
    public void tearDown() throws Exception {
    }

    //NOTE: Python flask server should be running
    @Test
    public void testRequest() throws Exception {

        String urlRelativePath = "/test";
        String jsonString = new JSONObject().put("test", 1).toString();
        JSONObject response = this.flaskClient.send_json_request(jsonString, urlRelativePath);
        assertEquals(response.getInt("test"),1);

    }
}