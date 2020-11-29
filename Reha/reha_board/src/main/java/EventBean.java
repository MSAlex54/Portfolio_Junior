import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


@Named("controller")
@ApplicationScoped
public class EventBean {

    @Inject @Push
    private PushContext push;

    @EJB
    private EventService service;

    private List<Event> events;

    public List<Event> getEvents() {
        events = service.getAllEvents();
        return events;
    }

    public void update() {
        events = service.getAllEvents();
        push.send("updateEvents");
        System.out.println("PUSH UPDATE");
    }
}
