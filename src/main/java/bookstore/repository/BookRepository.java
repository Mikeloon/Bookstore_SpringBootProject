package bookstore.repository;

import bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findById(Integer id);

    List<Book> findAllByTitle(String title);

    List<Book> findAllByPriceGreaterThan(int price);

    List<Book> findAllByOrderByPrice();

    int countBooksByPriceGreaterThan(int price);


}
