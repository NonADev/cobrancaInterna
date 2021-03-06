package br.com.sevencomm.cobranca.application.configs.security;

import br.com.sevencomm.cobranca.domain.models.User;
import br.com.sevencomm.cobranca.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
        else{
            return user;
        }
    }
}
