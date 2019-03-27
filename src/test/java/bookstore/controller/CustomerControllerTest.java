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

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerRepository customerRepository;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }


    @Test
    public void addCustomer() throws Exception {
        Customer customer = new Customer(1, "94052355879", "Andrzej", "Góra");

        given(customerService.addCustomer(customer)).willReturn(customer);
        this.mockMvc.perform(post("/customer/addCustomer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(customer)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findCustomerById() throws Exception {

        Customer customer = new Customer(1, "94052355879", "Andrzej", "Góra");

        when(customerService.findById(1)).thenReturn(Optional.of(customer));

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/findCustomerById/1")).andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void deleteCustomer() throws Exception {

        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer(1, "94052355879", "Andrzej", "Góra");
        Customer customer2 = new Customer(2, "88052355879", "Karol", "Król");

        customers.add(customer2);

        when(customerService.delete(1)).thenReturn(customers);

        mockMvc.perform(MockMvcRequestBuilders.delete("/customer/delete/1")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateCustomer() {
    }

    @Test
    public void findCustomerByName() throws Exception {

        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer(1, "94052355879", "Andrzej", "Góra");
        Customer customer2 = new Customer(2, "88052355879", "Karol", "Król");
        customers.add(customer);
        customers.add(customer2);

        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer2);
        when(customerService.findCustomerByName("Karol")).thenReturn(customerList);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/findCustomersByName/Karol")).andExpect(MockMvcResultMatchers.status().
                isOk()).andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Karol"));

    }

    @Test
    public void findCustomerContainsAKAndHaveMoreThan2Books() throws Exception {

        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Królewna Sniezka", 50));
        books.add(new Book(2, "Kot w Butach", 20));
        books.add(new Book(1, "Calineczka", 15));
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer(1, "94052355879", "JAKUB", "Góra", books);
        Customer customer2 = new Customer(2, "88052355879", "Karol", "Król", null);

        List<Customer> newList = new ArrayList<>();
        newList.add(customer);

        when(customerService.findCustomerWhoseNameContainsAKAndHaveMoreThan2Books(customers)).thenReturn(newList);
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/customersWithAKAAndMoreThan2Books")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void findTheOldestCustomer() throws Exception {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer(1, "94052355879", "JAKUB", "Góra", null);
        Customer customer2 = new Customer(2, "88052355879", "Karol", "Król", null);

        customers.add(customer);
        customers.add(customer2);

        // when(customerService.findTheOldestCustomer(customers)).thenReturn(customer2);
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/findTheOldestCustomer")).andExpect(MockMvcResultMatchers.status().isOk());


    }

    @Test
    public void customersSortedBySurname() throws Exception {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer(1, "94052355879", "JAKUB", "Zalewski", null);
        Customer customer2 = new Customer(2, "88052355879", "Karol", "Król", null);

        customers.add(customer);
        customers.add(customer2);

        List<Customer> newList = new ArrayList<>();
        newList.add(customer2);
        newList.add(customer);

        when(customerService.customersSortedBySurname()).thenReturn(newList);
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/customersSortedBySurname")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    private String toJson(Customer customer) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(customer);
    }
}