package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.FilialRate;
import kg.tezal.tezal_back.model.FilialRateModel;

import java.util.List;

public interface FilialRateService {
    FilialRate findById(Long id);

    List<FilialRate> findAll();

    FilialRate create(FilialRate filialRate);

    String deleteById(Long id);

    FilialRate putById(Long id, FilialRate filialRate);

    List<FilialRateModel> findFilialRatesByFilialId(Long id);
}
