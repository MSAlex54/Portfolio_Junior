import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Named
public class EventRepository {

    @PersistenceContext(unitName = "rehaUnit")
    EntityManager entityManager;

    public List<Event> getAllEvents() {
        Query query = entityManager.createQuery("SELECT e FROM Event e ORDER BY e.timeStamp, e.patientName");
        List<Event> events = query.getResultList();
        return events;
    }

    @Transactional
    public void updateAllEvents(Event[] events) {
        clearTable();
        for (Event e : events) {
            if (entityManager.find(Event.class,e.getId())!=null){
                entityManager.merge(e);
            } else {
                entityManager.persist(e);
            }
        }
        flushAndClear();
    }

    @Transactional
    public void clearTable() {
        for (Event e: getAllEvents()) {
            entityManager.remove(e);
        }
        flushAndClear();
    }

    @Transactional
    void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}
