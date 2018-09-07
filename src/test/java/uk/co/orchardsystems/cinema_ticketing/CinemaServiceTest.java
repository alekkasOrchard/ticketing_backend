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
import uk.co.orchardsystems.cinema_ticketing.Exceptions.ScreeningsNotFoundException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;


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
        Mockito.when(mongoBean.getCollectionJSON("ticketing", "cinemas", "cinemas")).thenReturn(testData);
    }

    @Test
    public void whenCinemaIdIsProvided_thenReturnedCinemaIsCorrect() throws JSONException {
        String expected = "{'contactNum':'07789147384','address':'address1','name':'cinema1', 'id':1}";
        JSONAssert.assertEquals(expected, cinemaService.getCinema(1), JSONCompareMode.LENIENT);
        expected = "{'contactNum':'07789147384','address':'address2','name':'cinema2', 'id':2}";
        JSONAssert.assertEquals(expected, cinemaService.getCinema(2), JSONCompareMode.LENIENT);
    }

    @Test
    public void whenInvalidCinemaIdIsProvidedForGetCinema_thenCorrectExceptionIsThrown() {
        assertThrows(JSONException.class, () -> {
            cinemaService.getCinema(-1);
        });
    }

    @Test
    public void returnsEveryCinemaInDatabase() throws JSONException {
        String expected = "[{'contactNum':'07789147384','address':'address1','name':'cinema1', 'id':1}, {'contactNum':'07789147384','address':'address2','name':'cinema2', 'id':2}]";
        JSONAssert.assertEquals(expected, cinemaService.getCinemasList(), JSONCompareMode.STRICT);
    }

    @Test
    public void whenCinemaIdIsProvided_thenReturnedMoviesAreCorrect() throws JSONException {
        String expected = "[ { 'rating': '15', 'id': 1, 'title': 'Blade Runner' }, { 'rating': '18', 'id': 2, 'title': 'Alien' }, { 'rating': '12', 'id': 3, 'title': 'The Lord of the Rings: The Fellowship of the Ring' }, { 'rating': '12', 'id': 5, 'title': 'Lawrence of Arabia' }, { 'rating': '18', 'id': 6, 'title': 'The Thing' } ]";
        JSONAssert.assertEquals(expected, cinemaService.getMoviesList(1), JSONCompareMode.LENIENT);
        expected = "[ { 'rating': '15', 'id': 1, 'title': 'Blade Runner' }, { 'rating': '18', 'id': 2, 'title': 'Alien' }, { 'rating': '12', 'id': 3, 'title': 'The Lord of the Rings: The Fellowship of the Ring' }, { 'rating': '15', 'id': 4, 'title': 'The Big Lebowski' }, { 'rating': '12', 'id': 5, 'title': 'Lawrence of Arabia' }, { 'rating': '18', 'id': 6, 'title': 'The Thing' } ]";
        JSONAssert.assertEquals(expected, cinemaService.getMoviesList(2), JSONCompareMode.LENIENT);
    }

    @Test
    public void whenInvalidCinemaIdIsProvidedForGetMoviesList_thenCorrectExceptionIsThrown() {
        assertThrows(JSONException.class, () -> {
            cinemaService.getMoviesList(-1);
        });
    }

    @Test
    public void whenCinemaIdAndMovieIdIsProvided_thenReturnedScreeningsAreCorrect() throws JSONException, ScreeningsNotFoundException {
        String expected = "[ { 'id': 1, 'date': '7/8/2018', 'screenId': 1, 'time': '9:00' }, { 'id': 4, 'date': '7/8/2018', 'screenId': 1, 'time': '22:45' }, { 'id': 5, 'date': '7/8/2018', 'screenId': 2, 'time': '10:00' } ]";
        JSONAssert.assertEquals(expected, cinemaService.getScreeningsForMovie(1, 1), JSONCompareMode.LENIENT);
        expected = "[ { 'id': 7, 'date': '7/8/2018', 'screenId': 2, 'time': '19:15' } ]";
        JSONAssert.assertEquals(expected, cinemaService.getScreeningsForMovie(1, 5), JSONCompareMode.LENIENT);
        expected = "[ { 'id': 1, 'date': '7/8/2018', 'screenId': 1, 'time': '9:00' }, { 'date': '7/8/2018', 'screenId': 2, 'id': 5, 'time': '10:00' } ]";
        JSONAssert.assertEquals(expected, cinemaService.getScreeningsForMovie(2, 1), JSONCompareMode.LENIENT);
        expected = "[ { 'id': 8, 'date': '7/8/2018', 'screenId': 2, 'time': '23:45' } ]";
        JSONAssert.assertEquals(expected, cinemaService.getScreeningsForMovie(2, 6), JSONCompareMode.LENIENT);
    }

    @Test
    public void whenInvalidCinemaIdISProvidedForGetScreeningsForMovie_thenCorrectExceptionIsThrown(){
                assertThrows(JSONException.class, () -> {
            cinemaService.getScreeningsForMovie(-1, 1);
        });
    }

        @Test
    public void whenInvalidMovieIdISProvidedForGetScreeningsForMovie_thenCorrectExceptionIsThrown(){
                assertThrows(ScreeningsNotFoundException.class, () -> {
            cinemaService.getScreeningsForMovie(1, -1);
        });
    }

    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
