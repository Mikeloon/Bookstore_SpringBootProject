package bookstore.service;

import bookstore.exception.BookNotFoundException;
import bookstore.exception.CustomerNotFoundException;
import bookstore.model.Book;
import bookstore.model.Customer;
import bookstore.repository.BookRepository;
import bookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Book addBook(Book book) throws CustomerNotFoundException {

        Optional<Customer> customerExist = customerRepository.findById(book.getCustomer().getId());

        if (!customerExist.isPresent()) {
            throw new CustomerNotFoundException();
        }

        return bookRepository.save(book);

    }

    public Optional<Book> findBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public List<Book> delete(Integer id) {
        bookRepository.deleteById(id);
        return bookRepository.findAll();
    }

    public Book updateBook(Book book) throws BookNotFoundException {

        Optional<Book> bookExist = bookRepository.findById(book.getId());

        if (!bookExist.isPresent()) {
            throw new BookNotFoundException();
        }
        Book newBook = new Book();
        newBook.setId(book.getId());
        newBook.setTitle(book.getTitle());
        newBook.setPrice(book.getPrice());
        newBook.setCustomer(book.getCustomer());
        bookRepository.save(newBook);

        return newBook;
    }

    public List<Book> findByTitle(String title) {
        return bookRepository.findAllByTitle(title);
    }

    public List<Book> findBooksAbovePrice(int price) {
        List<Book> result = bookRepository.findAllByPriceGreaterThan(price);
        return result;
    }


    public Book findTheCheapestBook(List<Book> books) {
        int min = books.get(0).getPrice();
        Book result = books.get(0);
        for (Book book : books) {
            if (min > book.getPrice()) {
                min = book.getPrice();
                result = book;
            }
        }
        return result;
    }

    public List<Book> booksSortedByPrice() {
        return bookRepository.findAllByOrderByPrice();
    }

    public int countBooksByPriceGreaterThan(int price) {
        return bookRepository.countBooksByPriceGreaterThan(price);
    }
}
