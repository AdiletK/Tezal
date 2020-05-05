package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.Organization;
import kg.tezal.tezal_back.model.OrganizationModel;
import kg.tezal.tezal_back.model.OrganizationModelImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrganizationService {

    Organization findById(Long id);

    List<Organization> findAll();

    List<Organization> findAllSorted();

    Page<OrganizationModel> findAll(Pageable pageable);

    Page<OrganizationModel> findAllByNameOrDescription(String search, Pageable pageable);

    Organization create(OrganizationModelImage organization);

    void changeStatus(Long id);

    String deleteById(Long id);

    Organization putById(Long id, OrganizationModelImage organization);

    List<OrganizationModel> findAllOrganizationList();
    List<OrganizationModel> findAllOrganizationListByCategoryId(Long catId);

    List<OrganizationModel> getOrganizationListByClientId(Long id);

    List<OrganizationModel> nextOrganizationListByClientIdAndCategoryId(Long clientId, Long categoryId, Long lastId, Integer limit);

    OrganizationModel getOrganizationById(Long id);
}
