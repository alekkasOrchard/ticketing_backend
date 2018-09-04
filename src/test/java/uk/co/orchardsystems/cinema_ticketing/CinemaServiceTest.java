package uk.co.orchardsystems.cinema_ticketing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CinemaTicketingApplication.class)
public class CinemaServiceTest {

    @MockBean
    private MongoBean mongoBean;
    @Autowired
    private CinemaService cinemaService;
    private JSONArray testData;

    @Before
    public void beforeClassSetup() throws IOException, JSONException {
        testData = new JSONObject(readFile("src/test/resources/testData/cinemas.json", StandardCharsets.UTF_8)).getJSONArray("cinemas");
    }

    @Test
    public void whenCinemaIdIsProvided_thenReturnedCinemaIsCorrect() throws JSONException {
        Mockito.when(mongoBean.getCinemasJSON("ticketing", "cinemas")).thenReturn(testData);
        String expected = "{'contactNum':'07789147384','address':'address1','name':'cinema1', 'id':1}";
        JSONAssert.assertEquals(expected, cinemaService.getCinema(1), JSONCompareMode.LENIENT);
    }

    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
