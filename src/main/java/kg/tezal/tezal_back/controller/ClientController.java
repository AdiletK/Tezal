package kg.tezal.tezal_back.controller;

import kg.tezal.tezal_back.apicontroller.OrganizationRestController;
import kg.tezal.tezal_back.entity.Client;
import kg.tezal.tezal_back.entity.Organization;
import kg.tezal.tezal_back.model.ClientLongModel;
import kg.tezal.tezal_back.model.ClientModelImage;
import kg.tezal.tezal_back.model.ClientPreferenceModel;
import kg.tezal.tezal_back.model.ClientShortModel;
import kg.tezal.tezal_back.service.ClientService;
import kg.tezal.tezal_back.service.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "client")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private OrganizationRestController organizationRestController;

    @GetMapping(value = "/findByOrgId", produces = "application/json")
    @ResponseBody
    public String findClientsByOrgID(@RequestParam Long id, Model model) {

        List<Client> clients = clientService.findAll();
        model.addAttribute(clients);

        return "clientList";
    }

    @GetMapping("/list")
    public String getClientList(@PageableDefault Pageable pageable,
                                @RequestParam(value = "search", required = false) String search,
                                Model model) {
        Page<ClientShortModel> clients;
        if (search != null) {
            clients = clientService.findAllByNameOrDescription(search.toLowerCase(), pageable);
        } else {
            clients = clientService.getAllClientByPagination(pageable);;
        }
        List<Organization> organizations = organizationRestController.getAllSorted();

        Organization organization = new Organization();

        model.addAttribute("organizations", organizations);
        model.addAttribute("org", organization);
        model.addAttribute("clients", clients);
        return "clientList";
    }

    @GetMapping("/list/forOrgAdmin")
    public String getClientListForOrgAdmin(@PageableDefault Pageable pageable,
                                @RequestParam(value = "search", required = false) String search,
                                Model model,
                                Authentication authentication
    ) {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        Organization organization = user.getOrganization();
        List<ClientShortModel> clients = null;
        if (search != null) {
            clients = clientService.getAllClientByOrgId(organization.getId(), search.toLowerCase());
        } else {
            clients = clientService.getAllClientByOrgId(organization.getId(), null);;
        }
        model.addAttribute("org", organization);
        model.addAttribute("clients", clients);
        return "clientList";
    }

    @GetMapping("{id}")
    public String getClientById(@PathVariable("id") Long id, Model model) {
        ClientLongModel clientLongModel = clientService.findModelById(id);
        model.addAttribute("client", clientLongModel);
        model.addAttribute("add", false);
        return "clientForm";
    }

    @PostMapping("/update/{id}")
    public String updateClient(@PathVariable("id") Long id, @Valid @ModelAttribute("client") ClientModelImage clientModelImage,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("add", false);
            model.addAttribute(clientModelImage);
            return "clientForm";
        }
        clientService.putById(id, clientModelImage);
        return "redirect:/client/list";
    }


    @GetMapping(value = "/form")
    public String clientForm(Model model, ClientLongModel clientLongModel) {

        if (clientLongModel != null) {
            model.addAttribute("client", clientLongModel);
//            model.addAttribute("add", true);
        }
        return "clientForm";
    }


//    @PostMapping(value = "/create")
//    public String addClient(@Valid Client client, BindingResult result) {
//        if (result.hasErrors()) {
//            return "clientForm";
//        }
//        clientService.create(client);
//        return "redirect:/client/list";
//    }


    @PostMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            clientService.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("has_exception", true);
            return "redirect:/client/" + id;
        }
        return "redirect:/client/list";
    }

    @GetMapping("/{id}/preferences")
    public String getPreferences(@PathVariable("id") Long id, Model model) {
        List<ClientPreferenceModel> clientPreferenceModels = clientService.getClientPreferences(id);
        model.addAttribute("clientPreferenceModels", clientPreferenceModels);
        return "clientPreferencesList";
    }

}
