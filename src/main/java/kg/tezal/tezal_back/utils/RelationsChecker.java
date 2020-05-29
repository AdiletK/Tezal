package kg.tezal.tezal_back.utils;

import kg.tezal.tezal_back.entity.Client;
import kg.tezal.tezal_back.entity.User;
import kg.tezal.tezal_back.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Transactional
@Service("checkRelation")
public class RelationsChecker {
    @Autowired
    OrganizationService organizationService;

    @Autowired
    ClientService clientService;

    @Autowired
    EventService eventService;

    @Autowired
    OrgBonusService bonusService;

    @Autowired
    UserService userService;

    @Autowired
    FilialService filialService;

    public boolean isOrganizationMember(Authentication authentication, String organizationId) {
        User user = this.userService.findByUsername(authentication.getName());
        return user.getOrganization().getId().toString().equals(organizationId);
    }

    public boolean isOrganizationMember(Long userId, String organizationId) {
        User user = this.userService.findById(userId);
        return user.getOrganization().getId().toString().equals(organizationId);
    }

    public boolean isOrganizationClient(Authentication authentication, String clientId) {
        return organizationService
                .findById(userService.findByUsername(authentication.getName())
                        .getOrganization()
                        .getId()).getClients()
                .stream().map(Client::getId)
                .collect(Collectors.toSet()).contains(Long.valueOf(clientId));
    }


}

