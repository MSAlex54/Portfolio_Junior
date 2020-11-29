import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Stateful
public class EventService {

    @Inject
    private EventRepository eventRepository;

    @Inject
    private EventBean controller;

    private final String apiURL = "http://localhost:8081/reha_app/api/events";

    @PostConstruct
    private void init()  {
        updateEvents();
    }

    public void updateEvents(){
        try {
            Event[] events = getEventsFromApi();
            eventRepository.updateAllEvents(events);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Event[] getEventsFromApi() throws IOException {
        URL url = new URL(apiURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return mapper.readValue(getDataFromConnection(connection), Event[].class);
    }

    public String getDataFromConnection(HttpURLConnection connection) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))){
            String response = in.readLine();
            return response;
        }
    }

    public void doUpdate(){
        updateEvents();
        controller.update();
    }


    public List<Event> getAllEvents() {
        return eventRepository.getAllEvents();
    }

}
