package bookstore.repository;

import bookstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findById(Integer id);

    List<Customer> findByName(String name);

    List<Customer> findAllByOrderBySurname();
}
