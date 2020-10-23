package pl.piotron.animals.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.piotron.animals.model.User;
import pl.piotron.animals.repositories.UserRepository;

import javax.transaction.Transactional;

@Component
public class CustomUserDetails implements UserDetailsService {
    private UserRepository userRepository;
    @Autowired
    public CustomUserDetails(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("User not found");

        return new UserPrincipal(user);
    }
}
