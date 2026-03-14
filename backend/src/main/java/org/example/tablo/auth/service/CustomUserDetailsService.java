package org.example.tablo.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.tablo.auth.security.AuthenticatedUser;
import org.example.tablo.user.domain.User;
import org.example.tablo.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return AuthenticatedUser.from(user);
    }
}
