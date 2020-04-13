package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.Event;
import kg.tezal.tezal_back.model.EventFullModel;
import kg.tezal.tezal_back.model.EventModel;
import kg.tezal.tezal_back.model.EventModelImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventService {
    Event findById(Long id);

    List<Event> findAll();

    Event create(EventModelImage event);

    String deleteById(Long id);

    Event putById(Long id, EventModelImage event);

    List<EventModel> findAllEventsByOrgId(@Param("id") Long id);

    List<EventFullModel> getAllEventsByOrgId(Long orgId, Long lastId, String lastDate, Integer limit);

    Page<EventModel> findAllEventsByOrgIdAndName(Long id, String search, Pageable pageable);

    Page<EventModel> findAllEventsByOrgIdWithPagination(Long id, Pageable pageable);

}
