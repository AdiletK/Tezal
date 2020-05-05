package kg.tezal.tezal_back.apicontroller;

import kg.tezal.tezal_back.entity.Client;
import kg.tezal.tezal_back.model.ClientLongModel;
import kg.tezal.tezal_back.model.ClientPersonalCodeModel;
import kg.tezal.tezal_back.model.ClientShortModel;
import kg.tezal.tezal_back.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/client")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable("id") Long id) {
        return clientService.findById(id);
    }

    @GetMapping("/code/{id}")
    public ClientPersonalCodeModel getCodeByClientId(@PathVariable("id") Long id) {
        return clientService.findCodeByClientId(id);
    }

    @GetMapping("/all")
    public List<ClientShortModel> getAll() {
        return clientService.getAllClients();
    }

    @GetMapping("/allByOrgId/{id}")
    public List<ClientShortModel> getAllByOrgIdDao(@PathVariable("id") Long id) {
//        return clientService.getAllClientById(id);
        return null;
    }

//
//    @PutMapping("/{id}")
//    public Client putClient(@PathVariable("id") Long id, @RequestBody Client client) {
//        return clientService.putById(id, client);
//    }

    @PostMapping()
    public Client postClient(@RequestBody Client client) {
        return clientService.create(client);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return clientService.deleteById(id);
    }

//    @GetMapping("/list/{id}")
//    public List<ClientListDto> getClientList(@PathVariable("id") Long id) {
//        return clientService.getClientsByOrganization(id);
//    }

    @GetMapping("getClientByCode/{code}")
    public ClientLongModel getClientByCode(@PathVariable("code")String code){
        return clientService.findClientByPersonalCode(code);
    }
}
