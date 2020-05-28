package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.ClientDevice;
import kg.tezal.tezal_back.model.ClientDeviceModel;

import java.util.List;

public interface ClientDeviceService {
    ClientDevice findById(Long id);

    ClientDevice findByPhone(String id);

    List<ClientDevice> findAll();

    ClientDevice create(ClientDevice clientDevice);

    String deleteById(Long id);

    ClientDevice putById(Long id, ClientDeviceModel clientDevice);
}
