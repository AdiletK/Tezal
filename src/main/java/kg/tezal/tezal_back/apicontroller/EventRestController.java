package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.Event;
import kg.tezal.tezal_back.model.EventFullModel;
import kg.tezal.tezal_back.model.EventModel;
import kg.tezal.tezal_back.model.EventModelImage;
import kg.tezal.tezal_back.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/event")
public class EventRestController {
    @Autowired
    private EventService eventService;

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable("id") Long id) {
        return eventService.findById(id);
    }

//    @GetMapping("/all")
//    public List<Event> getAll() {
//        return eventService.findAll();
//    }

    @PutMapping("/{id}")
    public Event putEvent(@PathVariable("id") Long id, @RequestBody EventModelImage eventModel) {
        return eventService.putById(id, eventModel);
    }

    @PostMapping()
    public Event postEvent(@RequestBody EventModelImage eventModel) {
        return eventService.create(eventModel);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return eventService.deleteById(id);
    }

    @GetMapping("/all/{id}")
    public List<EventModel> findAllEventsByOrgId(@PathVariable("id") Long id) {
        return eventService.findAllEventsByOrgId(id);
    }

    @GetMapping("/all/jdbc/{id}")
    public List<EventFullModel> getAllEventsByOrgId(@PathVariable("id") Long id,
                                                    @RequestParam(value = "last_i", defaultValue="0", required = false) Long eventId,
                                                    @RequestParam(value = "last_d", defaultValue = "2020-03-03 00:00:00", required = false) String lastDate,
                                                    @RequestParam(value = "limit", defaultValue = "5", required = false) Integer pageSize) {
        return eventService.getAllEventsByOrgId(id, eventId, lastDate, pageSize);
    }
}
