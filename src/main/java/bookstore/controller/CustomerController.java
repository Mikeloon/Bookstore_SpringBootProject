package bookstore.controller;


import bookstore.exception.CustomerNotFoundException;
import bookstore.model.Customer;
import bookstore.repository.CustomerRepository;
import bookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    /*
    {
"pesel": "99052236994",
"surname": "KOWAL",
"name": "ANDRZEJ"
}
     */
    @PostMapping("/addCustomer")
    public void addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
    }

    /*
    /customer/findCustomerById/2
     */
    @GetMapping("/findCustomerById/{id}")
    public Optional<Customer> findCustomerById(@PathVariable Integer id) {
        return customerService.findById(id);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable int id) {
        customerService.delete(id);
    }

    /*
    {
    "id": 1,
    "pesel": "99052236994",
    "name": "ANDRZEJ",
    "surname": "KOWAL"
}
     */
    @PostMapping("/updateCustomer")
    public Customer updateCustomer(@RequestBody Customer customer) throws CustomerNotFoundException {
        return customerService.updateCustomer(customer);
    }

    /*
    /customer/findCustomersByName/JAKUB
     */
    @GetMapping("/findCustomersByName/{name}")
    public List<Customer> findCustomerByName(@PathVariable String name) {
        return customerService.findCustomerByName(name);
    }

    @GetMapping("/customersWithAKAAndMoreThan2Books")
    public List<Customer> findCustomerContainsAKAndHaveMoreThan2Books() {
        return customerService.findCustomerWhoseNameContainsAKAndHaveMoreThan2Books(customerRepository.findAll());
    }

    @GetMapping("/findTheOldestCustomer")
    public Customer findTheOldestCustomer() {
        return customerService.findTheOldestCustomer(customerRepository.findAll());
    }

    @GetMapping("/customersSortedBySurname")
    public List<Customer> customersSortedBySurname() {
        return customerService.customersSortedBySurname();
    }

}


