package za.co.neslotech.banking.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.neslotech.banking.schema.atm.DispensedCash;
import za.co.neslotech.banking.schema.atm.WithdrawRequest;
import za.co.neslotech.banking.schema.client.ApiClient;
import za.co.neslotech.banking.schema.client.account.ClientAccount;
import za.co.neslotech.banking.services.IATMService;
import za.co.neslotech.banking.services.IClientService;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final IClientService clientService;
    private final IATMService atmService;

    @Autowired
    public ClientController(IClientService clientService, IATMService atmService) {
        this.clientService = clientService;
        this.atmService = atmService;
    }

    @GetMapping(path = "/{id}/accounts")
    public ResponseEntity<ApiClient> retrieveAccounts(@PathVariable String id,
                                                      @RequestParam(defaultValue = "true") boolean transactional) {
        log.debug("ClientController :: retrieveAccounts :: " + id);

        ApiClient apiClient = new ApiClient();
        apiClient.setClientId(id);

        List<ClientAccount> accounts;
        if (transactional) {
            accounts = clientService.retrieveAccounts(apiClient);
        } else {
            accounts = clientService.retrieveCurrencyAccounts(apiClient);
        }

        apiClient.setAccounts(accounts);

        Link accountsLink = linkTo(methodOn(ClientController.class).retrieveAccounts(id, transactional)).withRel("accounts");
        apiClient.add(accountsLink);

        Link customerLink = linkTo(ClientController.class).slash(id).withSelfRel();
        apiClient.add(customerLink);

        return ResponseEntity.ok(apiClient);
    }

    @PostMapping(path = "/{id}/accounts/{accountNumber}/withdraw")
    public ResponseEntity<DispensedCash> withdrawAccount(@PathVariable String id, @PathVariable String accountNumber,
                                                         @RequestBody WithdrawRequest request) {
        log.debug("ClientController :: withdrawAccount :: " + id + " :: " + accountNumber);

        ApiClient apiClient = new ApiClient();
        apiClient.setClientId(id);

        ClientAccount account = new ClientAccount();
        account.setAccountNumber(accountNumber);
        account.setBalance(request.getAmount());

        account = clientService.checkAccountBalance(apiClient, account);

        DispensedCash dispensedCash = atmService.dispenseCash(request);
        clientService.updateBalance(account);

        Link accountsLink = linkTo(methodOn(ClientController.class).withdrawAccount(id, accountNumber, request)).withSelfRel();
        dispensedCash.add(accountsLink);

        Link customerLink = linkTo(ClientController.class).slash(id).withSelfRel();
        apiClient.add(customerLink);

        return ResponseEntity.ok(dispensedCash);
    }

}
