package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.User;
import kg.tezal.tezal_back.model.ManagerListDto;
import kg.tezal.tezal_back.model.OrganizationManagerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    User findById(Long id);

    List<User> findAll();

    User create(User user);

    String deleteById(Long id);

    User putById(Long id, User user);

    User createOrganizationManager(OrganizationManagerDto organizationManagerDto, Boolean isManager);

    User updateOrganizationManager(Long managerId, OrganizationManagerDto organizationManagerDto,Boolean isManager);

    List<ManagerListDto> findByOrgId(Long orgId);

    Page<ManagerListDto> findAllByOrgIdAndByNameOrDescription(Long id, String search, Pageable pageable);

    Page<ManagerListDto> findAllByOrgIdWithPagination(@Param("id") Long id, Pageable pageable);
}
