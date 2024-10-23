package br.com.santander.authenticationservice.services;

import br.com.santander.authenticationservice.clients.UserServiceClient;
import br.com.santander.authenticationservice.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userServiceClient.findByEmail(email);

        if (user == null) throw new RuntimeException("User does not exists");
        if (!user.isStatus()) throw new RuntimeException("Locked user. Contact the support");

        return user;
    }
}
