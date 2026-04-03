package kz.com.java_component.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Iterable<User>> all() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> findUserById(@PathVariable String email) {
        return ResponseEntity.of(userRepository.findById(email));
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<User> save(@RequestBody User user) {
        userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{email}")
                .buildAndExpand(user.getEmail())
                .toUri();
        return ResponseEntity.created(location).body(user);
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String email) {
        userRepository.deleteById(email);
    }

    @GetMapping("/{email}/gravatar")
    public ResponseEntity<String> getGravatar(@PathVariable String email) {
        return ResponseEntity.ok(UserGravatar.getGravatarUrlFromEmail(email));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message != null ? message : "undef");
        });
        return errors;
    }
}
