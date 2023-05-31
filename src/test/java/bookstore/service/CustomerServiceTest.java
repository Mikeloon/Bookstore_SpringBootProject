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
    
    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    // START: Test method for getting all customers
    @Test
    void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "John Doe", "john@example.com"),
                new Customer(2L, "Jane Smith", "jane@example.com")
        );
        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> result = customerService.getAllCustomers();
        assertEquals(customers, result);
    }
    // FINAL
    
    // START: Test method for getting a customer by ID
    @Test
    void testGetCustomerById() {
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "John Doe", "john@example.com");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Customer result = customerService.getCustomerById(customerId);
        assertEquals(customer, result);
    }
    // FINAL
    
    // START: Test method for adding a new customer
    @Test
    void testAddCustomer() {
        Customer customer = new Customer(1L, "New Customer", "new@example.com");
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer result = customerService.addCustomer(customer);
        assertEquals(customer, result);
    }
    // FINAL
    
    // START: Test method for updating an existing customer
    @Test
    void testUpdateCustomer() {
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "Updated Customer", "updated@example.com");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        Customer result = customerService.updateCustomer(customerId, customer);
        assertEquals(customer, result);
    }
    // FINAL
    
    // START: Test method for deleting a customer
    @Test
    void testDeleteCustomer() {
        Long customerId = 1L;
        doNothing().when(customerRepository).deleteById(customerId);
        customerService.deleteCustomer(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
    }
    // FINAL
}
