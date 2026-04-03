package kz.com.java_component.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.ArrayList;

@Entity(name = "PEOPLE")
public class User {

        @Id
        @NotBlank(message = "Email can not be empty")
        private String email;

        @NotBlank(message = "Name can not be empty")
        private String name;

        private String gravatarUrl;

        @NotBlank(message = "Password can not be empty")
        private String password;

        private Collection<UserRole> userRole = new ArrayList<>();

        private boolean active;

        public User() {}

        public User(String email, String name, String gravatarUrl,
                    String password, Collection<UserRole> userRole, boolean active) {
                this.email = email;
                this.name = name;
                this.gravatarUrl = gravatarUrl;
                this.password = password;
                this.userRole = userRole;
                this.active = active;
        }

        public void setUserRoleArr(UserRole... roles) {
                this.userRole = new ArrayList<>();
                for (UserRole r : roles) this.userRole.add(r);
        }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getGravatarUrl() { return gravatarUrl; }
        public void setGravatarUrl(String gravatarUrl) { this.gravatarUrl = gravatarUrl; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public Collection<UserRole> getUserRole() { return userRole; }
        public void setUserRole(Collection<UserRole> userRole) { this.userRole = userRole; }

        public boolean isActive() { return active; }
        public void setActive(boolean active) { this.active = active; }
}
