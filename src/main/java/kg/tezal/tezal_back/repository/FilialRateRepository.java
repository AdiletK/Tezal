package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.FilialRate;
import kg.tezal.tezal_back.model.FilialRateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilialRateRepository extends JpaRepository<FilialRate, Long> {
    @Query(value = "select new kg.tezal.tezal_back.model.FilialRateModel(filialRate.id, filialRate.comment, filialRate.rate, filialRate.createdDate , filialRate.client.firstName, filialRate.client.lastName) FROM FilialRate filialRate where filial_id = :id ")
    List<FilialRateModel> findAllFilialRatesByFilialId(@Param("id") Long id);

}
