package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UnitService {
    Unit findById(Long id);

    Unit create(Unit unit);

    String deleteById(Long id);

    Unit putById(Long id, Unit unit);

    Page<Unit> findAll(Pageable pageable);
}
