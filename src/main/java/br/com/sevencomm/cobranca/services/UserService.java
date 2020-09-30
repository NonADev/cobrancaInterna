package br.com.sevencomm.cobranca.services;

import br.com.sevencomm.cobranca.dtos.UserDTO;
import br.com.sevencomm.cobranca.exception.ObjectNotFoundException;
import br.com.sevencomm.cobranca.models.User;
import br.com.sevencomm.cobranca.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public UserDTO getUserById(Long id){
        return repository.findById(id).map(UserDTO::create).orElseThrow(()->new ObjectNotFoundException("User not found"));
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public UserDTO insert(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return UserDTO.create(repository.save(user));
    }

    public List<UserDTO> getUsers(){
        return repository.findAll().stream().map(UserDTO::create).collect(Collectors.toList());
    }
}
