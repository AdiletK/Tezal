package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.Client;
import kg.tezal.tezal_back.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {
    Client findById(Long id);

    List<Client> findAll();

    Page<ClientShortModel> getAllClientByPagination(Pageable pageable);

    List<ClientShortModel> getAllClientByOrgId(Long id, String search);

    Client create(Client client);

    Client create(ClientModelImage client);

    String deleteById(Long id);

    Client putById(Long id, ClientModelImage clientLongModel);

    ClientLongModel findModelById(Long id);

    ClientPersonalCodeModel findCodeByClientId(Long id);

    ClientLongModel findClientByPersonalCode(String code);


    List<ClientPreferenceModel> getClientPreferences(Long id);

    Page<ClientShortModel> findAllByNameOrDescription(String search, Pageable pageable);

}
