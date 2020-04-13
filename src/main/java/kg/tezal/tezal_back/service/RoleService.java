package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.Role;

import java.util.List;

public interface RoleService {
    Role findById(Long id);

    List<Role> findAll();

    Role create(Role role);

    String deleteById(Long id);

    Role putById(Long id, Role role);
}
