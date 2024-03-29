package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.User;
import kg.tezal.tezal_back.model.ManagerListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("select new kg.tezal.tezal_back.model.ManagerListDto(user.id,user.username, user.organization.id , user.createdDate,user.roleNameShort) FROM User user  WHERE organization.id = :id")
    List<ManagerListDto> findManagersByOrgId(@Param("id") Long orgId);

    @Query("select new kg.tezal.tezal_back.model.ManagerListDto(user.id,user.username, user.organization.id, user.createdDate,user.roleNameShort) FROM User user  WHERE organization.id = :id")
    Page<ManagerListDto> findAllByOrgIdWithPagination(@Param("id") Long id, Pageable pageable);

    @Query("select new kg.tezal.tezal_back.model.ManagerListDto(user.id,user.username, user.organization.id, user.createdDate,user.roleNameShort) FROM User user  WHERE organization.id = :id and (lower(username) like %:search%) ORDER BY username ASC")
    Page<ManagerListDto> findAllByOrgIdAndByNameOrDescription(@Param("id") Long id, String search, Pageable pageable);
}
