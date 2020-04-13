package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.Event;
import kg.tezal.tezal_back.model.EventModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("select new kg.tezal.tezal_back.model.EventModel(event.id, event.image, event.name, event.dateFrom, event.dateTo) FROM Event event WHERE organization_id = :id")
    List<EventModel> findAllEventsByOrgId(@Param("id") Long id);

    @Query("select new kg.tezal.tezal_back.model.EventModel(event.id, event.image, event.name, event.dateFrom, event.dateTo) FROM Event event WHERE organization_id = :id and lower(name) like %:search% ORDER BY name ASC")
    Page<EventModel> findAllEventsByOrgIdAndName(Long id, String search, Pageable pageable);

    @Query("select new kg.tezal.tezal_back.model.EventModel(event.id, event.image, event.name, event.dateFrom, event.dateTo) FROM Event event WHERE organization_id = :id")
    Page<EventModel> findAllEventsByOrgIdWithPagination(Long id, Pageable pageable);

}