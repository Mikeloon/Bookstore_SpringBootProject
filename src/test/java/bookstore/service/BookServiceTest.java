package bookstore.service;


import bookstore.exception.BookNotFoundException;
import bookstore.exception.CustomerNotFoundException;
import bookstore.model.Book;
import bookstore.repository.BookRepository;
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
public class BookServiceTest {

   @InjectMocks
    private BookService bookService;
    
    @Mock
    private BookRepository bookRepository;
    
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
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> result = bookService.getAllBooks();
        assertEquals(books, result);
    }
    // FINAL
    
    // START: Test method for getting a book by ID
    @Test
    void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book(bookId, "Book 1", "Author 1");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        Book result = bookService.getBookById(bookId);
        assertEquals(book, result);
    }
    // FINAL
    
    // START: Test method for adding a new book
    @Test
    void testAddBook() {
        Book book = new Book(1L, "New Book", "New Author");
        when(bookRepository.save(book)).thenReturn(book);
        Book result = bookService.addBook(book);
        assertEquals(book, result);
    }
    // FINAL
    
    // START: Test method for updating an existing book
    @Test
    void testUpdateBook() {
        Long bookId = 1L;
        Book book = new Book(bookId, "Updated Book", "Updated Author");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        Book result = bookService.updateBook(bookId, book);
        assertEquals(book, result);
    }
    // FINAL
    
    // START: Test method for deleting a book
    @Test
    void testDeleteBook() {
        Long bookId = 1L;
        doNothing().when(bookRepository).deleteById(bookId);
        bookService.deleteBook(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }
    // FINAL
}
