package uk.co.orchardsystems.cinema_ticketing;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.orchardsystems.cinema_ticketing.Exceptions.NoScreeningsFoundException;
import uk.co.orchardsystems.cinema_ticketing.controller.TicketServicesController;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TicketServicesController.class, secure = false)
public class TicketServicesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CinemaService cinemaService;

    @Before
    public void beforeClassSetup() throws JSONException, NoScreeningsFoundException {
        Mockito.when(cinemaService.getCinema(1)).thenReturn("results");
        Mockito.when(cinemaService.getCinemasList()).thenReturn("results");
        Mockito.when(cinemaService.getMoviesList(1)).thenReturn("results");
        Mockito.when(cinemaService.getScreeningsForMovie(1, 1)).thenReturn("results");

        Mockito.when(cinemaService.getCinema(-1)).thenThrow(JSONException.class);
        Mockito.when(cinemaService.getMoviesList(-1)).thenThrow(JSONException.class);
        Mockito.when(cinemaService.getScreeningsForMovie(-1, 1)).thenThrow(JSONException.class);
        Mockito.when(cinemaService.getScreeningsForMovie(1, -1)).thenThrow(NoScreeningsFoundException.class);
    }

    //@Test

}
