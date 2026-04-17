package kz.com.java_component.users;

import kz.com.java_component.users.service.UserService;
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
    private UserService userService;

    // GET /users — все пользователи
    @GetMapping
    public ResponseEntity<Iterable<User>> all() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // GET /users/{email} — найти по email
    @GetMapping("/{email}")
    public ResponseEntity<User> findUserById(@PathVariable String email) {
        return ResponseEntity.of(userService.findUserByEmail(email));
    }

    // POST /users — создать
    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        User saved = userService.saveUpdateUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{email}")
                .buildAndExpand(saved.getEmail())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    // PUT /users — обновить
    @PutMapping
    public ResponseEntity<User> update(@RequestBody User user) {
        User updated = userService.saveUpdateUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{email}")
                .buildAndExpand(updated.getEmail())
                .toUri();
        return ResponseEntity.created(location).body(updated);
    }

    // DELETE /users/{email} — удалить
    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String email) {
        userService.removeUserByEmail(email);
    }

    // GET /users/{email}/gravatar — получить gravatar URL
    @GetMapping("/{email}/gravatar")
    public ResponseEntity<String> getGravatar(@PathVariable String email) {
        return ResponseEntity.ok(
                UserGravatar.getGravatarUrlFromEmail(email));
    }

    // PATCH /users/{email}/activate — активировать пользователя
    @PatchMapping("/{email}/activate")
    public ResponseEntity<User> activate(@PathVariable String email) {
        return userService.findUserByEmail(email)
                .map(user -> {
                    user.setActive(true);
                    return ResponseEntity.ok(userService.saveUpdateUser(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH /users/{email}/deactivate — деактивировать пользователя
    @PatchMapping("/{email}/deactivate")
    public ResponseEntity<User> deactivate(@PathVariable String email) {
        return userService.findUserByEmail(email)
                .map(user -> {
                    user.setActive(false);
                    return ResponseEntity.ok(userService.saveUpdateUser(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Обработка ошибок валидации
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message != null ? message : "undef");
        });
        return errors;
    }
}
