package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.Filial;
import kg.tezal.tezal_back.model.FilialModel;
import kg.tezal.tezal_back.model.FilialModelImage;
import kg.tezal.tezal_back.model.FilialShortModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilialService {
    Filial findById(Long id);

    List<Filial> findAll();

    List<FilialModel> findAllByOrgId(Long id);

    Page<FilialModel> findAllByOrgIdWithPagination(Long id, Pageable pageable);

    Page<FilialModel> findAllByOrgIdAndByNameOrDescription(Long id, String search, Pageable pageable);

    Filial create(FilialModelImage filial);

    String deleteById(Long id);

    void changeStatus(Long id);


    Filial putById(Long id, FilialModelImage filial);

//    List<FilialModel> findAllFilialsByOrgId(@Param("id") Long id);

    List<FilialShortModel> getAllFilialsByOrgId(Long orgId, Long lastId, Double lastAverage, Integer limit);
}