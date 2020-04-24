package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.dao.ClientDao;
import kg.tezal.tezal_back.entity.Client;
import kg.tezal.tezal_back.model.*;
import kg.tezal.tezal_back.repository.ClientRepository;
import kg.tezal.tezal_back.service.ClientPreferenceValueService;
import kg.tezal.tezal_back.service.ClientService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import kg.tezal.tezal_back.utils.UtilBase64Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientPreferenceValueService clientPreferenceValueService;
    @Autowired
    private ClientDao clientDao;

    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Page<ClientShortModel> getAllClientByPagination(Pageable pageable) {
        return clientRepository.getClientsByPagination(pageable);
    }

    public List<ClientShortModel> getAllClientByOrgId(Long id, String search) {
        return clientDao.getClientsByOrgId(id, search);
    }

    public List<ClientShortModel> getAllClients() {
        return clientRepository.getAllClients();
    }

    public Client create(Client client) {
        return clientRepository.save(client);
    }

    public Client create(ClientModelImage clientLongModel) {
        Client client = new Client();
        if(clientLongModel.getImage() != null && clientLongModel.getImage().getContentType().contains("image"))
            client.setImage(UtilBase64Image.encoder(clientLongModel.getImage()));
        client.setPersonalCode(clientLongModel.getPersonalCode());
        client.setFirstName(clientLongModel.getFirstName());
        client.setLastName(clientLongModel.getLastName());
        client.setPatronymic(clientLongModel.getPatronymic());
        client.setNationality(clientLongModel.getNationality());
        client.setCreatedDate(Date.from(Instant.now()));
        client.setLocale(client.getLocale());
        client.setClientSex(client.getClientSex());
        return clientRepository.save(client);
    }

    public String deleteById(Long id) {
        clientRepository.deleteById(id);
        return "Client number " + id + " has been deleted!";
    }

    public Client putById(Long id, ClientModelImage client) {
        return clientRepository.findById(id)
                .map(newClient -> {
                    if(client.getImage() != null && client.getImage().getContentType().contains("image"))
                        newClient.setImage(UtilBase64Image.encoder(client.getImage()));
                    newClient.setClientSex(client.getClientSex());
                    newClient.setFirstName(client.getFirstName());
                    newClient.setLastName(client.getLastName());
                    newClient.setLocale(client.getLocale());
                    newClient.setNationality(client.getNationality());
                    newClient.setPatronymic(client.getPatronymic());
                    newClient.setPersonalCode(client.getPersonalCode());
                    newClient.setCreatedDate(clientRepository.findClientById(id).getCreatedDate());
                    return clientRepository.save(newClient);
                })
                .orElseThrow(() ->
                        new RecordNotFoundException("Record not found with id:" + id));
    }

    public ClientLongModel findModelById(Long id) {
        return clientRepository.findClientById(id);
    }

    public ClientPersonalCodeModel findCodeByClientId(Long id) {
        return clientDao.getCodeByClientId(id);
    }

    @Override
    public ClientLongModel findClientByPersonalCode(String code) {
        return clientRepository.findClientByPersonalCode(code);
    }

    public List<ClientPreferenceModel> getClientPreferences(Long clientId){
        return clientPreferenceValueService.getClientPreference(clientId);
    }

    public Page<ClientShortModel> findAllByNameOrDescription(String search, Pageable pageable) {
        return clientRepository.getClientsByName(search,pageable);
    }

}