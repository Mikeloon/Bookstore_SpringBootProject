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
    
    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    // START: Test method for getting all books
    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(
                new Book(1L, "Book 1", "Author 1"),
                new Book(2L, "Book 2", "Author 2")
        );
        when(bookService.getAllBooks()).thenReturn(books);
        ResponseEntity<List<Book>> response = bookController.getAllBooks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
    }
    // FINAL
    
    // START: Test method for getting a book by ID
    @Test
    void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book(bookId, "Book 1", "Author 1");
        when(bookService.getBookById(bookId)).thenReturn(book);
        ResponseEntity<Book> response = bookController.getBookById(bookId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }
    // FINAL
    
    // START: Test method for adding a new book
    @Test
    void testAddBook() {
        Book book = new Book(1L, "New Book", "New Author");
        when(bookService.addBook(book)).thenReturn(book);
        ResponseEntity<Book> response = bookController.addBook(book);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(book, response.getBody());
    }
    // FINAL
    
    // START: Test method for updating an existing book
    @Test
    void testUpdateBook() {
        Long bookId = 1L;
        Book book = new Book(bookId, "Updated Book", "Updated Author");
        when(bookService.updateBook(bookId, book)).thenReturn(book);
        ResponseEntity<Book> response = bookController.updateBook(bookId, book);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(book, response.getBody());
    }
    // FINAL
    
    // START: Test method for deleting a book
    @Test
    void testDeleteBook() {
        Long bookId = 1L;
        doNothing().when(bookService).deleteBook(bookId);
        ResponseEntity<Void> response = bookController.deleteBook(bookId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(bookService, times(1)).deleteBook(bookId);
    }
    // FINAL
}
