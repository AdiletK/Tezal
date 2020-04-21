package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplierService {
    Supplier findById(Long id);

    Supplier create(Supplier unit);

    String deleteById(Long id);

    Supplier putById(Long id, Supplier unit);

    Page<Supplier> findAll(Pageable pageable);

    List<Supplier> findAll();
}
