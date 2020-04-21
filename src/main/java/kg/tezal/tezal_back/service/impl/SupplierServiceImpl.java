package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.entity.Supplier;
import kg.tezal.tezal_back.repository.SupplierRepository;
import kg.tezal.tezal_back.service.SupplierService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier findById(Long id) {
        return supplierRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException("Record not found with id:" + id));
    }

    @Override
    public Supplier create(Supplier unit) {
        return supplierRepository.save(unit);
    }

    @Override
    public String deleteById(Long id) {
        supplierRepository.deleteById(id);
        return "Record deleted";
    }

    @Override
    public Supplier putById(Long id, Supplier unit) {
        return supplierRepository.findById(id)
                .map(newUnit -> {
                    newUnit.setName(unit.getName());
                    return supplierRepository.save(newUnit);
                })
                .orElseThrow(() ->
            new RecordNotFoundException("Record not found with id:" + id));

    }

    @Override
    public Page<Supplier> findAll(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }

    @Override
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }
}
