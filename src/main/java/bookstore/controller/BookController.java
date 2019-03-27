package bookstore.controller;

import bookstore.exception.BookNotFoundException;
import bookstore.exception.CustomerNotFoundException;
import bookstore.model.Book;
import bookstore.repository.BookRepository;
import bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    /*
    {
"title": "ALGEBRA",
"price": 25,
"customer": {
	"id" : 4
}
}
     */
    @PostMapping("/addBook")
    public void addBook(@RequestBody Book book) throws CustomerNotFoundException {
        bookService.addBook(book);
    }


    /*
    /book/findBookById/5
     */
    @GetMapping("/findBookById/{id}")
    public Optional<Book> findBookById(@PathVariable Integer id) {
        return bookService.findBookById(id);
    }

    /*
    /book/delete/1
     */
    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable int id) {
        bookService.delete(id);
    }

    /*
{
"id": 10,
"title": "PINOKIO",
"price": 19,
"customer": {
	"id" : 2
}
}
     */
    @PostMapping("/updateBook")
    public Book updateBook(@RequestBody Book book) throws BookNotFoundException {
        return bookService.updateBook(book);
    }

    /*
    book/findBookByTitle/ANALIZA MATEMATYCZNA I
     */
    @GetMapping("/findBookByTitle/{title}")
    public List<Book> findBookByTitle(@PathVariable String title) {
        return bookService.findByTitle(title);
    }

    /*
  /book/priceAbove/15
     */
    @GetMapping("/priceAbove/{price}")
    public List<Book> findAllByPriceAfter(@PathVariable int price) {
        final List<Book> result = bookService.findBooksAbovePrice(price);
        return result;
    }

    @GetMapping("/findTheCheapestBook")
    public Book findTheCheapestBook() {
        return bookService.findTheCheapestBook(bookRepository.findAll());
    }

    @GetMapping("/booksSortedByPrice")
    public List<Book> sortedBooks() {
        List<Book> result = bookService.booksSortedByPrice();
        return result;
    }


    @GetMapping("/countBooksAbove/{price}")
    public int countBooksByPriceAfter(@PathVariable int price) {
        return bookService.countBooksByPriceGreaterThan(price);
    }
}
