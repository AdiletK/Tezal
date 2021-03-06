package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.entity.Role;
import kg.tezal.tezal_back.repository.RoleRepository;
import kg.tezal.tezal_back.service.RoleService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role create(Role role) {
        return roleRepository.save(role);
    }

    public String deleteById(Long id) {
        roleRepository.deleteById(id);
        return "Role number " + id + " has been deleted!";
    }

    public Role putById(Long id, Role role) {
        return roleRepository.findById(id)
                .map(newRole -> {
                    newRole.setDescription(role.getDescription());
                    newRole.setName(role.getName());
                    return roleRepository.save(newRole);
                })
                .orElseThrow(() ->
                        new RecordNotFoundException("Record not found with id:" + id));
    }


}
