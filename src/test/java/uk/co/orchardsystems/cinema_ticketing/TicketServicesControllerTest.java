package uk.co.orchardsystems.cinema_ticketing;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uk.co.orchardsystems.cinema_ticketing.Exceptions.ScreeningsNotFoundException;
import uk.co.orchardsystems.cinema_ticketing.controller.TicketServicesController;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TicketServicesController.class, secure = false)
public class TicketServicesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CinemaService cinemaService;

    @Before
    public void beforeClassSetup() throws JSONException, ScreeningsNotFoundException {
        Mockito.when(cinemaService.getCinema(1)).thenReturn("result");
        Mockito.when(cinemaService.getCinemasList()).thenReturn("result");
        Mockito.when(cinemaService.getMoviesList(1)).thenReturn("result");
        Mockito.when(cinemaService.getScreeningsForMovie(1, 1)).thenReturn("result");

        Mockito.when(cinemaService.getCinema(-1)).thenThrow(JSONException.class);
        Mockito.when(cinemaService.getMoviesList(-1)).thenThrow(JSONException.class);
        Mockito.when(cinemaService.getScreeningsForMovie(-1, 1)).thenThrow(JSONException.class);
        Mockito.when(cinemaService.getScreeningsForMovie(1, -1)).thenThrow(ScreeningsNotFoundException.class);
    }

    @Test
    public void whenGetCinemasIsCalled_thenReturns200() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ticketing/cinemas").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void whenCinemaIdIsProvided_thenGetMoviesAtCinemaReturns200() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ticketing/movies?cinemaId=1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void whenInvalidCinemaIdIsProvided_thenGetMoviesAtCinemaReturns404() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ticketing/movies?cinemaId=-1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void whenCinemaIdAndMovieIdIsProvided_thenGetScreeningsReturns200() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ticketing/screenings?cinemaId=1&movieId=1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void whenInvalidCinemaIdAndValidMovieIdIsProvided_thenGetScreeningsReturns404() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ticketing/screenings?cinemaId=-1&movieId=1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void whenValidCinemaIdAndInvalidMovieIdIsProvided_thenGetScreeningsReturns404() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ticketing/screenings?cinemaId=1&movieId=-1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void whenCinemaIdIsProvided_thenGetCinemasReturns200() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ticketing/cinema?cinemaId=1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void whenInvalidCinemaIdIsProvided_thenGetCinemasReturns404() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/ticketing/cinema?cinemaId=-1").accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }
}
