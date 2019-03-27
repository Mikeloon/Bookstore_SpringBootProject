package bookstore.service;

import bookstore.exception.CustomerNotFoundException;
import bookstore.model.Book;
import bookstore.model.Customer;
import bookstore.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void addCustomer() {

        Customer customer = new Customer(1, "99041244321", "Jan", "Majchrzak", null);
        Customer customer1 = new Customer(2, "9905554321", "Andrzej", "Nowak", null);

        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        assertEquals(customer, customerService.addCustomer(customer));
    }

    @Test
    public void findById() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer(1, "99041244321", "Jan", "Majchrzak", null);
        Customer customer1 = new Customer(2, "9905554321", "Andrzej", "Nowak", null);
        customers.add(customer);
        customers.add(customer1);

        Optional<Customer> newCustomer = Optional.of(customer);
        Mockito.when(customerRepository.findById(1)).thenReturn(newCustomer);
        assertEquals(newCustomer, customerService.findById(1));

    }

    @Test
    public void delete() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer(1, "99041244321", "Jan", "Majchrzak", null);
        Customer customer1 = new Customer(2, "9905554321", "Andrzej", "Nowak", null);
        customers.add(customer);
        customers.add(customer1);

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);

        Mockito.when(customerRepository.findAll()).thenReturn(customerList);
        assertEquals(customerList, customerService.delete(1));

    }

    @Test
    public void updateCustomer() throws CustomerNotFoundException {
        Customer customer = new Customer(1, "99041244321", "Jan", "Majchrzak", null);
        Optional<Customer> cust = Optional.of(customer);
        Customer newCustomer = new Customer(1, "9905554321", "Andrzej", "Nowak", null);

        Mockito.when(customerRepository.findById(1)).thenReturn(cust);
        assertEquals(customer, customerService.updateCustomer(customer));
    }

    @Test
    public void findCustomerByName() {

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "88031144332", "Jerzy", "Góralski", null));
        customers.add(new Customer(2, "77112244556", "Karol", "Kowal", null));

        Mockito.when(customerRepository.findByName("Jerzy")).thenReturn(customers);
        assertEquals(customers, customerService.findCustomerByName("Jerzy"));
    }

    @Test
    public void customersSortedBySurname() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "88031144332", "Jerzy", "Góralski", null));
        customers.add(new Customer(2, "77112244556", "Karol", "Kowal", null));
        customers.add(new Customer(3, "88112248956", "Filip", "Zgierski", null));

        Customer customer = new Customer(1, "88031144332", "Jerzy", "Góralski", null);

        Mockito.when(customerRepository.findAllByOrderBySurname()).thenReturn(customers);
        assertEquals(customer, customerService.customersSortedBySurname().get(0));

    }

    @Test
    public void findTheOldestCustomer() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "90031144332", "Jerzy", "Góralski", null));
        customers.add(new Customer(2, "77112244556", "Karol", "Kowal", null));
        customers.add(new Customer(3, "88112248956", "Filip", "Zgierski", null));

        Customer customer = new Customer(2, "77112244556", "Karol", "Kowal", null);

        assertEquals(customer, customerService.findTheOldestCustomer(customers));
    }

    @Test
    public void findCustomerWhoseNameContainsAKAndHaveMoreThan2Books() throws CustomerNotFoundException {

        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Lalka", 25, null));
        books.add(new Book(2, "Wojna", 20, null));
        books.add(new Book(3, "Ogniem i mieczem", 10, null));

        Customer customer1 = new Customer(1, "92051048757", "JAKUB", "BAJOREK", books);
        Customer customer2 = new Customer(2, "90051861424", "MARIANNA", "SLOTARZ", null);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        List<Customer> findCustromers = new ArrayList<>();
        findCustromers.add(customer1);

        assertEquals(findCustromers, customerService.findCustomerWhoseNameContainsAKAndHaveMoreThan2Books(customers));
    }
}