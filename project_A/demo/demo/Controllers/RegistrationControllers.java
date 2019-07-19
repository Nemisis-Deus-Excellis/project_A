package com.example.demo;
@RestController
public class RegistrationController {

 @Autowired
 private CustomerService customerService;

 @PostMapping("/registration")
 public ResponseEntity < Customer > register(@RequestBody Registration registration) {
  Customer customer = customerService.saveCustomer(mapCustomerData(registration));
  return new ResponseEntity < Customer > (customer, HttpStatus.OK);
 }

 protected Customer mapCustomerData(Registration registration) {
  Customer customer = new Customer(registration.getFirstName(), registration.getLastName(), registration.getEmail());
  customer.setAge(registration.getAge());
  return customer;
 }
}