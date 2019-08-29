package com.gabor.partypeps.services;

import com.gabor.partypeps.models.dto.UserDTO;
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
    public UserService userService;

    private static Logger logger = Logger.getLogger(UserSecurityService.class.getName());

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        logger.info("Load Users by Username for Authentication");
        Preconditions.checkNotNull(username, "username");
        final UserDTO user = userService.findUserByUsername(username);
        if(user == null){
            logger.warning("Username not found: " + username);
            throw new UsernameNotFoundException("Username was not found: " + username);
        }
        logger.info("Username found: " + username);
        return new User(user.name,user.password, user.authorities);
    }
}
