package kg.tezal.tezal_back.service.impl;

import kg.tezal.tezal_back.entity.ClientDevice;
import kg.tezal.tezal_back.repository.ClientDeviceRepository;
import kg.tezal.tezal_back.service.ClientDeviceService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientDeviceServiceImpl implements ClientDeviceService {
    @Autowired
    private ClientDeviceRepository clientDeviceRepository;

    public ClientDevice findById(Long id) {
        return clientDeviceRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("Record not found with id:" + id));
    }

    @Override
    public ClientDevice findByPhone(String phone) {
        return clientDeviceRepository.findByPhoneNumber(phone);
    }

    public List<ClientDevice> findAll() {
        return clientDeviceRepository.findAll();
    }

    public ClientDevice create(ClientDevice clientDevice) {
        if (findByPhone(clientDevice.getPhoneNumber()) != null){
            throw new RecordNotFoundException("Record already exist with number:" + clientDevice.getPhoneNumber());
        }
        return clientDeviceRepository.save(clientDevice);
    }

    public String deleteById(Long id) {
        clientDeviceRepository.deleteById(id);
        return "ClientDevice number " + id + " has been deleted!";
    }

    public ClientDevice putById(Long id, ClientDevice clientDevice) {
        return clientDeviceRepository.findById(id)
                .map(newClientDevice -> {
                    newClientDevice.setId(clientDevice.getId());
                    newClientDevice.setClient(clientDevice.getClient());
                    newClientDevice.setImei(clientDevice.getImei());
                    newClientDevice.setLastEnterDate(clientDevice.getLastEnterDate());
                    newClientDevice.setPassword(clientDevice.getPassword());
                    newClientDevice.setPhoneNumber(clientDevice.getPhoneNumber());
                    newClientDevice.setStatus(clientDevice.getStatus());
                    return clientDeviceRepository.save(newClientDevice);
                })
                .orElseThrow(() ->
                        new RecordNotFoundException("Record not found with id:" + id));
    }


}
