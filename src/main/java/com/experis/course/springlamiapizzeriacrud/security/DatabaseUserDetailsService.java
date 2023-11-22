package com.experis.course.springlamiapizzeriacrud.security;

import com.experis.course.springlamiapizzeriacrud.model.User;
import com.experis.course.springlamiapizzeriacrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    //autowired: per quando creiamo un istanza di service ci inietta UserRepository
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //per la password ci pensa spring non dobbiamo farlo noi come l'email
        //prendo username dalla log in e cerco uno user con quella email tramite il metodo in repository
        Optional<User> loggedUser = userRepository.findByEmail(username);
        //se è presente
        if (loggedUser.isPresent()) {
            //creo un nuovo DatabaseUserDetails con i dati dello user
            //tramite spring verifico anche la password
            return new DatabaseUserDetails(loggedUser.get());
            //altrimenti tiro un eccezione
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
