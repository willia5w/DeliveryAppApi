package io.swagger.api.customer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.model.customer.Customer;
import io.swagger.repositories.CustomerRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/customer")
public class CustomerController {

  @Autowired
  private CustomerRepository customerRepository;

  @RequestMapping(method = RequestMethod.GET, produces = "application/json")
  @ApiOperation(value = "Returns list of all Customers in the system.", response = Customer.class, responseContainer = "List", tags = {
      "user",})
  public ResponseEntity<List<Customer>> getAllCustomers() {
    return ResponseEntity.ok(customerRepository.findAll());
  }

  @RequestMapping(path = "/", method = RequestMethod.POST)
  @ApiOperation(value = "Creates a customer", tags = {"user",})
  public ResponseEntity<Customer> createCustomer(
      @ApiParam("Customer information for a new customer") @Valid @RequestBody Customer customer) {
    return ResponseEntity.ok(customerRepository.save(customer));
  }

  @RequestMapping(path = "/{customerId}", produces = {
      "application/json"}, method = RequestMethod.GET)
  @ApiOperation(value = "Searches for a customer by id", response = Customer.class, tags = {
      "user",})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "search results matching criteria", response = Customer.class),
      @ApiResponse(code = 404, message = "customer not found")})
  public ResponseEntity<Customer> findById(
      @ApiParam("Id of crust to get.") @PathVariable("customerId") String customerId) {
    Optional<Customer> customer = customerRepository.findById(customerId);
    if (customer.isPresent()) {
      return ResponseEntity.of(customer);
    }
    return ResponseEntity.notFound().header("message", "customerId " + customerId + " not found.")
        .build();
  }
}