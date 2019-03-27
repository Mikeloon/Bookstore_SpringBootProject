package bookstore.service;

import bookstore.exception.CustomerNotFoundException;
import bookstore.model.Customer;
import bookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }

    public List<Customer> delete(Integer id) {
        customerRepository.deleteById(id);
        return customerRepository.findAll();
    }

    public Customer updateCustomer(Customer customer) throws CustomerNotFoundException {
        Optional<Customer> customerExist = customerRepository.findById(customer.getId());

        if (!customerExist.isPresent()) {
            throw new CustomerNotFoundException();
        }

        Customer newCustomer = new Customer();
        newCustomer.setId(customer.getId());
        newCustomer.setPesel(customer.getPesel());
        newCustomer.setName(customer.getName());
        newCustomer.setSurname(customer.getSurname());
        customerRepository.save(newCustomer);

        return customer;
    }

    public List<Customer> findCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    public List<Customer> findCustomerWhoseNameContainsAKAndHaveMoreThan2Books(List<Customer> customers) {
        List<Customer> customersWithAK = new ArrayList<>();

        for (Customer customer : customers) {

            if (customer.getName().contains("AK") && customer.getBooks().size() > 2) {
                customersWithAK.add(customer);
            }
        }
        return customersWithAK;
    }

    public Customer findTheOldestCustomer(List<Customer> customers) {
        Customer theOldestCustomer = customers.get(0);

        List<String> pesels = new ArrayList<>();

        Comparator<Customer> comparator = Comparator.comparing(Customer::getPesel);

        theOldestCustomer = customers.stream().min(comparator).get();

//        for (Customer customer : customers) {
//            pesels.add(customer.getPesel());
//        }
//        Collections.sort(pesels);
//
//        for (Customer customer : customers) {
//            if (pesels.get(0).equals(customer.getPesel())) {
//                theOldestCustomer = customer;
//            }
//        }
        return theOldestCustomer;
    }

    public List<Customer> customersSortedBySurname() {
        return customerRepository.findAllByOrderBySurname();
    }


}
