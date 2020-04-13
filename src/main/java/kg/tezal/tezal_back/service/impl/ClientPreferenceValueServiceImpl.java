package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.dao.ClientPreferenceDao;
import kg.tezal.tezal_back.entity.ClientPreferenceValue;
import kg.tezal.tezal_back.model.ClientPreferenceModel;
import kg.tezal.tezal_back.repository.ClientPreferenceValueRepository;
import kg.tezal.tezal_back.service.ClientPreferenceValueService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientPreferenceValueServiceImpl implements ClientPreferenceValueService {
    @Autowired
    private ClientPreferenceValueRepository clientPreferenceValueRepository;

    @Autowired
    private ClientPreferenceDao clientPreferenceDao;

    public ClientPreferenceValue findById(Long id) {
        return clientPreferenceValueRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    public List<ClientPreferenceValue> findAll() {
        return clientPreferenceValueRepository.findAll();
    }

    public ClientPreferenceValue create(ClientPreferenceValue clientPreferenceValue) {
        return clientPreferenceValueRepository.save(clientPreferenceValue);
    }

    public String deleteById(Long id) {
        clientPreferenceValueRepository.deleteById(id);
        return "ClientPreferenceValue number " + id + " has been deleted!";
    }

    public ClientPreferenceValue putById(Long id, ClientPreferenceValue clientPreferenceValue) {
        return clientPreferenceValueRepository.findById(id)
                .map(newClientPreferenceValue -> {
                    newClientPreferenceValue.setClient(clientPreferenceValue.getClient());
                    newClientPreferenceValue.setOrganization(clientPreferenceValue.getOrganization());
                    newClientPreferenceValue.setOrganizationPreferenceCategory(clientPreferenceValue.getOrganizationPreferenceCategory());
                    newClientPreferenceValue.setValue(clientPreferenceValue.getValue());
                    return clientPreferenceValueRepository.save(newClientPreferenceValue);
                })
                .orElseThrow(() ->
                        new RecordNotFoundException("Record not found with id:" + id));
    }

    public List<ClientPreferenceModel> getClientPreference(Long clientId) {
        return clientPreferenceDao.getClientPreferences(clientId);
    }

}
