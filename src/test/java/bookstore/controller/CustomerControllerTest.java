package bookstore.controller;

import bookstore.service.CustomerService;
import bookstore.model.Book;
import bookstore.model.Customer;
import bookstore.repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;
    
    @Mock
    private CustomerService customerService;
    
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
        when(customerService.getAllCustomers()).thenReturn(customers);
        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
    }
    // FINAL
    
    // START: Test method for getting a customer by ID
    @Test
    void testGetCustomerById() {
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "John Doe", "john@example.com");
        when(customerService.getCustomerById(customerId)).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.getCustomerById(customerId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }
    // FINAL
    
    // START: Test method for adding a new customer
    @Test
    void testAddCustomer() {
        Customer customer = new Customer(1L, "New Customer", "new@example.com");
        when(customerService.addCustomer(customer)).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.addCustomer(customer);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }
    // FINAL
    
    // START: Test method for updating an existing customer
    @Test
    void testUpdateCustomer() {
        Long customerId = 1L;
        Customer customer = new Customer(customerId, "Updated Customer", "updated@example.com");
        when(customerService.updateCustomer(customerId, customer)).thenReturn(customer);
        ResponseEntity<Customer> response = customerController.updateCustomer(customerId, customer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }
    // FINAL
    
    // START: Test method for deleting a customer
    @Test
    void testDeleteCustomer() {
        Long customerId = 1L;
        doNothing().when(customerService).deleteCustomer(customerId);
        ResponseEntity<Void> response = customerController.deleteCustomer(customerId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(customerService, times(1)).deleteCustomer(customerId);
    }
    // FINAL
}
