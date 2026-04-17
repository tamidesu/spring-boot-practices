package kz.com.java_component.users.service;

import kz.com.java_component.users.User;
import kz.com.java_component.users.UserRepository;
import kz.com.java_component.users.events.UserActivatedEvent;
import kz.com.java_component.users.events.UserRemovedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findById(email);
    }

    public User saveUpdateUser(User user) {
        User saved = userRepository.save(user);
        publisher.publishEvent(
                new UserActivatedEvent(saved.getEmail(), saved.isActive()));
        return saved;
    }

    public void removeUserByEmail(String email) {
        userRepository.deleteById(email);
        publisher.publishEvent(
                new UserRemovedEvent(email, LocalDateTime.now()));
    }
}
