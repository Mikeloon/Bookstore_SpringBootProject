package bookstore.controller;

import bookstore.service.BookService;
import bookstore.model.Book;
import bookstore.model.Customer;
import bookstore.repository.BookRepository;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void addBook() throws Exception {
        Customer customer = new Customer(1, "94052355879", "Andrzej", "Góra", null);
        Book book = new Book(1, "Wróżka", 22, customer);
//        given(bookService.addBook(book)).willReturn(book);
        this.mockMvc.perform(post("/book/addBook")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(book)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void findBookById() throws Exception {
        Book book = new Book(1, "Wróżka", 22, null);

        when(bookService.findBookById(1)).thenReturn(Optional.of(book));

        mockMvc.perform(MockMvcRequestBuilders.get("/book/findBookById/1")).andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));

    }

    @Test
    public void deleteBook() throws Exception {

        List<Book> books = new ArrayList<>();
        Book book = new Book(1, "Wróżka", 22, null);
        Book book1 = new Book(2, "Kot w Butach", 45, null);

        books.add(book1);

        when(bookService.delete(1)).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.delete("/book/delete/1")).andExpect(MockMvcResultMatchers.status().isOk());


    }

    @Test
    public void updateBook() {

    }

    @Test
    public void findBookByTitle() throws Exception {
        List<Book> books = new ArrayList<>();
        Book book = new Book(1, "Kaczka dziwaczka", 19, null);
        books.add(book);
        when(bookService.findByTitle("Kaczka dziwaczka")).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/findBookByTitle/Kaczka dziwaczka")).andExpect(MockMvcResultMatchers.status().
                isOk()).andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Kaczka dziwaczka"));

    }

    @Test
    public void findAllByPriceAfter() throws Exception {
        List<Book> books = new ArrayList<>();
        Book book = new Book(1, "Kaczka dziwaczka", 19, null);
        Book book1 = new Book(2, "Harry Potter", 40, null);
        books.add(book);
        books.add(book1);

        when(bookService.findBooksAbovePrice(15)).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/priceAbove/15")).andExpect(MockMvcResultMatchers.status().
                isOk()).andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(19));

    }

    @Test
    public void findTheCheapestBook() throws Exception {
        List<Book> books = new ArrayList<>();
        Book book = new Book(1, "Kaczka dziwaczka", 19, null);
        Book book1 = new Book(2, "Harry Potter", 40, null);
        books.add(book);
        books.add(book1);


        this.mockMvc.perform(get("/book/findTheCheapestBook")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void sortedBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        Book book = new Book(1, "Kaczka dziwaczka", 19, null);
        Book book1 = new Book(2, "Harry Potter", 40, null);
        books.add(book);
        books.add(book1);
        List<Book> newList = new ArrayList<>();
        newList.add(book1);
        newList.add(book);

        when(bookService.booksSortedByPrice()).thenReturn(books);
        mockMvc.perform(MockMvcRequestBuilders.get("/book/booksSortedByPrice")).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void countBooksByPriceAfter() throws Exception {
        List<Book> books = new ArrayList<>();
        Book book = new Book(1, "Kaczka dziwaczka", 19, null);
        Book book1 = new Book(2, "Harry Potter", 40, null);
        books.add(book);
        books.add(book1);

        when(bookService.countBooksByPriceGreaterThan(15)).thenReturn(2);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/countBooksAbove/15")).andExpect(MockMvcResultMatchers.status().
                isOk());

    }


    private String toJson(Book book) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(book);
    }
}