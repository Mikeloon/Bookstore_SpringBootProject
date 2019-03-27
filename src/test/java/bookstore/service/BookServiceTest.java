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

    @Test
    public void addBook() throws CustomerNotFoundException {
        Book book = new Book(1, "Basnie", 22, null);

        Mockito.when(bookRepository.save(book)).thenReturn(book);
        assertEquals(book, bookService.addBook(book));
    }

    @Test
    public void findBookById() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Królewna Sniezka", 50, null));
        books.add(new Book(2, "Kot w Butach", 20, null));

        Book book1 = new Book(1, "Królewna Sniezka", 50, null);
        Optional<Book> book = Optional.of(book1);

        Mockito.when(bookRepository.findById(1)).thenReturn(book);
        assertEquals(book, bookService.findBookById(1));
    }

    @Test
    public void delete() {
        Book book = new Book(2, "Kot w Burach", 20, null);
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Calineczka", 15, null));
        books.add(new Book(2, "Calineczka", 20, null));

        List<Book> newList = new ArrayList<>();
        newList.add(new Book(2, "Calineczka", 20, null));

        //  assertEquals(newList, bookService.delete(2));
        Mockito.when(bookRepository.findAll()).thenReturn(newList);
        assertEquals(newList, bookService.delete(1));
    }

    @Test
    public void updateBook() throws BookNotFoundException {

        Book book = new Book(2, "Kot w Burach", 20, null);
        Optional<Book> optionalBook = Optional.of(book);

        Book newBook = new Book(2, "Kot", 15, null);
        Optional<Book> optionalNewBook = Optional.of(newBook);

        Mockito.when(bookRepository.findById(2)).thenReturn(optionalNewBook);
        assertEquals(book, bookService.updateBook(book));

    }

    @Test
    public void findByTitle() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Calineczka", 15, null));
        books.add(new Book(2, "Calineczka", 20, null));

        Mockito.when(bookRepository.findAllByTitle("Calineczka")).thenReturn(books);
        assertEquals(books, bookService.findByTitle("Calineczka"));
    }

    @Test
    public void findBooksAbovePrice() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Lalka", 15, null));
        books.add(new Book(2, "Wojna", 20, null));
        books.add(new Book(3, "Ogniem i mieczem", 10, null));

        Mockito.when(bookRepository.findAllByPriceGreaterThan(10)).thenReturn(books);
        assertEquals(books, bookService.findBooksAbovePrice(10));

    }

    @Test
    public void findTheCheapestBook() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Lalka", 10, null));
        books.add(new Book(2, "Wojna", 20, null));
        books.add(new Book(3, "Ogniem i mieczem", 15, null));

        Book book = new Book(1, "Lalka", 10, null);

        assertEquals(book, bookService.findTheCheapestBook(books));


    }

    @Test
    public void booksSortedByPrice() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Lalka", 25, null));
        books.add(new Book(2, "Wojna", 20, null));
        books.add(new Book(3, "Ogniem i mieczem", 10, null));

        Book book = new Book(1, "Lalka", 25, null);

        Mockito.when(bookRepository.findAllByOrderByPrice()).thenReturn(books);
        assertEquals(books, bookService.booksSortedByPrice());

    }

    @Test
    public void countBooksByPriceAfter() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "Lalka", 25, null));
        books.add(new Book(2, "Wojna", 20, null));

        Mockito.when(bookRepository.countBooksByPriceGreaterThan(15)).thenReturn(2);
        assertEquals(2, bookService.countBooksByPriceGreaterThan(15));
    }
}