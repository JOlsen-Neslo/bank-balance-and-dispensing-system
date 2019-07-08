package za.co.neslotech.banking.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.neslotech.banking.schema.client.ApiClient;
import za.co.neslotech.banking.schema.client.ClientAccount;
import za.co.neslotech.banking.services.IClientService;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final IClientService clientService;

    @Autowired
    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(path = "/{id}/accounts")
    public ResponseEntity<ApiClient> retrieveAccounts(@PathVariable String id) {
        log.debug("ClientController :: retrieveAccounts :: " + id);

        ApiClient apiClient = new ApiClient();
        apiClient.setClientId(id);

        List<ClientAccount> accounts = clientService.retrieveAccounts(apiClient);
        apiClient.setAccounts(accounts);

        if (!accounts.isEmpty()) {
            Link accountsLink = linkTo(methodOn(ClientController.class).retrieveAccounts(id)).withRel("accounts");
            apiClient.add(accountsLink);
        }

        Link customerLink = linkTo(ClientController.class).slash(id).withSelfRel();
        apiClient.add(customerLink);

        return ResponseEntity.ok(apiClient);
    }

}
