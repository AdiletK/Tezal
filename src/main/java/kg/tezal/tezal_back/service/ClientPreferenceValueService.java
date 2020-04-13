package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.ClientPreferenceValue;
import kg.tezal.tezal_back.model.ClientPreferenceModel;

import java.util.List;

public interface ClientPreferenceValueService {
    ClientPreferenceValue findById(Long id);

    List<ClientPreferenceValue> findAll();

    ClientPreferenceValue create(ClientPreferenceValue clientPreferenceValue);

    String deleteById(Long id);

    ClientPreferenceValue putById(Long id, ClientPreferenceValue clientPreferenceValue);

    List<ClientPreferenceModel> getClientPreference(Long clientId);
}
