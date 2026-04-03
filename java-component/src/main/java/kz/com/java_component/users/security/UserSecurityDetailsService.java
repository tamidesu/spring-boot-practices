package kz.com.java_component.users.security;

import kz.com.java_component.users.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserSecurityDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserSecurityDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        var user = userRepository.findById(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username));

        PasswordEncoder passwordEncoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();

        String[] roles = user.getUserRole()
                .stream()
                .map(Enum::toString)
                .toArray(String[]::new);

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .roles(roles)
                .password(passwordEncoder.encode(user.getPassword()))
                .accountExpired(!user.isActive())
                .build();
    }
}
