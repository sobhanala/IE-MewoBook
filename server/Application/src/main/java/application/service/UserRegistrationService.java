package application.service;

import domain.entities.user.Admin;
import domain.entities.user.Customer;
import domain.valueobjects.Address;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Customer createCustomer(String username, String plainPassword, String email, Address address) {
        String hashedPassword = passwordEncoder.encode(plainPassword);
        return new Customer(username, hashedPassword, email, address);
    }

    public Admin createAdmin(String username, String plainPassword, String email, Address address) {
        String hashedPassword = passwordEncoder.encode(plainPassword);
        return new Admin(username, hashedPassword, email, address);
    }
}
