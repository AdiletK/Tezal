package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.entity.Unit;
import kg.tezal.tezal_back.repository.UnitRepository;
import kg.tezal.tezal_back.service.UnitService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl implements UnitService {
    private final UnitRepository unitRepository;

    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public Unit findById(Long id) {
        return unitRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Record not found with id:" + id));
    }

    @Override
    public Unit create(Unit unit) {
        return unitRepository.save(unit);
    }

    @Override
    public String deleteById(Long id) {
        unitRepository.deleteById(id);
        return "Record deleted";
    }

    @Override
    public Unit putById(Long id, Unit unit) {
        return unitRepository.findById(id)
                .map(newUnit -> {
                    newUnit.setName(unit.getName());
                    return unitRepository.save(newUnit);
                })
                .orElseThrow(() ->
            new RecordNotFoundException("Record not found with id:" + id));

    }

    @Override
    public Page<Unit> findAll(Pageable pageable) {
        return unitRepository.findAll(pageable);
    }
}
