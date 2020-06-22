package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.ClientDevice;
import kg.tezal.tezal_back.model.ClientDeviceModel;
import kg.tezal.tezal_back.service.ClientDeviceService;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/clientDevice")
public class ClientDeviceRestController {
    @Autowired
    private ClientDeviceService clientDeviceService;

    @GetMapping("/{id}")
    public ClientDevice getClientDeviceById(@PathVariable("id") Long id) {
        return clientDeviceService.findById(id);
    }

    @GetMapping("/byClientId/{id}")
    public ClientDevice getClientDeviceByClientId(@PathVariable("id") Long id) {
        return clientDeviceService.findByClientId(id);
    }

    @GetMapping("/all")
    public List<ClientDevice> getAll() {
        return clientDeviceService.findAll();
    }

    @PutMapping("/{id}")
    public ClientDevice putClientDevice(@PathVariable("id") Long id, @RequestBody ClientDeviceModel clientDevice) {
        return clientDeviceService.putById(id, clientDevice);
    }

    @PostMapping()
    public ClientDevice postClientDevice(@RequestBody ClientDevice clientDevice) {
        if (clientDeviceService.findByPhone(clientDevice.getPhoneNumber()) != null){
            throw new RecordNotFoundException("Record already exist with number:" + clientDevice.getPhoneNumber());
        }
        clientDeviceService.create(clientDevice);
        return clientDevice;
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return clientDeviceService.deleteById(id);
    }

    public ClientDevice findByPhone(String username) {
        return clientDeviceService.findByPhone(username);
    }
}
