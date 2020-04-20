package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.dao.EventDao;
import kg.tezal.tezal_back.entity.Event;
import kg.tezal.tezal_back.model.EventFullModel;
import kg.tezal.tezal_back.model.EventModel;
import kg.tezal.tezal_back.model.EventModelImage;
import kg.tezal.tezal_back.repository.EventRepository;
import kg.tezal.tezal_back.service.EventService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import kg.tezal.tezal_back.utils.UtilBase64Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    private final EventDao eventDao;

    public EventServiceImpl(EventRepository eventRepository, EventDao eventDao) {
        this.eventRepository = eventRepository;
        this.eventDao = eventDao;
    }

    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event create(EventModelImage eventModelImage) {
        Event event = new Event();
        event.setId(eventModelImage.getId());
        event.setName(eventModelImage.getName());
        if(eventModelImage.getImage() != null && eventModelImage.getImage().getContentType().contains("image"))
            event.setImage(UtilBase64Image.encoder(eventModelImage.getImage()));
        event.setDescription(eventModelImage.getDescription());
        event.setDateFrom(eventModelImage.getDateFrom());
        event.setDateTo(eventModelImage.getDateTo());
        return eventRepository.save(event);
    }

    public String deleteById(Long id) {
        eventRepository.deleteById(id);
        return "Event number " + id + " has been deleted!";
    }

    public Event putById(Long id, EventModelImage event) {
        return eventRepository.findById(id)
                .map(newEvent -> {
                    if(event.getImage() != null && event.getImage().getContentType().contains("image"))
                        newEvent.setImage(UtilBase64Image.encoder(event.getImage()));
                    newEvent.setName(event.getName());
                    newEvent.setDescription(event.getDescription());
                    newEvent.setDateTo(event.getDateTo());
                    newEvent.setDateFrom(event.getDateFrom());
                    newEvent.setOrganization(event.getOrganization());
                    return eventRepository.save(newEvent);
                })
                .orElseThrow(() ->
                        new RecordNotFoundException("Record not found with id:" + id));
    }

    public List<EventModel> findAllEventsByOrgId(@Param("id") Long id) {
        return eventRepository.findAllEventsByOrgId(id);
    }

    public List<EventFullModel> getAllEventsByOrgId(Long orgId, Long lastId, String lastDate, Integer limit) {
        return eventDao.getEventByOrgId(orgId,lastId,lastDate,limit);
    }
    @Override
    public Page<EventModel> findAllEventsByOrgIdAndName(Long id, String search, Pageable pageable) {
        return eventRepository.findAllEventsByOrgIdAndName(id, search, pageable);
    }

    @Override
    public Page<EventModel> findAllEventsByOrgIdWithPagination(Long id, Pageable pageable) {
        return eventRepository.findAllEventsByOrgIdWithPagination(id, pageable);
    }


}
