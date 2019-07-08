package za.co.neslotech.banking.schema;

import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;

/**
 * API Entity that is passed to the client in order to obfuscate the domain.
 */
public abstract class ApiEntity extends ResourceSupport implements Serializable {
}
