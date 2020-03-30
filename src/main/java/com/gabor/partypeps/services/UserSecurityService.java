package com.gabor.partypeps.services;

import com.gabor.partypeps.repositories.UserRepository;
import org.postgresql.shaded.com.ongres.scram.common.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    private static Logger logger = Logger.getLogger(UserSecurityService.class.getName());

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        logger.info("Load Users by Username for Authentication");
        Preconditions.checkNotNull(username, "username");
        final com.gabor.partypeps.models.dao.User user = userRepository.findByUsername(username);
        if(user == null){
            logger.warning("Username not found: " + username);
            throw new UsernameNotFoundException("Username was not found: " + username);
        }
        logger.info("Username found: " + username);
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
