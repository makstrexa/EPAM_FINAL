import com.conference.dao.exceptions.DAOException;
import com.conference.dao.implementations.InMemoryDaoFactory;
import com.conference.model.Event;
import com.conference.services.EventServiceImpl;
import com.conference.services.EventSortCriteria;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.stream.Collectors;

public class test {

    @Test
    public void test() throws DAOException {
        EventServiceImpl eventService = new EventServiceImpl(new InMemoryDaoFactory());
        eventService.search(null, EventSortCriteria.valueOf("NEW_FIRST"), "true").forEach(System.out::println);
        Collection<Event> eventsWithPag;
        int page = 1;
        int recordsPerPage = 8;

        eventsWithPag = eventService.search(null, EventSortCriteria.valueOf("NEW_FIRST"), "true")
                .stream()
                .sequential()
                .skip((long) (page - 1) * recordsPerPage)
                .limit(recordsPerPage)
                .collect(Collectors.toList());
        eventsWithPag.forEach(System.out::println);
    }

}
