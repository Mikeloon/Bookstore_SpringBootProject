package bookstore.exception;

public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException() {
        super("Customer not found");
    }
}
