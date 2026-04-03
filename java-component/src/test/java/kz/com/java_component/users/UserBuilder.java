package kz.com.java_component.users;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

public class UserBuilder {

    private final User user;
    private final Validator validator;

    private UserBuilder(User user, Validator validator) {
        this.user = user;
        this.validator = validator;
    }

    public static UserBuilder createUser() {
        return new UserBuilder(new User(), null);
    }

    public static UserBuilder createUser(Validator validator) {
        return new UserBuilder(new User(), validator);
    }

    public UserBuilder withEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder withName(String name) {
        user.setName(name);
        return this;
    }

    public UserBuilder withPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder withRoles(UserRole... roles) {
        user.setUserRoleArr(roles);
        return this;
    }

    public UserBuilder active() {
        user.setActive(true);
        return this;
    }

    public UserBuilder inactive() {
        user.setActive(false);
        return this;
    }

    public User build() {
        if (validator != null) {
            var violations = validator.validate(user);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        }
        if (user.getEmail() != null) {
            user.setGravatarUrl(UserGravatar.getGravatarUrlFromEmail(user.getEmail()));
        }
        return user;
    }
}
